package ppl.b08.warunglaundry.view.penyedia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import ppl.b08.warunglaundry.adapter.ChangeOrderPAdapter;
import ppl.b08.warunglaundry.business.C;
import ppl.b08.warunglaundry.business.PreferencesManager;
import ppl.b08.warunglaundry.business.VolleySingleton;
import ppl.b08.warunglaundry.view.pengguna.*;

public class CheckOrderActivity extends AppCompatActivity {

    ListView listView;
    ChangeOrderPAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);
        getSupportActionBar().setTitle("Check Order");

        listView = (ListView) findViewById(R.id.list);

        getAndSyncListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CheckOrderActivity.this, CheckOrderChangeActivity.class);
                intent.putExtra(C.KEY_ORDER, id);
                intent.putExtra(C.KEY_ORDER, (Order)adapter.getItem(position));
                startActivity(intent);
            }
        });

    }

    public void getAndSyncListView() {
        String url = C.HOME_URL + "/getPendingOrder";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject hasil = new JSONObject(response);
                    int status = hasil.getInt("status");
                    if(status == 1){
                        Log.e("CheckOrderActivity", "onResponse: sukses" );
                        JSONArray arr = hasil.getJSONArray("order");

                        ArrayList<Order> items = new ArrayList<>();

                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject a = arr.getJSONObject(i);
                            double harga = 0;
                            String tmp = a.getString("harga");
                            if(!tmp.equals("")){
                                harga = a.getDouble("harga");
                            }
                            items.add(new Order(a.getLong("id"),a.getString("nama_laundry"),a.getString("nama_pelanggan"), a.getLong("id_penyedia"), a.getLong("id_pelanggan"), harga, a.getInt("status"), a.getString("jam_ambil"), a.getString("jam_antar"), harga, a.getString("detail_lokasi"), a.getDouble("latitude"), a.getDouble("longitude")));
                        }

                        adapter = new ChangeOrderPAdapter(items, CheckOrderActivity.this);
                        listView.setAdapter(adapter);


                    }else{
                        Toast.makeText(CheckOrderActivity.this, "Jaringan Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(CheckOrderActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CheckOrderActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                Log.e("volley", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> input = new HashMap<>();
                String token = PreferencesManager.getInstance(CheckOrderActivity.this).getToken();
                input.put("token",token);
                return input;
            }
        };
//        String[] namaPemesan = {"Bear ruang", "Wira DH", "Abdul Soclin", "Downy Wangkito"};
//        double harga = 6000;
//        double[] berat = {0, 0, 0, 0};

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}
