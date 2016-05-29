package ppl.b08.warunglaundry.view.pengguna;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import org.json.JSONArray;
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
 * Updated by Bimo Prasetyo
 * View for Current Order
 */
public class CurrentOrderDetailActivity extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    static final float zoom = 15;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order_detail);

        // get map fragment
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        // get order from previus intent
        order = (Order) getIntent().getSerializableExtra(C.KEY_ORDER);
        if (order == null) finish();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getOrder();
        // set marker
        LatLng place = new LatLng(order.getLat(),order.getLng());
        mMap.addMarker(new MarkerOptions().position(place).title(order.getNamaProvider()).snippet("Lokasi penyedia laundry").flat(true));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place, zoom));

    }

    public void getOrder() {
        // create request
        String url = C.HOME_URL+"/getLaundry";
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject hasil = new JSONObject(response);

                    JSONArray arr = hasil.getJSONArray("laundry");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject laundry = (JSONObject) arr.get(i);
                        if (order.getNamaProvider().equals(laundry.getString("nama"))){
                            EditText phoneTxt = (EditText) findViewById(R.id.phone_txt);
                            phoneTxt.setText(laundry.getString("telepon"));
                            return;
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(CurrentOrderDetailActivity.this, "Kesalahan jaringan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CurrentOrderDetailActivity.this, "Kesalahan jaringan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> a = new HashMap<>();
                a.put("lattitude", "0");
                a.put("longitude", "1");
                return a;
            }
        };
        // send request
        VolleySingleton.getInstance(this).addToRequestQueue(req);

        // inflate view
        TextView namaTxt = (TextView) findViewById(R.id.nama_txt);
        TextView status = (TextView) findViewById(R.id.status_txt);
        EditText detil = (EditText) findViewById(R.id.detil_txt);
        EditText berat = (EditText) findViewById(R.id.berat_txt);
        EditText hargaSatuan = (EditText) findViewById(R.id.harga_satuan_txt);
        EditText hargaTxt = (EditText) findViewById(R.id.harga_txt);

        // bind view
        namaTxt.setText(order.getNamaProvider());
        detil.setText(order.getDetilLokasi());
        status.setText("Status : "+order.getStatusStr());
        status.setTextColor(Color.parseColor(order.getColor()));
        String message = order.getBerat()==0?"-":String.format("%.02f", order.getBerat())+" kg";
        berat.setText(message);
        double tmp = order.getBerat()==0? 0 : order.getHargaTotal()/order.getBerat();
        hargaSatuan.setText(String.format("%.02f", tmp));
        hargaTxt.setText(String.format("%.02f", order.getHargaTotal()));
    }

}
