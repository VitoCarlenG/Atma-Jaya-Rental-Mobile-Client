package com.vitocarlengiovanni.ajr.models;

public class Promo {
    private Long id_promo;
    private String kode_promo;
    private String jenis_promo;
    private String keterangan_promo;

    public Promo(String kode_promo, String jenis_promo, String keterangan_promo) {
        this.kode_promo = kode_promo;
        this.jenis_promo = jenis_promo;
        this.keterangan_promo = keterangan_promo;
    }

    public Long getId_promo() {
        return id_promo;
    }

    public void setId_promo(Long id_promo) {
        this.id_promo = id_promo;
    }

    public String getKode_promo() {
        return kode_promo;
    }

    public void setKode_promo(String kode_promo) {
        this.kode_promo = kode_promo;
    }

    public String getJenis_promo() {
        return jenis_promo;
    }

    public void setJenis_promo(String jenis_promo) {
        this.jenis_promo = jenis_promo;
    }

    public String getKeterangan_promo() {
        return keterangan_promo;
    }

    public void setKeterangan_promo(String keterangan_promo) {
        this.keterangan_promo = keterangan_promo;
    }
}
