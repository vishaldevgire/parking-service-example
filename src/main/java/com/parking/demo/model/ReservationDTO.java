package com.parking.demo.model;

public class ReservationDTO {
    private String user;
    private int numOfDays;

    public ReservationDTO() {
    }

    public ReservationDTO(String user, int numOfDays) {
        this.user = user;
        this.numOfDays = numOfDays;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(int numOfDays) {
        this.numOfDays = numOfDays;
    }
}
