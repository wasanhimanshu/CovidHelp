package com.example.covidhelp.network;

import androidx.core.app.NotificationCompat;

import com.example.covidhelp.model.covidUpdate;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class statewisedata {
    @SerializedName("statewise")
   public List<covidUpdate> mUpdates;




}
