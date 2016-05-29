package ppl.b08.warunglaundry.Entity;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by Andi Fajar on 01/05/2016.
 * Model for Order
 */
public class Order implements Serializable{
    private long id;
    private int status;
    private String jamAmbil;
    private String jamAntar;
    private String tipe;
    private long idCustomer;
    private double berat;
    private double hargaPerKg;
    private double hargaTotal;
    private long idProvider;
    private String namaCustomer;
    private String namaProvider;
    private String color;
    private double lng;
    private double lat;
    private String phone;
    private String detilLokasi;



    public static final String[] statusStr = {"pending", "canceled", "accepted", "on-going", "done", "complete"};

    /**
     * Construktor untuk model Current Order Customer
     * @param id
     * @param namaProvider
     * @param status
     * @param berat
     */
    public Order(long id, String namaProvider, int status, double berat) {
        this.id = id;
        this.namaProvider = namaProvider;
        this.status = status;
        this.berat = berat;
        setColor();
    }

    /**
     * Construktor untuk model History Order Customer
     * @param id
     * @param namaProvider
     * @param status
     * @param berat
     * @param hargaTotal
     */
    public Order(long id, String namaProvider, int status, double berat, double hargaTotal) {
        this.id = id;
        this.namaProvider = namaProvider;
        this.status = status;
        this.berat = berat;
        this.hargaTotal = hargaTotal;
        setColor();
    }

    /**
     * Construktor untuk model History Order Provider
     * @param id
     * @param idCustomer
     * @param namaCustomer
     * @param hargaTotal
     * @param berat
     * @param status
     */
    public Order(long id, long idCustomer, String namaCustomer, double hargaTotal, double berat, int status) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.namaCustomer = namaCustomer;
        this.hargaTotal = hargaTotal;
        this.berat = berat;
        this.status = status;
        setColor();
    }

    /**
     * Construktor untuk model Change Order Provider
     * @param id
     * @param idCustomer
     * @param namaCustomer
     * @param status
     * @param berat
     */
    public Order(long id,  long idCustomer, String namaCustomer, int status, double berat) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.namaCustomer = namaCustomer;
        this.status = status;
        this.berat = berat;
        setColor();
    }


    public Order(long id,  long idCustomer, String namaCustomer, int status, double berat, String phone, double lat, double lng, String detil) {
        this.id = id;
        this.phone = phone;
        this.idCustomer = idCustomer;
        this.namaCustomer = namaCustomer;
        this.status = status;
        this.berat = berat;
        this.lat = lat;
        this.lng = lng;
        this.detilLokasi = detil;
        setColor();
    }

    public Order(long id, String namaProvider, String namaCustomer, long idProvider, long idCustomer, double berat, int status, String jamAmbil, String jamAntar, double hargaTotal, String detilLokasi, double lat, double lng) {
        this.id = id;
        this.namaProvider = namaProvider;
        this.namaCustomer = namaCustomer;
        this.idProvider = idProvider;
        this.idCustomer = idCustomer;
        this.berat = berat;
        this.status = status;
        this.jamAmbil = jamAmbil;
        this.jamAntar = jamAntar;
        this.hargaTotal = hargaTotal;
        this.detilLokasi = detilLokasi;
        this.lng = lng;
        this.lat = lat;
        setColor();
    }

    public void setColor() {
        switch (status) {
            case 0:
                color = "#FFC107";
                break;
            case 1:
                color = "#F44336";
                break;
            case 2:
                color = "#03A9F4";
                break;
            case 3:
                color = "#0288D1";
                break;
            case 4:
                color = "#4CAF50";
                break;
            case 5:
                color = "#4CAF50";
                break;
            default:
                color = "#4CAF50";
                break;
        }

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getJamAmbil() {
        return jamAmbil;
    }

    public void setJamAmbil(String jamAmbil) {
        this.jamAmbil = jamAmbil;
    }

    public String getJamAntar() {
        return jamAntar;
    }

    public void setJamAntar(String jamAntar) {
        this.jamAntar = jamAntar;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public double getBerat() {
        return berat;
    }

    public void setBerat(double berat) {
        this.berat = berat;
    }

    public double getHargaPerKg() {
        return hargaPerKg;
    }

    public void setHargaPerKg(double hargaPerKg) {
        this.hargaPerKg = hargaPerKg;
    }

    public double getHargaTotal() {
        return hargaTotal;
    }

    public void setHargaTotal(double hargaTotal) {
        this.hargaTotal = hargaTotal;
    }

    public long getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(long idProvider) {
        this.idProvider = idProvider;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public String getNamaProvider() {
        return namaProvider;
    }

    public void setNamaProvider(String namaProvider) {
        this.namaProvider = namaProvider;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatusStr() {
        return statusStr[status];
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetilLokasi() {
        return detilLokasi;
    }

    public void setDetilLokasi(String detilLokasi) {
        this.detilLokasi = detilLokasi;
    }
}
