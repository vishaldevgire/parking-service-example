package com.parking.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ParkingSpot {
    private int id;
    private double latitudes;
    private double longitude;
    private int height;
    private int width;
    private Reservation reservation = null;

    public ParkingSpot(int id, double latitudes, double longitude, int height, int width) {
        this(id, latitudes, longitude, height, width, null);
    }

    public ParkingSpot(int id, double latitudes, double longitude, int height, int width, Reservation reservation) {
        this.id = id;
        this.latitudes = latitudes;
        this.longitude = longitude;
        this.height = height;
        this.width = width;
        this.reservation = reservation;
    }

    public int getId() {
        return id;
    }

    public double getLatitudes() {
        return latitudes;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Reservation getReservation() {
        return this.reservation;
    }

    @JsonIgnore
    public void reserve(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParkingSpot that = (ParkingSpot) o;

        if (id != that.id) return false;
        if (Double.compare(that.latitudes, latitudes) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (height != that.height) return false;
        return width == that.width;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(latitudes);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + height;
        result = 31 * result + width;
        return result;
    }

    @JsonIgnore
    public boolean isReserved() {
        return this.reservation != null;
    }
}

