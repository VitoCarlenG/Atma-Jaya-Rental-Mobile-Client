package com.vitocarlengiovanni.ajr.models;

public class Transaksi {
    private Long id;
    private String format_nomor_transaksi;
    private Long nomor_transaksi;
    private String tanggal_transaksi;
    private String status_transaksi;
    private String nama_customer;
    private String nama_driver;
    private String nama_pegawai;
    private String kode_promo;
    private String nama_mobil;

    public Transaksi(String format_nomor_transaksi, Long nomor_transaksi, String tanggal_transaksi, String status_transaksi, String nama_customer, String nama_driver, String nama_pegawai, String kode_promo, String nama_mobil) {
        this.format_nomor_transaksi = format_nomor_transaksi;
        this.nomor_transaksi = nomor_transaksi;
        this.tanggal_transaksi = tanggal_transaksi;
        this.status_transaksi = status_transaksi;
        this.nama_customer = nama_customer;
        this.nama_driver = nama_driver;
        this.nama_pegawai = nama_pegawai;
        this.kode_promo = kode_promo;
        this.nama_mobil = nama_mobil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormat_nomor_transaksi() {
        return format_nomor_transaksi;
    }

    public void setFormat_nomor_transaksi(String format_nomor_transaksi) {
        this.format_nomor_transaksi = format_nomor_transaksi;
    }

    public Long getNomor_transaksi() {
        return nomor_transaksi;
    }

    public void setNomor_transaksi(Long nomor_transaksi) {
        this.nomor_transaksi = nomor_transaksi;
    }

    public String getTanggal_transaksi() {
        return tanggal_transaksi;
    }

    public void setTanggal_transaksi(String tanggal_transaksi) {
        this.tanggal_transaksi = tanggal_transaksi;
    }

    public String getStatus_transaksi() {
        return status_transaksi;
    }

    public void setStatus_transaksi(String status_transaksi) {
        this.status_transaksi = status_transaksi;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public String getKode_promo() {
        return kode_promo;
    }

    public void setKode_promo(String kode_promo) {
        this.kode_promo = kode_promo;
    }

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }
}
