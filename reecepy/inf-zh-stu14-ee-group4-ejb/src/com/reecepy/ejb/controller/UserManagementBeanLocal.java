package com.reecepy.ejb.controller;

import com.reecepy.ejb.exceptions.AttemptException;
import com.reecepy.ejb.exceptions.ValidationException;
import com.reecepy.ejb.models.user.Attempt;
import com.reecepy.ejb.models.user.User;
import com.reecepy.ejb.utils.BCrypt;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Handles action affecting the user
 *
 * @author Patrick Stillhart
 */
@LocalBean
@Stateless
public class UserManagementBeanLocal implements Serializable {

    private final static Logger LOGGER = Logger.getLogger(UserManagementBeanLocal.class.getName());

    /** basic email regex */
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}$";

    /** at least 8 chars / capital letters and numbers */
    public static final String PASSWORD_REGEX = "^((?=.*[A-Z])(?=.*\\d)\\S{8,})$";

    public final static int HASH_ROUNDS = 12;

    public final static int LOGIN_ATTEMPTS = 5;
    public final static long LOGIN_SEC_LOCKED = 30;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Returns a user for credentials
     *
     * @param ip          ip from the user who tries to login
     * @param credentials the login / password combo
     * @return the corresponding user
     * @throws AttemptException    the user tried to access too many times
     * @throws ValidationException the supplied values are not valid
     */
    @SuppressWarnings("unchecked")
    public User login(String ip, User credentials) throws AttemptException, ValidationException {

        if (credentials.getEmail().isEmpty() || credentials.getPassword().isEmpty())
            throw new ValidationException("Wrong email or password");

        // > Add Attempt
        Attempt attempt = entityManager.find(Attempt.class, ip);
        if (attempt == null) attempt = new Attempt(ip, 1, new Timestamp(new Date().getTime()));
        else attempt.addOne();
        entityManager.persist(attempt);

        // Check attempts
        if (attempt.getCount() >= LOGIN_ATTEMPTS) {
            long secPassed = (new Timestamp(new Date().getTime()).getTime() - attempt.getEntry().getTime()) / 1000;

            if (secPassed < LOGIN_SEC_LOCKED) {
                throw new AttemptException("Too many tries! Wait another " + (LOGIN_SEC_LOCKED - secPassed) + " sec");
            } else {
                Query query = entityManager.createQuery("DELETE FROM attempts a WHERE a.ip=:ip");
                query.setParameter("ip", ip);
                query.executeUpdate();
            }
        }

        // > Check credentials
        Query query = entityManager.createQuery("SELECT u FROM users u WHERE u.email=:email", User.class);
        query.setParameter("email", credentials.getEmail());
        List<User> results = query.getResultList();
        if (results.size() == 1 && BCrypt.checkpw(credentials.getPassword(), results.get(0).getPassword())) {
            return results.get(0);
        } else {
            if(results.size() > 1) LOGGER.severe("Duplicate entry for email: " + credentials.getEmail());
            throw new ValidationException("Wrong email or password");
        }

    }

    /**
     * Returns the user for the delivered id
     *
     * @param userId the userId from the user to load
     * @return the user
     */
    public User getUserById(int userId) {
        return entityManager.find(User.class, userId);
    }

    /**
     * Registers a new user
     *
     * @param user the new user
     * @throws ValidationException the supplied values are not valid
     */
    public void register(User user) throws ValidationException {
        validateUser(null, user, true);

        // Hash password
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(HASH_ROUNDS)));

        entityManager.persist(user);
        LOGGER.info("User registered: " + user.getEmail());
    }



    /**
     * Updates a user
     *
     * @param current the current user
     * @param update  the new user
     * @throws ValidationException the supplied values are not valid
     */
    public void updateUser(User current, User update, boolean hashPassword) throws ValidationException {
        validateUser(current, update, hashPassword);

        // Hash password if necessary
        if (hashPassword) update.setPassword(BCrypt.hashpw(update.getPassword(), BCrypt.gensalt(HASH_ROUNDS)));
        
        entityManager.merge(update);

    }

    /**
     * Checks if a user is ready to be added to a circle
     *
     * @param email the user mail to check
     * @return true if available
     */
    public boolean availableCircleMember(String email) {
        Query query = entityManager.createQuery("SELECT u FROM users u WHERE u.email=:email AND u.addable=:addable", User.class);
        query.setParameter("email", email);
        query.setParameter("addable", true);
        return query.getResultList().size() == 1;
    }

    /**
     * Counts all members in a circle
     *
     * @param user a user in this circle
     * @return the number of members
     */
    public int getUserCountForCircle(User user) {
        Query query = entityManager.createQuery("SELECT COUNT (u.userId) FROM users u WHERE u.circleId=:circleId", Integer.class);
        query.setParameter("circleId", user.getCircleId());
        return ((Long) query.getSingleResult()).intValue();
    }

    /**
     * Returns all other users in the same circle
     *
     * @param user a user in a circle
     * @return all users in the same circle
     */
    @SuppressWarnings("unchecked")
    public List<User> getCircleMemberForUser(User user) {
        Query query = entityManager.createQuery("SELECT u FROM users u WHERE u.circleId=:circleId", User.class);
        query.setParameter("circleId", user.getCircleId());
        return query.getResultList();
    }

    /**
     * Adds a member to a circle
     *
     * @param email the email from the user to add to the circle from the initiator
     * @param initiator the one who tires to add the email
     */
    public void addMemberToCircle(String email, User initiator) {
        Query query = entityManager.createQuery("UPDATE users SET circleId=:circleId, addable=0  WHERE email=:email");
        query.setParameter("circleId", initiator.getCircleId());
        query.setParameter("email", email);
        query.executeUpdate();
    }

    /**
     * Removes a member from a circle and remaps the circleId
     *
     * @param emailToRemove the email to remove
     * @param initiator the one who tries to remove
     */
    public void removeCircleMember(String emailToRemove, User initiator) {
        List<User> members = getCircleMemberForUser(initiator);
        if(members.size() == 1) return;

        // Check if user is in the same circle
        boolean ok = false;
        for(User member : members) {
            if(member.getEmail().equals(emailToRemove)) {
                ok = true;
                break;
            }
        }
        if(!ok) return;

        // regroup
        int newGroup = -1;
        for(User member : members) {
           if(member.getEmail().equals(emailToRemove)){
               member.setAddable(true);
               member.setCircleId(member.getUserId());
           } else {
               if(newGroup == -1) newGroup = member.getUserId();
               member.setCircleId(newGroup);
           }
        }

        // Update the suggestions if necessary
        if(newGroup != initiator.getCircleId()) {
            Query query = entityManager.createQuery("UPDATE suggestions SET circleId=:circleIdNew WHERE circleId=:circleIdOld");
            query.setParameter("circleIdNew", newGroup);
            query.setParameter("circleIdOld", initiator.getCircleId());
            query.executeUpdate();

            query = entityManager.createQuery("UPDATE additionalitems SET circleId=:circleIdNew WHERE circleId=:circleIdOld");
            query.setParameter("circleIdNew", newGroup);
            query.setParameter("circleIdOld", initiator.getCircleId());
            query.executeUpdate();

        }

        members.forEach(entityManager::merge);


    }

    /**
     * Checks weather this eMail is used or not
     *
     * @param email the email to check
     * @return true if already used
     */
    public boolean isEmailUsed(String email) {
        Query query = entityManager.createQuery("SELECT u FROM users u WHERE u.email=:email", User.class);
        query.setParameter("email", email);
        return query.getResultList().size() > 0;

    }

    /**
     * Validates a user
     *
     * @param current the old user
     * @param update  the new user
     * @throws ValidationException the supplied values are not valid
     */
    private void validateUser(User current, User update, boolean validatePassword) throws ValidationException {
        if (update.getFirstname() == null || update.getFirstname().length() < 3)
            throw new ValidationException("Please enter a first name");
        if (update.getLastname() == null || update.getLastname().length() < 3)
            throw new ValidationException("Please enter a last name");

        if (update.getEmail() == null || !update.getEmail().matches(EMAIL_REGEX))
            throw new ValidationException("Please enter a valid email address");
        if (current == null || !current.getEmail().equals(update.getEmail()))
            if (isEmailUsed(update.getEmail())) throw new ValidationException("eMail is already used");

        if (validatePassword && update.getPassword() == null || !update.getPassword().matches(PASSWORD_REGEX))
            throw new ValidationException("The password has to contain at least 8 chars, numbers and, capital letters");

    }

}
