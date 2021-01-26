package com.example.didi.beans;

public class OrderBean {
    private int orderID;
    private int ownerID;
    private int driverID;
    private float price;
    private String start;
    private String end;

    public OrderBean()
    {

    }

    public OrderBean(int orderID, int ownerID, int driverID, float price, String start, String end) {
        this.orderID = orderID;
        this.ownerID = ownerID;
        this.driverID = driverID;
        this.price = price;
        this.start = start;
        this.end = end;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
