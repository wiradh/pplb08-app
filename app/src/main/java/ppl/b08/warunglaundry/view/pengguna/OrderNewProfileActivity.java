package ppl.b08.warunglaundry.view.pengguna;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ppl.b08.warunglaundry.Entity.LProvider;
import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.business.C;
import ppl.b08.warunglaundry.business.PreferencesManager;
import ppl.b08.warunglaundry.business.VolleySingleton;
/**
 * Created by Andi Fajar on 29/04/2016.
 */
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
    double lng;
    double lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_new_profile);
        getSupportActionBar().setTitle("Detil Laundry");

        // inflate every subview
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

        // get alamat from local database
        String detilAlamat = PreferencesManager.getInstance(this).getAlamatValue();
        if (!detilAlamat.isEmpty()) detilTxt.setText(detilAlamat);

//        laundryId = getIntent().getLongExtra(C.KEY_LAUNDRY_ID,-1);
//        if (laundryId == -1) finish();
        model =(LProvider) getIntent().getSerializableExtra(C.KEY_LAUNDRY);
        lng = getIntent().getDoubleExtra(C.KEY_LONG, Integer.MAX_VALUE);
        lat = getIntent().getDoubleExtra(C.KEY_LAT, Integer.MAX_VALUE);

        if (lng == Integer.MAX_VALUE) {
            Toast.makeText(OrderNewProfileActivity.this, "Terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (model == null) finish();
        // sync data with server
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
        // bind with our model
        namaTxt.setText(model.getNama());
        lastLoginTxt.setText("Last login : "+model.getLastLogin());
        alamatTxt.setText(model.getAlamat());
        phoneTxt.setText(model.getTelp());
        hargaTxt.setText("Rp"+String.format("%.02f", model.getHarga()));
        pengerjaanTxt.setText(model.getDetil());
        jangkauanTxt.setText(String.format("%.02f",model.getJangkauan())+" meter");
        ratingTxt.setText(String.format("%.02f", model.getRate())+"/10");
    }

    public void order() {
        if (detilTxt.getText().toString().isEmpty()) {
            Toast.makeText(OrderNewProfileActivity.this, "Isi detil alamat penjemputan terlebih dahulu", Toast.LENGTH_SHORT).show();
            return;
        }
        // finish the allert dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Memesan Jasa Laundry ini?");
        String message = "Nama : " + model.getNama() + "\nHarga per kg : Rp"+String.format("%.02f", model.getHarga())
                +"\n\nDetil lokasi penjemputan : " +detilTxt.getText();
        builder.setMessage(message);
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.setPositiveButton("Pesan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                kirimRequestOrder();
            }
        });
        builder.show();

    }

    private void kirimRequestOrder() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Terima kasih");
        String message = "Pesanan anda telah terkirim ke penyedia jasa laundry";
        builder.setMessage(message);
        
        builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(OrderNewProfileActivity.this,HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish(); // to end the current activity
                Toast.makeText(OrderNewProfileActivity.this, "Pesanan telah ditambahkan ke current order", Toast.LENGTH_LONG).show();

            }
        });
        // create requset
        String url = C.HOME_URL+"/order";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 1) {
                        builder.show();
                    } else {
                        Toast.makeText(OrderNewProfileActivity.this, "Tidak dapat melakukan order, silahkan coba kembali", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(OrderNewProfileActivity.this, "Terjadi kesalahan jaringan, silahkan coba kembali", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderNewProfileActivity.this, "Terjadi kesalahan jaringan, silahkan coba kembali", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> a = new HashMap<>();
                a.put("token", PreferencesManager.getInstance(OrderNewProfileActivity.this).getToken());
                a.put("longitude", lng+"");
                a.put("latitude", lat+"");
                a.put("id_penyedia", model.getId()+"");
                a.put("jam_antar", "00.00");
                a.put("jam_ambil", "00.00");
                a.put("tipe","0");
                a.put("harga","0");
                a.put("detail_lokasi", detilTxt.getText().toString());
                return a;
            }
        };
        // sent request
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    @Override
    protected void onDestroy() {

        if (!detilTxt.getText().toString().isEmpty()) {
            PreferencesManager.getInstance(this).setAlamatValue(detilTxt.getText().toString());
        }
        super.onDestroy();
    }
}
