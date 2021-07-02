package com.example.covidhelp.model;

public class Requests {
    private String uid;
    private String name;
    private String phone;
    private String state;
    private String hospital;
    private String desc;
    private String amount;
    private String type;
    private String request_type;

    public Requests(){}

    public Requests(String n,String p, String s, String h ,String d, String a, String t, String request_t,String uid){
        this.name=n;
        this.phone=p;
        this.state=s;
        this.hospital=h;
        this.desc=d;
        this.amount=a;
        this.type=t;
        this.request_type=request_t;
        this.uid=uid;
    }

    public String getRequest_type() {
        return request_type;
    }

    public String getHospital() {
        return hospital;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public String getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }
}
