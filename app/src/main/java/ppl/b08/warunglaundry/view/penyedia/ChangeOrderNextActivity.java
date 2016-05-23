package ppl.b08.warunglaundry.view.penyedia;

import android.content.DialogInterface;
import android.graphics.Color;
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

public class ChangeOrderNextActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    LatLng myLoc;
    static final float zoom = 15;
    long orderId;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_order_next);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        final Button ubah = (Button) findViewById(R.id.ubah_btn);

        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubah();
            }
        });

        order = (Order) getIntent().getSerializableExtra(C.KEY_ORDER);
        if (order == null) {
            Toast.makeText(ChangeOrderNextActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getOrder();

        LatLng place = new LatLng(order.getLat(),order.getLng());
        mMap.addMarker(new MarkerOptions().position(place).title(order.getNamaCustomer()).snippet("Lokasi customer").flat(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, zoom));

    }

    public void getOrder() {
        TextView namaTxt = (TextView) findViewById(R.id.nama_txt);
        TextView status = (TextView) findViewById(R.id.status_txt);

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
        hargaSatuan.setText(String.format("%.02f", harga));
        hargaTxt.setText(String.format("%.02f", harga*order.getBerat()));
        String url = C.HOME_URL + "/getDetails";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject hasil = new JSONObject(response);
                    JSONObject arr = hasil.getJSONObject("user");
                    int status = hasil.getInt("status");
                    if (status == 1) {
                        Log.e("ChangeOrderNext", "onResponse: sukses");
                        ubahNomorHP(arr.getString("nomor_hp"));
                    } else {
                        Toast.makeText(ChangeOrderNextActivity.this, "Jaringan Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(ChangeOrderNextActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangeOrderNextActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                Log.e("volley", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> input = new HashMap<>();
                String token = PreferencesManager.getInstance(ChangeOrderNextActivity.this).getToken();
                input.put("token", token);
                String tmp = "" + order.getIdCustomer();
                input.put("id", tmp);
                return input;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
    public void ubahNomorHP(String nomorHP){
        EditText phoneTxt = (EditText) findViewById(R.id.phone_txt);
        phoneTxt.setText(nomorHP);
    }

    public void ubah(){
        int status = order.getStatus();


        if (status == 2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ubah status pesanan");
            final int statusB = status;
            builder.setMessage("Silahkan pilih salah satu status pesanan");
            builder.setNegativeButton("Cancelled", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ubahStatusAPI(statusB - 1);
                }
            });
            builder.setPositiveButton("On Going", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ubahStatusAPI(statusB + 1);
                }
            });
            builder.setNeutralButton("Kembali", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            builder.show();


        } else
        if (status == 3 || status == 4) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ubah status pesanan");
            final int statusB = status;
            final String next = status == 3 ? "done" : "complete";
            builder.setMessage("Silahkan pilih salah satu status pesanan");
            builder.setPositiveButton(next, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ubahStatusAPI(statusB + 1);
                }
            });
            builder.setNeutralButton("Kembali", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            builder.show();

        }
    }
    public void ubahStatusAPI(int a){
        final String asd = "" + a;
        String url = C.HOME_URL + "/changeOrder";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject hasil = new JSONObject(response);
                    int status = hasil.getInt("status");
                    if (status == 1) {
                        Log.e("CheckOrderActivity", "onResponse: sukses");
                        int temp2 = Integer.parseInt(asd);
                        if(temp2 == 1){
                            Toast.makeText(ChangeOrderNextActivity.this, "Pesanan dibatalkan", Toast.LENGTH_SHORT).show();
                        }else if(temp2 == 3){
                            Toast.makeText(ChangeOrderNextActivity.this, "Status pesanan berubah On Going", Toast.LENGTH_SHORT).show();
                        }else if(temp2 == 4){
                            Toast.makeText(ChangeOrderNextActivity.this, "Status pesanan berubah Done", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ChangeOrderNextActivity.this, "Status pesanan berubah Completed", Toast.LENGTH_SHORT).show();
                        }
                        finish();

                    } else {
                        Toast.makeText(ChangeOrderNextActivity.this, "Jaringan Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(ChangeOrderNextActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangeOrderNextActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                Log.e("volley", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> input = new HashMap<>();
                String token = PreferencesManager.getInstance(ChangeOrderNextActivity.this).getToken();
                input.put("token", token);
                String tmp = "" + order.getId();
                input.put("order_id", tmp);
                input.put("status",asd);
                return input;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}
