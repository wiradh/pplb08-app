package ppl.b08.warunglaundry.view.pengguna;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ppl.b08.warunglaundry.Entity.LProvider;
import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.business.C;
import ppl.b08.warunglaundry.business.PreferencesManager;

public class OrderNewProfileActivity extends AppCompatActivity {

    TextView namaTxt;
    TextView lastLoginTxt;
    EditText alamatTxt;
    EditText phoneTxt;
    EditText hargaTxt;
    EditText pengerjaanTxt;
    EditText jangkauanTxt;
    EditText ratingTxt;
    EditText detilTxt;

    long laundryId;
    LProvider model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_new_profile);
        getSupportActionBar().setTitle("Detil Laundry");

        Button kembali = (Button) findViewById(R.id.back_btn);
        final Button pesan = (Button) findViewById(R.id.pesan_btn);

        namaTxt = (TextView) findViewById(R.id.nama_txt);
        lastLoginTxt = (TextView) findViewById(R.id.last_login_txt);
        alamatTxt = (EditText) findViewById(R.id.alamat_txt);
        phoneTxt = (EditText) findViewById(R.id.phone_txt);
        hargaTxt = (EditText) findViewById(R.id.harga_txt);
        pengerjaanTxt = (EditText) findViewById(R.id.pengerjaan_txt);
        jangkauanTxt = (EditText) findViewById(R.id.jangkauan_txt);
        ratingTxt = (EditText) findViewById(R.id.rating_txt);
        detilTxt = (EditText) findViewById(R.id.detil_alamat_txt);

        String detilAlamat = PreferencesManager.getInstance(this).getAlamatValue();
        if (!detilAlamat.isEmpty()) detilTxt.setText(detilAlamat);

        laundryId = getIntent().getLongExtra(C.KEY_LAUNDRY_ID,-1);
        if (laundryId == -1) finish();
        getAndSyncData();


        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order();
            }
        });
    }

    public void getAndSyncData() {
        model = new LProvider(laundryId,"Aisha Laundry", "12 April 2016", "Apartemen Taman Melati lantai 1 nomer 31","08997677231", 9000,"1-2 hari",2000,8.7);



        namaTxt.setText(model.getNama());
        lastLoginTxt.setText("Last login : "+model.getLastLogin());
        alamatTxt.setText(model.getAlamat());
        phoneTxt.setText(model.getTelp());
        hargaTxt.setText("Rp"+String.format("%.02f", model.getHarga()));
        pengerjaanTxt.setText(model.getPengerjaan());
        jangkauanTxt.setText(String.format("%.02f",model.getJangkauan())+" meter");
        ratingTxt.setText(String.format("%.02f", model.getRate())+"/10");
    }

    public void order() {
        if (detilTxt.getText().toString().isEmpty()) {

        }
        //TODO
    }

    @Override
    protected void onDestroy() {

        if (!detilTxt.getText().toString().isEmpty()) {
            PreferencesManager.getInstance(this).setAlamatValue(detilTxt.getText().toString());
        }
        super.onDestroy();
    }
}
