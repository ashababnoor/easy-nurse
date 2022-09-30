package com.example.easynurse;

public class User {
    public String name;
    public String email;
    public String password;
    public String dob;
    public String phone;
    public String gender;

    public User() {

    }

    public User(String name, String email, String password, String dob, String phone, String gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.phone = phone;
        this.gender = gender;
    }
}
