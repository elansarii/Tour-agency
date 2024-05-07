package com.example.demo2;

public class Customer {
    String Passport,Residency,Phone,Name,Status;
    int Customer_ID;



    public Customer(int customer_ID, String name, String passport, String residency, String phone) {
        Passport = passport;
        Residency = residency;
        Phone = phone;
        Name = name;
        this.Customer_ID=customer_ID;
        Status="Confirmed";
    }
    public Customer(String name, String passport, String residency, String phone) {
        Passport = passport;
        Residency = residency;
        Phone = phone;
        Name = name;

        Status="Confirmed";
    }

    public String getPassport() {
        return Passport;
    }

    public void setPassport(String passport) {
        Passport = passport;
    }

    public String getResidency() {
        return Residency;
    }

    public void setResidency(String residency) {
        Residency = residency;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCustomer_ID() {
        return Customer_ID;
    }


    public void setCustomer_ID(int Customer_ID) {
        this.Customer_ID=Customer_ID;
    }
}
