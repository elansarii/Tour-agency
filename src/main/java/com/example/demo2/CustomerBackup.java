package com.example.demo2;

public class CustomerBackup {
    String Passport,Residency,Phone,Name,Status;
    int Customer_ID;



    public CustomerBackup(int customer_ID, String name, String passport, String residency, String phone) {
        Passport = passport;
        Residency = residency;
        Phone = phone;
        Name = name;
        Customer_ID = customer_ID;
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

    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }
}
