package com.example.finery.Model;

public class Customers {
    private String name, email, contactNo, password;

    public Customers() {
    }

    public Customers(String name, String email, String contactNo, String password) {
        this.name = name;
        this.email = email;
        this.contactNo = contactNo;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

