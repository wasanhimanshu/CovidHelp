package com.example.covidhelp.model;

import com.google.gson.annotations.SerializedName;

public class covidUpdate {
    @SerializedName("active")
    private String active;
    @SerializedName("confirmed")
    private String confirmed;
    @SerializedName("deaths")
    private String deaths;
    @SerializedName("lastupdatedtime")
    private String lastupdatedtime;
    @SerializedName("recovered")
    private String recovered;
    @SerializedName("state")
    private String state;

    public covidUpdate(String active, String confirmed, String deaths, String lastupdatedtime, String recovered, String state) {
        this.active = active;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.lastupdatedtime = lastupdatedtime;
        this.recovered = recovered;
        this.state = state;

    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastupdatedtime() {
        return lastupdatedtime;
    }

    public void setLastupdatedtime(String lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

}
