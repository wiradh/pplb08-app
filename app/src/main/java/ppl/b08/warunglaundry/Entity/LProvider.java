package ppl.b08.warunglaundry.Entity;

/**
 * Created by Andi Fajar on 30/04/2016.
 */
public class LProvider {
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

    public LProvider(long id, double lon, double lat, String nama, double harga) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.nama = nama;
        this.harga = harga;
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
}
