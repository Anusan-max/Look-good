package com.example.lookgood;

public class UserHelperClass {
    //TODO remove password
    String uid , email, name, phone;

    public UserHelperClass() {

    }

    public UserHelperClass(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;

    }

    public UserHelperClass(String uid, String email, String name, String phone) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
