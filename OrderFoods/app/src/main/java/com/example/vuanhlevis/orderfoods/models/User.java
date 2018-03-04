package com.example.vuanhlevis.orderfoods.models;

/**
 * Created by vuanhlevis on 03/03/2018.
 */

public class User {
    private String Name;
    private String Password;
    private String phone;

    public User() {
    }

    public User(String name, String password) {
        Name = name;
        Password = password;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}
