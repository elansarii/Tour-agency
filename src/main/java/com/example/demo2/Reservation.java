package com.example.demo2;

import java.sql.Date;

public class Reservation {
    private Date bookingDate;
    private String destination;
    private String duration;
    private Double price;
    private String siteName;
    private String status;
    private Date tourDate;
    private int Customer_ID;
    private int Reservation_ID;
    private int Agent_ID;
    public Reservation(Date bookingDate, String destination, String duration, Double price, String siteName, String status, Date tourDate) {
        this.bookingDate = bookingDate;
        this.destination = destination;
        this.duration = duration;
        this.price = price;
        this.siteName = siteName;
        this.status = status;
        this.tourDate = tourDate;
    }
    public Reservation(String status,int Reservation_ID,int Agent_ID,int Customer_ID,Date bookingDate) {
        this.bookingDate = bookingDate;
        this.status = status;
        this.Agent_ID=Agent_ID;
        this.Customer_ID=Customer_ID;
        this.Reservation_ID=Reservation_ID;
    }


    public int getReservation_ID() {
        return Reservation_ID;
    }

    public void setReservation_ID(int reservation_ID) {
        Reservation_ID = reservation_ID;
    }

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    public int getAgent_ID() {
        return Agent_ID;
    }

    public void setAgent_ID(int agent_ID) {
        Agent_ID = agent_ID;
    }

    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getSiteName() { return siteName; }
    public void setSiteName(String siteName) { this.siteName = siteName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getTourDate() { return tourDate; }
    public void setTourDate(Date tourDate) { this.tourDate = tourDate; }
}