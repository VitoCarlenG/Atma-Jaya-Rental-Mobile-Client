package com.vitocarlengiovanni.ajr.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DriverResponse {
    private String message;

    @SerializedName("driver")
    private List<Driver> driverList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }
}
