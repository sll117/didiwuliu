package com.example.didi.beans;

public class PathBean {
    private String location;
    private float carriage;

    public PathBean(String location, float carriage) {
        this.location = location;
        this.carriage = carriage;
    }
    public PathBean()
    {
        location="";
        carriage=0.0f;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getCarriage() {
        return carriage;
    }

    public void setCarriage(float carriage) {
        this.carriage = carriage;
    }
}
