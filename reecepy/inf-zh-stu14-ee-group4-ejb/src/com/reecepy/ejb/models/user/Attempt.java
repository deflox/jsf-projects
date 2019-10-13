package com.reecepy.ejb.models.user;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the attempt database table.
 *
 * @author Patrick Stillhart
 */
@Entity(name = "attempts")
public class Attempt implements Serializable {

    @Id
    @Column(name = "attemptIp")
    private String ip;

    @Column(name = "count")
    private int count;

    @Column(name = "entry")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entry;

    public Attempt() {
    }

    public Attempt(String ip, int count, Timestamp entry) {
        this.ip = ip;
        this.count = count;
        this.entry = entry;
    }

    public void addOne() {
        this.count += 1;
    }

    public String getIp() {
        return ip;
    }

    public int getCount() {
        return count;
    }

    public Date getEntry() {
        return entry;
    }
}
