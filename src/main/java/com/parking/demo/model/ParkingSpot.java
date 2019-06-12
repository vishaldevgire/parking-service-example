package com.parking.demo.model;

public class ParkingSpot {
    private int id;
    private double latitudes;
    private double longitude;
    private int height;
    private int width;
    private int cost;
    private boolean reserved;

    public ParkingSpot(int id, double latitudes, double longitude, int height, int width, int cost, boolean reserved) {
        this.id = id;
        this.latitudes = latitudes;
        this.longitude = longitude;
        this.height = height;
        this.width = width;
        this.cost = cost;
        this.reserved = reserved;
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

    public int getCost() {
        return cost;
    }

    public boolean isReserved() {
        return reserved;
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
        if (width != that.width) return false;
        if (cost != that.cost) return false;
        return reserved == that.reserved;
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
        result = 31 * result + cost;
        result = 31 * result + (reserved ? 1 : 0);
        return result;
    }
}

