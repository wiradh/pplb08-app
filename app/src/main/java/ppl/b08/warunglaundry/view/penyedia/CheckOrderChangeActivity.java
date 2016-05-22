package ppl.b08.warunglaundry.view.penyedia;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ppl.b08.warunglaundry.Entity.Order;
import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.adapter.ChangeOrderPAdapter;
import ppl.b08.warunglaundry.business.C;
import ppl.b08.warunglaundry.business.PreferencesManager;
import ppl.b08.warunglaundry.business.VolleySingleton;

public class CheckOrderChangeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    LatLng myLoc;
    static final float zoom = 15;
    long orderId;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order_change);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        Button kembali = (Button) findViewById(R.id.back_btn);
        final Button pesan = (Button) findViewById(R.id.ambil_btn);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(CheckOrderChangeActivity.this);
                builder.setTitle("Ambil pesanan Laundry ini?");
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder.setPositiveButton("Ambil", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        kirimRequestOrder();
                    }
                });
                builder.show();
            }
        });

        order = (Order) getIntent().getSerializableExtra(C.KEY_ORDER);
        if (order == null) {
            Toast.makeText(CheckOrderChangeActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
        }

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getOrder();

        LatLng place = new LatLng(order.getLat(), order.getLng());
        mMap.addMarker(new MarkerOptions().position(place).title(order.getNamaCustomer()).snippet("Lokasi customer").flat(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, zoom));

    }

    public void getOrder() {
        String url = C.HOME_URL + "/getDetails";
        TextView namaTxt = (TextView) findViewById(R.id.nama_txt);

        EditText detil = (EditText) findViewById(R.id.detil_txt);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject hasil = new JSONObject(response);
                    JSONObject arr = hasil.getJSONObject("user");
                    int status = hasil.getInt("status");
                    if (status == 1) {
                        Log.e("CheckOrderActivity", "onResponse: sukses");
                        ubahNomorHP(arr.getString("nomor_hp"));
                    } else {
                        Toast.makeText(CheckOrderChangeActivity.this, "Jaringan Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(CheckOrderChangeActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CheckOrderChangeActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                Log.e("volley", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> input = new HashMap<>();
                String token = PreferencesManager.getInstance(CheckOrderChangeActivity.this).getToken();
                input.put("token", token);
                String tmp = "" + order.getIdCustomer();
                input.put("id", tmp);
                return input;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(request);


        namaTxt.setText(order.getNamaCustomer());

        detil.setText(order.getDetilLokasi());

    }

    public void ubahNomorHP(String nomorHP){
        EditText phoneTxt = (EditText) findViewById(R.id.phone_txt);
        phoneTxt.setText(nomorHP);
    }

    public void kirimRequestOrder() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Terima kasih");
        String message = "Pesanan ini telah ditambahkan ke order.";
        builder.setMessage(message);

        builder.setPositiveButton("Pesan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i = new Intent(CheckOrderChangeActivity.this, HomeActivity.class);

                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish(); // to end the current activity
                Toast.makeText(CheckOrderChangeActivity.this, "Status pesanan telah berubah menjadi accepted", Toast.LENGTH_LONG).show();

            }
        });
        builder.show();
    }
}
