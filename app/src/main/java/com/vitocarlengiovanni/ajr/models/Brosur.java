package com.vitocarlengiovanni.ajr.models;

import com.google.gson.annotations.SerializedName;

public class Brosur {
    private Long id_mobil;
    private String nama_mobil;
    private String tipe_mobil;
    private String jenis_transmisi_mobil;
    private String jenis_bahan_bakar_mobil;
    private String warna_mobil;
    private String volume_bagasi_mobil;
    private String fasilitas_mobil;
    private float harga_sewa_mobil;
    private String gambar_mobil;

    public Brosur(String nama_mobil, String tipe_mobil, String jenis_transmisi_mobil, String jenis_bahan_bakar_mobil, String warna_mobil, String volume_bagasi_mobil, String fasilitas_mobil, float harga_sewa_mobil, String gambar_mobil) {
        this.nama_mobil = nama_mobil;
        this.tipe_mobil = tipe_mobil;
        this.jenis_transmisi_mobil = jenis_transmisi_mobil;
        this.jenis_bahan_bakar_mobil = jenis_bahan_bakar_mobil;
        this.warna_mobil = warna_mobil;
        this.volume_bagasi_mobil = volume_bagasi_mobil;
        this.fasilitas_mobil = fasilitas_mobil;
        this.harga_sewa_mobil = harga_sewa_mobil;
        this.gambar_mobil = gambar_mobil;
    }

    public Long getId_mobil() {
        return id_mobil;
    }

    public void setId_mobil(Long id_mobil) {
        this.id_mobil = id_mobil;
    }

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }

    public String getTipe_mobil() {
        return tipe_mobil;
    }

    public void setTipe_mobil(String tipe_mobil) {
        this.tipe_mobil = tipe_mobil;
    }

    public String getJenis_transmisi_mobil() {
        return jenis_transmisi_mobil;
    }

    public void setJenis_transmisi_mobil(String jenis_transmisi_mobil) {
        this.jenis_transmisi_mobil = jenis_transmisi_mobil;
    }

    public String getJenis_bahan_bakar_mobil() {
        return jenis_bahan_bakar_mobil;
    }

    public void setJenis_bahan_bakar_mobil(String jenis_bahan_bakar_mobil) {
        this.jenis_bahan_bakar_mobil = jenis_bahan_bakar_mobil;
    }

    public String getWarna_mobil() {
        return warna_mobil;
    }

    public void setWarna_mobil(String warna_mobil) {
        this.warna_mobil = warna_mobil;
    }

    public String getVolume_bagasi_mobil() {
        return volume_bagasi_mobil;
    }

    public void setVolume_bagasi_mobil(String volume_bagasi_mobil) {
        this.volume_bagasi_mobil = volume_bagasi_mobil;
    }

    public String getFasilitas_mobil() {
        return fasilitas_mobil;
    }

    public void setFasilitas_mobil(String fasilitas_mobil) {
        this.fasilitas_mobil = fasilitas_mobil;
    }

    public float getHarga_sewa_mobil() {
        return harga_sewa_mobil;
    }

    public void setHarga_sewa_mobil(float harga_sewa_mobil) {
        this.harga_sewa_mobil = harga_sewa_mobil;
    }

    public String getGambar_mobil() {
        return gambar_mobil;
    }

    public void setGambar_mobil(String gambar_mobil) {
        this.gambar_mobil = gambar_mobil;
    }
}
