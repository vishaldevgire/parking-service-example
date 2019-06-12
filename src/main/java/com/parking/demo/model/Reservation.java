package com.parking.demo.model;

import java.util.Date;

public class Reservation {
    private String user;
    private Date start;
    private Date end;
    private int cost;

    public Reservation(String user, Date start, Date end, int cost) {
        this.user = user;
        this.start = start;
        this.end = end;
        this.cost = cost;
    }

    public String getUser() {
        return user;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (cost != that.cost) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;
        return end != null ? end.equals(that.end) : that.end == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + cost;
        return result;
    }
}
