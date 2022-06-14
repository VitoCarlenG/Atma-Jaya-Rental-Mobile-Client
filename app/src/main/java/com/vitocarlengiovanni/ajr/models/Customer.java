package com.vitocarlengiovanni.ajr.models;

public class Customer {
    private Long id;
    private String format_id_customer;
    private Long id_customer;
    private String nama_customer;
    private String alamat_customer;
    private String email_customer;
    private String nomor_telepon_customer;
    private String password_customer;

    public Customer(String nama_customer, String alamat_customer, String email_customer, String nomor_telepon_customer, String password_customer) {
        this.nama_customer = nama_customer;
        this.alamat_customer = alamat_customer;
        this.email_customer = email_customer;
        this.nomor_telepon_customer = nomor_telepon_customer;
        this.password_customer = password_customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormat_id_customer() {
        return format_id_customer;
    }

    public void setFormat_id_customer(String format_id_customer) {
        this.format_id_customer = format_id_customer;
    }

    public Long getId_customer() {
        return id_customer;
    }

    public void setId_customer(Long id_customer) {
        this.id_customer = id_customer;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getAlamat_customer() {
        return alamat_customer;
    }

    public void setAlamat_customer(String alamat_customer) {
        this.alamat_customer = alamat_customer;
    }

    public String getEmail_customer() {
        return email_customer;
    }

    public void setEmail_customer(String email_customer) {
        this.email_customer = email_customer;
    }

    public String getNomor_telepon_customer() {
        return nomor_telepon_customer;
    }

    public void setNomor_telepon_customer(String nomor_telepon_customer) {
        this.nomor_telepon_customer = nomor_telepon_customer;
    }

    public String getPassword_customer() {
        return password_customer;
    }

    public void setPassword_customer(String password_customer) {
        this.password_customer = password_customer;
    }
}
