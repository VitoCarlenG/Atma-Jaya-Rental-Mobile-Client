package com.vitocarlengiovanni.ajr.models;

public class Driver {
    private Long id;
    private String format_id_driver;
    private Long id_driver;
    private String nama_driver;
    private String alamat_driver;
    private String email_driver;
    private String nomor_telepon_driver;
    private float rerata_rating_driver;
    private int status_ketersediaan_driver;
    private String password_driver;

    public Driver(String nama_driver, String alamat_driver, String email_driver, String nomor_telepon_driver, int status_ketersediaan_driver, String password_driver) {
        this.nama_driver = nama_driver;
        this.alamat_driver = alamat_driver;
        this.email_driver = email_driver;
        this.nomor_telepon_driver = nomor_telepon_driver;
        this.status_ketersediaan_driver = status_ketersediaan_driver;
        this.password_driver = password_driver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormat_id_driver() {
        return format_id_driver;
    }

    public void setFormat_id_driver(String format_id_driver) {
        this.format_id_driver = format_id_driver;
    }

    public Long getId_driver() {
        return id_driver;
    }

    public void setId_driver(Long id_driver) {
        this.id_driver = id_driver;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
    }

    public String getAlamat_driver() {
        return alamat_driver;
    }

    public void setAlamat_driver(String alamat_driver) {
        this.alamat_driver = alamat_driver;
    }

    public String getEmail_driver() {
        return email_driver;
    }

    public void setEmail_driver(String email_driver) {
        this.email_driver = email_driver;
    }

    public String getNomor_telepon_driver() {
        return nomor_telepon_driver;
    }

    public void setNomor_telepon_driver(String nomor_telepon_driver) {
        this.nomor_telepon_driver = nomor_telepon_driver;
    }

    public float getRerata_rating_driver() {
        return rerata_rating_driver;
    }

    public void setRerata_rating_driver(float rerata_rating_driver) {
        this.rerata_rating_driver = rerata_rating_driver;
    }

    public int getStatus_ketersediaan_driver() {
        return status_ketersediaan_driver;
    }

    public void setStatus_ketersediaan_driver(int status_ketersediaan_driver) {
        this.status_ketersediaan_driver = status_ketersediaan_driver;
    }

    public String getPassword_driver() {
        return password_driver;
    }

    public void setPassword_driver(String password_driver) {
        this.password_driver = password_driver;
    }
}
