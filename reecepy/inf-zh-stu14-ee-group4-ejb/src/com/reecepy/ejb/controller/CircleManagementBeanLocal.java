package com.reecepy.ejb.controller;

import com.reecepy.ejb.exceptions.ValidationException;
import com.reecepy.ejb.models.circle.AdditionalItem;
import com.reecepy.ejb.models.circle.Suggestion;
import com.reecepy.ejb.models.circle.Vote;
import com.reecepy.ejb.models.user.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Manages everything regarding days
 *
 * @author Leo Rudin
 */
@LocalBean
@Stateless
public class CircleManagementBeanLocal implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Gets all days from today until in two weeks.
     *
     * @param today          Date today
     * @param futureDay      Date in the future
     * @return List with days
     */
    @SuppressWarnings("unchecked")
    public List<Suggestion> getTableForCircle(int circleId, Date today, Date futureDay) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Suggestion> query = cb.createQuery(Suggestion.class);
        Root<Suggestion> recipeRoot = query.from(Suggestion.class);

        Predicate[] predicates = new Predicate[2];
        predicates[0] = (cb.equal(recipeRoot.get("circleId"), circleId));
        predicates[1] = (cb.between(recipeRoot.get("date"), today, futureDay));

        query = query.where(predicates);
        query.orderBy(cb.asc(recipeRoot.get("date")));

        Query readyQuery = entityManager.createQuery(query);
        return readyQuery.getResultList();
    }

    /**
     * Adds or removes a vote for a user
     * Also removes a suggestion if it hasn't at least one vote
     *
     * @param user         The one who voted
     * @param suggestion   For what he voted
     */
    public void switchVote(User user, Suggestion suggestion) {
        Vote v = null;
        for (Vote vote : suggestion.getVotes()) if (vote.getUserId() == user.getUserId()) v = vote;

        if (v == null) entityManager.persist(new Vote(suggestion.getSuggestionId(), user.getUserId()));
        else {
            Query query = entityManager.createQuery("DELETE FROM votes v WHERE v.suggestionId=:suggestionId AND v.userId = :userId");
            query.setParameter("suggestionId", suggestion.getSuggestionId());
            query.setParameter("userId", user.getUserId());
            query.executeUpdate();

            // After removing one there wont be any left
            if(suggestion.getVotes().size() == 1) deleteSuggestion(suggestion);
        }
    }

    /**
     * Adds a Vote
     *
     * @param user the user who adds
     * @param id the id of the suggestion
     */
    public void addVote(User user, int id) {
        List<Integer> list = new ArrayList<>(2);
        list.add(id);
        list.add(user.getUserId());
        if(entityManager.find(Vote.class, list) == null) entityManager.persist(new Vote(id, user.getUserId()));

    }

    /**
     * Adds a suggestion entry
     *
     * @param suggestion the suggestion to be added
     * @return the generated id
     */
    @SuppressWarnings("unchecked")
    public int addSuggestion(Suggestion suggestion) {
        Query query = entityManager.createQuery("SELECT s FROM suggestions s WHERE s.circleId=:circleId AND s.recipe=:recipeId AND s.date=:date ", Suggestion.class);
        query.setParameter("circleId", suggestion.getCircleId());
        query.setParameter("recipeId", suggestion.getRecipe());
        query.setParameter("date", suggestion.getDate());
        List<Suggestion> results = query.getResultList();

        if(results.size() > 0) return results.get(0).getSuggestionId();

        entityManager.persist(suggestion);
        entityManager.flush();
        return suggestion.getSuggestionId();

    }

    /**
     * Update a suggestion entry
     *
     * @param suggestion the suggestion to be updated
     */
    public void updateSuggestion(Suggestion suggestion) {
        entityManager.merge(suggestion);
    }

    /**
     * Deletes a suggestion entry
     *
     * @param suggestion the suggestion to delete
     */
    public void deleteSuggestion(Suggestion suggestion) {
        Query query = entityManager.createQuery("DELETE FROM suggestions s WHERE s.suggestionId=:suggestionId");
        query.setParameter("suggestionId", suggestion.getSuggestionId());
        query.executeUpdate();
    }

    /**
     * Adds a new additional item to the db
     *
     * @param additionalItem the item to add
     */
    public void addAdditionalItem(AdditionalItem additionalItem) throws ValidationException{
        if(additionalItem.getAdditionalItemName().trim().isEmpty()) throw new ValidationException("Please fill in a name");
        if(additionalItem.getAmount() == 0) throw new ValidationException("Please fill in an amount");
        if(additionalItem.getAmountUnitTranslation().getAmountUnitTranslationId() == 0) throw new ValidationException("Please fill in an amount type");
        entityManager.persist(additionalItem);
    }

    /**
     * Removes an additional item to the db
     *
     * @param additionalItem the item to remove
     */
    public void removeAdditionalItem(AdditionalItem additionalItem) {
        Query query = entityManager.createQuery("DELETE FROM additionalitems a WHERE a.additionalItemId=:additionalItemId");
        query.setParameter("additionalItemId", additionalItem.getAdditionalItemId());
        query.executeUpdate();
    }

    /**
     * Gets all additional items for a circle
     *
     * @param circleId the circle id
     * @return a list with all items
     */
    @SuppressWarnings("unchecked")
    public List<AdditionalItem> getAdditionalItemsForCircle(int circleId) {
        Query query = entityManager.createQuery("SELECT a FROM additionalitems a WHERE a.circleId = :circleId", AdditionalItem.class);
        query.setParameter("circleId", circleId);
        return query.getResultList();
    }

}
