package com.example.finery;

public class Feed {
    private String name;
    private String proname;
    private String description;
    private Integer contact;

    public Feed() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }
}
