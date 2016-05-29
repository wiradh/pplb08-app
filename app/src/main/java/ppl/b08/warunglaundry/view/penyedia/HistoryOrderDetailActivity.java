package ppl.b08.warunglaundry.view.penyedia;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ppl.b08.warunglaundry.Entity.Order;
import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.business.C;
import ppl.b08.warunglaundry.business.PreferencesManager;
import ppl.b08.warunglaundry.business.VolleySingleton;
/**
 * Created by Andi Fajar on 29/04/2016.
 * contributor tegar
 * Updated by Bimo Prasetyo
 */
public class HistoryOrderDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    LatLng myLoc;
    static final float zoom = 15;
    long orderId;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order_detail2);
        // get map fragment
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        // get information from previous intent
        order = (Order) getIntent().getSerializableExtra(C.KEY_ORDER);
        if (order == null) {
            Toast.makeText(HistoryOrderDetailActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getOrder();
        // set marker
        LatLng place = new LatLng(order.getLat(),order.getLng());
        mMap.addMarker(new MarkerOptions().position(place).title(order.getNamaCustomer()).snippet("Lokasi customer").flat(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, zoom));

    }

    public void getOrder() {
        // create request
        String url = C.HOME_URL + "/getDetails";
        TextView namaTxt = (TextView) findViewById(R.id.nama_txt);
        TextView status = (TextView) findViewById(R.id.status_txt);

        //EditText phoneTxt = (EditText) findViewById(R.id.phone_txt);
        EditText detil = (EditText) findViewById(R.id.detil_txt);
        EditText berat = (EditText) findViewById(R.id.berat_txt);
        EditText hargaSatuan = (EditText) findViewById(R.id.harga_satuan_txt);
        EditText hargaTxt = (EditText) findViewById(R.id.harga_txt);

        namaTxt.setText(order.getNamaCustomer());
        detil.setText(order.getDetilLokasi());
        status.setText("Status : "+order.getStatusStr());
        status.setTextColor(Color.parseColor(order.getColor()));
        String message = order.getBerat()==0?"-":String.format("%.02f", order.getBerat())+" kg";
        berat.setText(message);
        double harga = order.getHargaTotal();
        hargaSatuan.setText(String.format("%.02f", harga/order.getBerat()));
        hargaTxt.setText(String.format("%.02f", harga));
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
                        Toast.makeText(HistoryOrderDetailActivity.this, "Jaringan Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(HistoryOrderDetailActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HistoryOrderDetailActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                Log.e("volley", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> input = new HashMap<>();
                String token = PreferencesManager.getInstance(HistoryOrderDetailActivity.this).getToken();
                input.put("token", token);
                String tmp = "" + order.getIdCustomer();
                input.put("id", tmp);
                return input;
            }
        };
        // sent request
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
    public void ubahNomorHP(String nomorHP){
        EditText phoneTxt = (EditText) findViewById(R.id.phone_txt);
        phoneTxt.setText(nomorHP);
    }
}
