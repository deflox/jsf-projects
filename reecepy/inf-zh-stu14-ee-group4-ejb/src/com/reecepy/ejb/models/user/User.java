package com.reecepy.ejb.models.user;

import javax.persistence.*;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The persistent class for the user database table.
 *
 * @author Patrick Stillhart
 */
@Entity(name = "users")
public class User implements Serializable {

    private static final String DEFAULT_ICON = "http://i.istockimg.com/file_thumbview_approve/36951702/3/stock-illustration-36951702-chef-hat-flat-icon.jpg";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "userId")
    private int userId;

    @Column(name = "circleId")
    private int circleId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "addable")
    private boolean addable;

    @Column(name = "trusted")
    private boolean trusted;

    public User() {
    }

    public User(int userId, int circleId, String email, String password, String firstname, String lastname, boolean addable) {
        this.userId = userId;
        this.circleId = circleId;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.addable = addable;
    }

    /**
     * Gets a User specific profile picture
     *
     * @return the profile picture link
     */
    public String getProfileIcon() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = (md.digest(getEmail().getBytes("CP1252")));

            StringBuilder sb = new StringBuilder("http://www.gravatar.com/avatar/");
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }

            sb.append("?s=60&d=" + DEFAULT_ICON);

            return sb.toString();

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not working!!!!");
        }
    }

    public User getCopy() {
        return new User(userId, circleId, email, password, firstname, lastname, addable);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isAddable() {
        return addable;
    }

    public void setAddable(boolean addable) {
        this.addable = addable;
    }

    public boolean isTrusted() {
        return trusted;
    }

    public void setTrusted(boolean trusted) {
        this.trusted = trusted;
    }
}
