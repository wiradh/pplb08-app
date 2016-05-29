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

public class ChangeOrderActivity extends AppCompatActivity {

    ListView listView;
    ChangeOrderPAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_order);
        getSupportActionBar().setTitle("Change Status");

        listView = (ListView) findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO
                Intent intent = new Intent(ChangeOrderActivity.this, ChangeOrderNextActivity.class);
                intent.putExtra(C.KEY_ORDER, id);
                intent.putExtra(C.KEY_ORDER, (Order)adapter.getItem(position));
                startActivity(intent);
            }
        });

        getAndSyncListView();
    }

    public void getAndSyncListView() {
        String url = C.HOME_URL + "/getOrderByPenyedia";
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
                            double berat = 0;
                            String tmp = a.getString("harga");
                            String tmp2 = a.getString("berat");
                            if(!tmp.equals("")){
                                harga = a.getDouble("harga");
                            }
                            if(!tmp2.equals("")){
                                berat = a.getDouble("berat");
                            }
                            if((a.getInt("status") != 5)&&(a.getInt("status") != 1)) {
                                items.add(new Order(a.getLong("id"), a.getString("nama_laundry"), a.getString("nama_pelanggan"), a.getLong("id_penyedia"), a.getLong("id_pelanggan"), berat, a.getInt("status"), a.getString("jam_ambil"), a.getString("jam_antar"), harga, a.getString("detail_lokasi"), a.getDouble("latitude"), a.getDouble("longitude")));
                            }
                        }

                        adapter = new ChangeOrderPAdapter(items, ChangeOrderActivity.this);
                        listView.setAdapter(adapter);


                    }else{
                        Toast.makeText(ChangeOrderActivity.this, "Jaringan Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(ChangeOrderActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangeOrderActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                Log.e("volley", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> input = new HashMap<>();
                String token = PreferencesManager.getInstance(ChangeOrderActivity.this).getToken();
                input.put("token",token);
                return input;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        adapter.notify();
//        adapter.notifyDataSetChanged();
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//    }

    @Override
    protected void onResume() {
//        adapter.notify();
       getAndSyncListView();
        super.onResume();
    }
}
