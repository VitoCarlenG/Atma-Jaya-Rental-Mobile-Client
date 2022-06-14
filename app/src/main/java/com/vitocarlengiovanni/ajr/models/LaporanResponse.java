package com.vitocarlengiovanni.ajr.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LaporanResponse {
    private String message;

    @SerializedName("laporan")
    private List<Laporan> laporanList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Laporan> getLaporanList() {
        return laporanList;
    }

    public void setLaporanList(List<Laporan> laporanList) {
        this.laporanList = laporanList;
    }
}
