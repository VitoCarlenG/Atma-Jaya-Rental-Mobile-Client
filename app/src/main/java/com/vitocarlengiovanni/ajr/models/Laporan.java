package com.vitocarlengiovanni.ajr.models;

import com.google.gson.annotations.SerializedName;

public class Laporan {
    private String TIPE_MOBIL;
    private String NAMA_MOBIL;
    private float HARGA_SEWA_MOBIL;
    private int JUMLAH_PEMINJAMAN;
    private int TOTAL_DURASI_SEWA;
    private float PENDAPATAN_DARI_MOBIL;
    private String NAMA_CUSTOMER;
    private int JENIS_TRANSAKSI;
    private int JUMLAH_TRANSAKSI;
    private float PENDAPATAN_YANG_DIPEROLEH_DARI_CUSTOMER;
    @SerializedName("FORMAT_ID_DRIVER")
    private String format_id_driver;
    @SerializedName("ID_DRIVER")
    private Long id_driver;
    private String NAMA_DRIVER;
    private float RERATA_RATING_DRIVER;

    public Laporan(String TIPE_MOBIL, String NAMA_MOBIL, float HARGA_SEWA_MOBIL, int JUMLAH_PEMINJAMAN, int TOTAL_DURASI_SEWA, float PENDAPATAN_DARI_MOBIL, String NAMA_CUSTOMER, int JENIS_TRANSAKSI, int JUMLAH_TRANSAKSI, float PENDAPATAN_YANG_DIPEROLEH_DARI_CUSTOMER, String format_id_driver, Long id_driver, String NAMA_DRIVER, float RERATA_RATING_DRIVER) {
        this.TIPE_MOBIL = TIPE_MOBIL;
        this.NAMA_MOBIL = NAMA_MOBIL;
        this.HARGA_SEWA_MOBIL = HARGA_SEWA_MOBIL;
        this.JUMLAH_PEMINJAMAN = JUMLAH_PEMINJAMAN;
        this.TOTAL_DURASI_SEWA = TOTAL_DURASI_SEWA;
        this.PENDAPATAN_DARI_MOBIL = PENDAPATAN_DARI_MOBIL;
        this.NAMA_CUSTOMER = NAMA_CUSTOMER;
        this.JENIS_TRANSAKSI = JENIS_TRANSAKSI;
        this.JUMLAH_TRANSAKSI = JUMLAH_TRANSAKSI;
        this.PENDAPATAN_YANG_DIPEROLEH_DARI_CUSTOMER = PENDAPATAN_YANG_DIPEROLEH_DARI_CUSTOMER;
        this.format_id_driver = format_id_driver;
        this.id_driver = id_driver;
        this.NAMA_DRIVER = NAMA_DRIVER;
        this.RERATA_RATING_DRIVER = RERATA_RATING_DRIVER;
    }

    public String getTIPE_MOBIL() {
        return TIPE_MOBIL;
    }

    public void setTIPE_MOBIL(String TIPE_MOBIL) {
        this.TIPE_MOBIL = TIPE_MOBIL;
    }

    public String getNAMA_MOBIL() {
        return NAMA_MOBIL;
    }

    public void setNAMA_MOBIL(String NAMA_MOBIL) {
        this.NAMA_MOBIL = NAMA_MOBIL;
    }

    public float getHARGA_SEWA_MOBIL() {
        return HARGA_SEWA_MOBIL;
    }

    public void setHARGA_SEWA_MOBIL(float HARGA_SEWA_MOBIL) {
        this.HARGA_SEWA_MOBIL = HARGA_SEWA_MOBIL;
    }

    public int getJUMLAH_PEMINJAMAN() {
        return JUMLAH_PEMINJAMAN;
    }

    public void setJUMLAH_PEMINJAMAN(int JUMLAH_PEMINJAMAN) {
        this.JUMLAH_PEMINJAMAN = JUMLAH_PEMINJAMAN;
    }

    public int getTOTAL_DURASI_SEWA() {
        return TOTAL_DURASI_SEWA;
    }

    public void setTOTAL_DURASI_SEWA(int TOTAL_DURASI_SEWA) {
        this.TOTAL_DURASI_SEWA = TOTAL_DURASI_SEWA;
    }

    public float getPENDAPATAN_DARI_MOBIL() {
        return PENDAPATAN_DARI_MOBIL;
    }

    public void setPENDAPATAN_DARI_MOBIL(float PENDAPATAN_DARI_MOBIL) {
        this.PENDAPATAN_DARI_MOBIL = PENDAPATAN_DARI_MOBIL;
    }

    public String getNAMA_CUSTOMER() {
        return NAMA_CUSTOMER;
    }

    public void setNAMA_CUSTOMER(String NAMA_CUSTOMER) {
        this.NAMA_CUSTOMER = NAMA_CUSTOMER;
    }

    public int getJENIS_TRANSAKSI() {
        return JENIS_TRANSAKSI;
    }

    public void setJENIS_TRANSAKSI(int JENIS_TRANSAKSI) {
        this.JENIS_TRANSAKSI = JENIS_TRANSAKSI;
    }

    public int getJUMLAH_TRANSAKSI() {
        return JUMLAH_TRANSAKSI;
    }

    public void setJUMLAH_TRANSAKSI(int JUMLAH_TRANSAKSI) {
        this.JUMLAH_TRANSAKSI = JUMLAH_TRANSAKSI;
    }

    public float getPENDAPATAN_YANG_DIPEROLEH_DARI_CUSTOMER() {
        return PENDAPATAN_YANG_DIPEROLEH_DARI_CUSTOMER;
    }

    public void setPENDAPATAN_YANG_DIPEROLEH_DARI_CUSTOMER(float PENDAPATAN_YANG_DIPEROLEH_DARI_CUSTOMER) {
        this.PENDAPATAN_YANG_DIPEROLEH_DARI_CUSTOMER = PENDAPATAN_YANG_DIPEROLEH_DARI_CUSTOMER;
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

    public String getNAMA_DRIVER() {
        return NAMA_DRIVER;
    }

    public void setNAMA_DRIVER(String NAMA_DRIVER) {
        this.NAMA_DRIVER = NAMA_DRIVER;
    }

    public float getRERATA_RATING_DRIVER() {
        return RERATA_RATING_DRIVER;
    }

    public void setRERATA_RATING_DRIVER(float RERATA_RATING_DRIVER) {
        this.RERATA_RATING_DRIVER = RERATA_RATING_DRIVER;
    }
}
