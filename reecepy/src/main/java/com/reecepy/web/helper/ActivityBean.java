package com.reecepy.web.helper;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Handles and provides information if a user needs a data reload
 *
 * @author Patrick Stillhart
 */
@ManagedBean
@ApplicationScoped
public class ActivityBean implements Serializable {

    // String = email
    private Set<String> needsUpdate;

    public ActivityBean() {
        needsUpdate = new HashSet<>();
    }

    public void notifyUser(String userId) {
        needsUpdate.add(userId);
    }

    public boolean checkAndRemove(String userId) {
        return needsUpdate.remove(userId);
    }

    public void remove(String userId) {
        needsUpdate.remove(userId);
    }

}
