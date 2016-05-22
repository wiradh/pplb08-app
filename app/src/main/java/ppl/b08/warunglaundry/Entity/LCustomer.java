package ppl.b08.warunglaundry.Entity;

import java.io.Serializable;

/**
 * Created by Andi Fajar on 30/04/2016.
 */
public class LCustomer implements Serializable {

    private long id;
    private String nama;
    private String email;
    private String telp;
    private String detil;
    private String alamat;

    public LCustomer(long id, String nama, String email, String telp, String detil, String alamat) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.telp = telp;
        this.detil = detil;
        this.alamat = alamat;
    }
}
