package com.example.demo2;

import java.sql.Date;
import java.time.LocalTime;

public class Tour {
    private int tourId;
    private String destination;
    private String duration;
    private String guideName;
    private String location;
    private Double price;
    private String siteName;
    private Date startTime;
    private Date tourDate;


    public Tour(int tourId, String destination, String duration, String guideName, String location, Double price, String siteName, Date startTime, Date tourDate) {
        this.tourId = tourId;
        this.destination = destination;
        this.duration = duration;
        this.guideName = guideName;
        this.location = location;
        this.price = price;
        this.siteName = siteName;
        this.startTime = startTime;
        this.tourDate = tourDate;
    }


    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getTourDate() {
        return tourDate;
    }

    public void setTourDate(Date tourDate) {
        this.tourDate = tourDate;
    }
}
