package ppl.b08.warunglaundry.Entity;

import android.graphics.Color;

/**
 * Created by Andi Fajar on 01/05/2016.
 */
public class Order {
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

    public static final String[] statusStr = {"pending", "canceled", "accepted", "on-going", "done", "complete"};

    public Order(long id, String namaProvider, int status, double berat) {
        this.id = id;
        this.namaProvider = namaProvider;
        this.status = status;
        this.berat = berat;
        setColor();
    }
    public Order(long id, String namaProvider, int status, double berat, double hargaTotal) {
        this.id = id;
        this.namaProvider = namaProvider;
        this.status = status;
        this.berat = berat;
        this.hargaTotal = hargaTotal;
        setColor();
    }

    public void setColor() {
        //TODO
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
}
