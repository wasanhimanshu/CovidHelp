package com.example.covidhelp.model;

public class User {
    private String mName;
    private String mEmail;
    private String mPhone;


    public User(){}

    public User(String name,String email,String phone){
        this.mEmail=email;
        this.mName=name;
        this.mPhone=phone;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }
}
