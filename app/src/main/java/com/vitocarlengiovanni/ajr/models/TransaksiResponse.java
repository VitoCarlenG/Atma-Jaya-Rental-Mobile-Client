package com.vitocarlengiovanni.ajr.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransaksiResponse {
    private String message;

    @SerializedName("transaksi")
    private List<Transaksi> transaksiList;
    private int temp;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Transaksi> getTransaksiList() {
        return transaksiList;
    }

    public void setTransaksiList(List<Transaksi> transaksiList) {
        this.transaksiList = transaksiList;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
