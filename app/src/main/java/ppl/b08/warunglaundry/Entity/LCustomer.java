package ppl.b08.warunglaundry.Entity;

import java.io.Serializable;

/**
 * Created by Andi Fajar on 30/04/2016.
 * Model for laundry customer
 */
public class LCustomer implements Serializable {

    private long id;
    private String nama;
    private String email;
    private String telp;

    public LCustomer(long id, String nama, String email, String telp) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.telp = telp;
    }
}
