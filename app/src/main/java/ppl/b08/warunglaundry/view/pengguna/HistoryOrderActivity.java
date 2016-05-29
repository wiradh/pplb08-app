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
import ppl.b08.warunglaundry.adapter.HistoryOrderCAdapter;
import ppl.b08.warunglaundry.business.C;
import ppl.b08.warunglaundry.business.PreferencesManager;
import ppl.b08.warunglaundry.business.VolleySingleton;
/**
 * Created by Andi Fajar on 29/04/2016.
 */
public class HistoryOrderActivity extends AppCompatActivity {

    ListView listView;
    HistoryOrderCAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
        getSupportActionBar().setTitle("History Order");
        //inflate view
        listView = (ListView) findViewById(R.id.list);
        // sync with server
        getAndSyncListView();

        // set on click listerner
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HistoryOrderActivity.this, HistoryOrderDetailActivity.class);
                intent.putExtra(C.KEY_ORDER, (Order)adapter.getItem(position));
                startActivity(intent);
            }
        });
    }
//menampilkan listview
    public void getAndSyncListView(){
        // create request
        String url = C.HOME_URL + "/getCompletedOrder";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject ob = new JSONObject(response);
                    JSONArray arr = ob.getJSONArray("completed");
                    ArrayList<Order> items = new ArrayList<>();
                    items.addAll(parseJSONArray(arr));
                    arr = ob.getJSONArray("canceled");
                    items.addAll(parseJSONArray(arr));
                    adapter =  new HistoryOrderCAdapter(items, HistoryOrderActivity.this);
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(HistoryOrderActivity.this, C.KONEKSI_GAGAL, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HistoryOrderActivity.this, C.KONEKSI_GAGAL, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> a = new HashMap<>();
                a.put("token", PreferencesManager.getInstance(HistoryOrderActivity.this).getToken());
                return a;
            }
        };

        // sent request
        VolleySingleton.getInstance(this).addToRequestQueue(request);

        listView.setAdapter(adapter);
    }

    /**
     * Parse JSON to Model
     * @param arr
     * @return
     * @throws JSONException
     */
    //menampilkan list order
    public ArrayList<Order> parseJSONArray(JSONArray arr) throws JSONException {
        ArrayList<Order> items = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject item = arr.getJSONObject(i);
            long id = item.getLong("id");
            int status = item.getInt("status");
            String jamAntar = item.getString("jam_antar");
            String jamAmbil = item.getString("jam_ambil");
            double lng = item.getDouble("longitude_laundry");
            double lat = item.getDouble("latitude_laundry");
            String detil = item.getString("detail_lokasi");
            double berat = 0;
            try {
                berat = Double.parseDouble(item.getString("berat"));
            } catch (Exception e) {
            }
            double harga = item.getDouble("harga");
            long idPenyedia = item.getLong("id_penyedia");
            long idPelanggan = item.getLong("id_pelanggan");
            String namaPelanggan = item.getString("nama_pelanggan");
            String namaLaundry = item.getString("nama_laundry");
            Order order = new Order(id, namaLaundry, namaPelanggan, idPenyedia, idPelanggan, berat, status, jamAmbil, jamAntar, harga, detil, lat, lng);
            items.add(order);
        }
        return items;
    }
}
