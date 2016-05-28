package ppl.b08.warunglaundry.view.penyedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ppl.b08.warunglaundry.Entity.Order;
import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.business.C;
import ppl.b08.warunglaundry.business.PreferencesManager;
import ppl.b08.warunglaundry.business.VolleySingleton;

/**
 * Created By M Risky and Bimo Prasetyo
 *
 *
 * */
public class ProfileActivity extends AppCompatActivity {
    String id;
    TextView namaEdt;
    TextView emailEdt;
    TextView noHPEdt;
    TextView alamat1;
    TextView harga1;
    TextView rate1;
    TextView jangkauan1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        getSupportActionBar().setTitle("Profil Penyedia");
        namaEdt = (TextView) findViewById(R.id.name1);
        emailEdt = (TextView) findViewById(R.id.email1);
        noHPEdt = (TextView) findViewById(R.id.telpon1);
        alamat1 = (TextView) findViewById(R.id.alamat1);
        harga1 = (TextView) findViewById(R.id.harga1);
        rate1 = (TextView) findViewById(R.id.rate1);
        jangkauan1 = (TextView) findViewById(R.id.jangkauan1);
        Button updateBtn = (Button) findViewById(R.id.ubah_btn);
        Button kembali = (Button) findViewById(R.id.back_btn);
        getData();
        System.out.println(PreferencesManager.getInstance(ProfileActivity.this).getToken());
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                Bundle data = new Bundle();
                data.putString("nama", (String) namaEdt.getText());
                data.putString("email", (String) emailEdt.getText());
                data.putString("noHp", (String) noHPEdt.getText());
                intent.putExtras(data);
                startActivity(intent);
            }
        });
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();
            }
        });
    }
    public void getData() {

        String url = C.HOME_URL+"/getData/"+ PreferencesManager.getInstance(ProfileActivity.this).getToken();
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject hasil = new JSONObject(response);
                    id = hasil.getString("id");
                    getDetails();
                } catch (JSONException e) {
                    Toast.makeText(ProfileActivity.this, "j", Toast.LENGTH_SHORT).show();

                    //    Toast.makeText(ProfileActivity.this, "Kesalahan jaringan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, "i", Toast.LENGTH_SHORT).show();

                //   Toast.makeText(ProfileActivity.this, "Kesalahan jaringan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(req);
    }

    public void getDetails() {

        String url = C.HOME_URL+"/getDetails";
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject hasil = new JSONObject(response);
                    int status = hasil.getInt("status");
                    if (status == 1 ){

                        JSONObject user = hasil.getJSONObject("user");
                        namaEdt.setText(user.getString("name"));
                        noHPEdt.setText(user.getString("nomor_hp"));
                        emailEdt.setText(user.getString("email"));
                        getAndSyncListView();
                        // alamat1.setText(user.getString("alamat"));
                        // harga1.setText(user.getString("harga"));
                        // jangkauan1.setText(user.getString("email"));
                        // rate1.setText(user.getString("rate"));
                        System.out.println(response);
                    }
                    else{
                        Toast.makeText(ProfileActivity.this, "A", Toast.LENGTH_SHORT).show();

                        //       Toast.makeText(ProfileActivity.this, "Terjadi kesalahan, silahkan coba kembali", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(ProfileActivity.this, "B", Toast.LENGTH_SHORT).show();

                    //    Toast.makeText(ProfileActivity.this, "Kesalahan jaringan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, "C", Toast.LENGTH_SHORT).show();

                //  Toast.makeText(ProfileActivity.this, "Kesalahan jaringan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> a = new HashMap<>();
                a.put("token", PreferencesManager.getInstance(ProfileActivity.this).getToken());
                a.put("id",id);
                return a;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(req);
    }
    public ArrayList<Order> parseJSONArray(JSONArray arr) throws JSONException {
        ArrayList<Order> items = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject item = arr.getJSONObject(i);
            String id2 = item.getString("id");
            if(id2==id) {
                Toast.makeText(ProfileActivity.this, "Matches", Toast.LENGTH_SHORT).show();

                // int status = item.getInt("status");
                // String jamAntar = item.getString("jam_antar");
                // String jamAmbil = item.getString("jam_ambil");
                // double lng = item.getDouble("longitude_laundry");
                // double lat = item.getDouble("latitude_laundry");
                // String detil = item.getString("detail_lokasi");
                alamat1.setText(item.getString("alamat"));
                harga1.setText(item.getString("harga"));
                jangkauan1.setText(item.getString("email"));
                rate1.setText(item.getString("rate"));
                // double berat = 0;
            }
            else
            {

                Toast.makeText(ProfileActivity.this, "Not Matches", Toast.LENGTH_SHORT).show();
            }
            try {
                //   berat = Double.parseDouble(item.getString("berat"));
            } catch (Exception e) {
            }
            /**  double harga = item.getDouble("harga");
             long idPenyedia = item.getLong("id_penyedia");
             long idPelanggan = item.getLong("id_pelanggan");
             String namaPelanggan = item.getString("nama_pelanggan");
             String namaLaundry = item.getString("nama_laundry");
             Order order = new Order(id, namaLaundry, namaPelanggan, idPenyedia, idPelanggan, berat, status, jamAmbil, jamAntar, harga, detil, lat, lng);
             items.add(order);
             */
        }
        return items;
    }
    //testing
    public void getAndSyncListView(){

        String url = C.HOME_URL + "/getLaundry";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject ob = new JSONObject(response);
                    JSONArray arr = ob.getJSONArray("[]");
                    ArrayList<Order> items = new ArrayList<>();

                    items.addAll(parseJSONArray(arr));
                    //    adapter =  new HistoryOrderCAdapter(items, ProfileActivity.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, C.KONEKSI_GAGAL, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, C.KONEKSI_GAGAL, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> a = new HashMap<>();
                a.put("token", PreferencesManager.getInstance(ProfileActivity.this).getToken());
                return a;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(request);
//
//        String[] namaLaundry = {"Sejahtera Laundry", "Clean Laundry", "Aishy Laundry", "Miku Laundry", "Wayang Laundry"};
//        double[] harga = {6000, 6500, 7000, 5000, 9000};
//        double[] berat = {3, 2, 4, 5, 1, 3};
//        String[] status = {"dilaporkan", "penjemputan", "dicuci", "pengeringan", "disetrika", "pengantaran","selesai"};
//        int[] pos1 = {0, 1, 0, 4, 3, 2};
//        int[] pos2 = {3, 1, 2, 4, 5, 3};
//
//        ArrayList<Order> items = new ArrayList<>();
//
//        for (int i = 0; i < 6; i++) {
//            items.add(new Order(6-i, namaLaundry[pos1[i]], 5, berat[pos2[i]], harga[pos1[i]] * berat[pos2[i]]));
//        }
//
//        HistoryOrderCAdapter adapter = new HistoryOrderCAdapter(items, this);
        //  listView.setAdapter(adapter);
    }

    //
}

