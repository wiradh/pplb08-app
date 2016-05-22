package ppl.b08.warunglaundry.Entity;

import java.io.Serializable;

import ppl.b08.warunglaundry.business.C;

/**
 * Created by Andi Fajar on 30/04/2016.
 */
public class LProvider implements Serializable{
    private double lon;
    private double lat;
    private String telp;
    private String nama;
    private String alamat;
    private long id;
    private double rate;
    private double jangkauan;
    private double harga;
    private String email;
    private String detil;
    private String lastLogin;
    private double jarak;
    private String pengerjaan;


    public LProvider(long id, String nama, String lastLogin, String alamat, String telp, double harga, String pengerjaan, double jangkauan, double rate) {
        this.id = id;
        this.nama = nama;
        this.lastLogin = lastLogin;
        this.alamat = alamat;
        this.telp = telp;
        this.harga = harga;
        this.pengerjaan = pengerjaan;
        this.jangkauan = jangkauan;
        this.rate = rate;
    }

    public LProvider(long id, double lat, double lon, String nama, double harga) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.nama = nama;
        this.harga = harga;
    }

    public String getPengerjaan() {
        return pengerjaan;
    }

    public void setPengerjaan(String pengerjaan) {
        this.pengerjaan = pengerjaan;
    }
    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getJangkauan() {
        return jangkauan;
    }

    public void setJangkauan(double jangkauan) {
        this.jangkauan = jangkauan;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetil() {
        return detil;
    }

    public void setDetil(String detil) {
        this.detil = detil;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public double getJarak() {
        return jarak;
    }

    public void setJarak(double jarak) {
        this.jarak = jarak;
    }

    public void calculateDistance(double lat, double lon) {
        this.jarak=C.distFrom(lat,lon,this.lat,this.lon);
    }

}
