package ppl.b08.warunglaundry.view.pengguna;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import ppl.b08.warunglaundry.adapter.CurrentOrderCAdapter;
import ppl.b08.warunglaundry.business.C;
import ppl.b08.warunglaundry.business.PreferencesManager;
import ppl.b08.warunglaundry.business.VolleySingleton;

public class CurrentOrderActivity extends AppCompatActivity {

    ListView listView;
    CurrentOrderCAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);
        getSupportActionBar().setTitle("Current Order");
        listView = (ListView) findViewById(R.id.list);

        getAndSyncListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CurrentOrderActivity.this, CurrentOrderDetailActivity.class);
                intent.putExtra(C.KEY_ORDER, (Order)adapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    public void getAndSyncListView() {

        String url = C.HOME_URL+"/getActiveOrder";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject ob = new JSONObject(response);
                    JSONArray arr = ob.getJSONArray("pending");
                    ArrayList<Order> items = new ArrayList<>();
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject item = arr.getJSONObject(i);
                        long id = item.getLong("id");
                        int status = item.getInt("status");
                        String jamAntar = item.getString("jam_antar");
                        String jamAmbil = item.getString("jam_ambil");
                        double lng = item.getDouble("longitude");
                        double lat = item.getDouble("latitude");
                        String detil = item.getString("detail_lokasi");
                        double berat = 0;
                        try {
                            berat = Double.parseDouble(item.getString("berat"));
                        } catch (Exception e){
                        }
                        double harga = item.getDouble("harga");
                        long idPenyedia = item.getLong("id_penyedia");
                        long idPelanggan = item.getLong("id_pelanggan");
                        String namaPelanggan = item.getString("nama_pelanggan");
                        String namaLaundry = item.getString("nama_laundry");
                        Order order = new Order(id,namaLaundry,namaPelanggan,idPenyedia, idPelanggan,berat,status,jamAmbil,jamAntar,harga,detil,lat,lng);
                        items.add(order);
//                        "id": 6,
//                                "status": "0",
//                                "jam_antar": "00.00",
//                                "jam_ambil": "00.00",
//                                "longitude": "106.828978",
//                                "latitude": "-6.3641789",
//                                "detail_lokasi": "Fasilkom UI",
//                                "tipe": "",
//                                "berat": "",
//                                "harga": "0",
//                                "id_penyedia": "3",
//                                "id_pelanggan": "1",
//                                "nama_pelanggan": "wiradh",
//                                "nama_laundry": "Clean Laundry",
                    }

                    adapter =  new CurrentOrderCAdapter(items, CurrentOrderActivity.this);
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CurrentOrderActivity.this, C.KONEKSI_GAGAL, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CurrentOrderActivity.this, C.KONEKSI_GAGAL, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> a = new HashMap<>();
                a.put("token", PreferencesManager.getInstance(CurrentOrderActivity.this).getToken());
                return a;
            }
        };
//
//        String[] namaLaundry = {"Sejahtera Laundry", "Clean Laundry", "Aishy Laundry", "Miku Laundry", "Wayang Laundry"};
//        double[] harga = {6000, 6500, 7000, 5000, 9000};
//        double[] berat = {0, 4, 3, 2, 0, 2};
//        String[] status = {"pending", "canceled", "accepted", "on-going", "done", "complete"};
//        int[] pos1 = {0, 1, 0, 4, 3, 2};
//        int[] pos2 = {0, 4, 4, 3, 2, 3};
//
//        ArrayList<Order> items = new ArrayList<>();
//
//        items.add(new Order(6, "Aishy Laundry", 0, 0));
//        for (int i = 1; i < 7; i++) {
//            items.add(new Order(6-i, namaLaundry[pos1[i-1]], pos2[i-1], berat[i-1]));
//        }
//
//         adapter = new CurrentOrderCAdapter(items, this);
        VolleySingleton.getInstance(this).addToRequestQueue(request);
//
//        hashLaundry.put("Sejahtera Laundry", new LProvider(1,-6.35628,106.83539,"Sejahtera Laundry",6000));
//        hashLaundry.put("Clean Laundry", new LProvider(1,-6.36055,106.83329,"Clean Laundry",6500));
//        hashLaundry.put("Aishy Laundry", new LProvider(1,-6.35897,106.82355,"Aishy Laundry",7000));
//        hashLaundry.put("Miku Laundry", new LProvider(1,-6.35210,106.83303,"Miku Laundry",5000));
//        hashLaundry.put("Wayang Laundry", new LProvider(1,-6.34835,106.82955,"Wayang Laundry",9000));

    }
}
