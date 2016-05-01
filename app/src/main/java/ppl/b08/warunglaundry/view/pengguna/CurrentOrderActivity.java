package ppl.b08.warunglaundry.view.pengguna;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ppl.b08.warunglaundry.Entity.Order;
import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.adapter.CurrentOrderCAdapter;
import ppl.b08.warunglaundry.business.C;

public class CurrentOrderActivity extends AppCompatActivity {

    ListView listView;
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
                intent.putExtra(C.KEY_ORDER, id);
                startActivity(intent);
            }
        });
    }

    public void getAndSyncListView() {
        String[] namaLaundry = {"Sejahtera Laundry", "Clean Laundry", "Aishy Laundry", "Miku Laundry", "Wayang Laundry"};
        double[] harga = {6000, 6500, 7000, 5000, 9000};
        double[] berat = {0, 4, 3, 2, 0, 2};
        String[] status = {"pending", "canceled", "accepted", "on-going", "done", "complete"};
        int[] pos1 = {0, 1, 0, 4, 3, 2};
        int[] pos2 = {0, 4, 4, 3, 2, 3};

        ArrayList<Order> items = new ArrayList<>();

        items.add(new Order(6, "Aishy Laundry", 0, 0));
        for (int i = 1; i < 7; i++) {
            items.add(new Order(6-i, namaLaundry[pos1[i-1]], pos2[i-1], berat[i-1]));
        }

        CurrentOrderCAdapter adapter = new CurrentOrderCAdapter(items, this);
        listView.setAdapter(adapter);
//
//        hashLaundry.put("Sejahtera Laundry", new LProvider(1,-6.35628,106.83539,"Sejahtera Laundry",6000));
//        hashLaundry.put("Clean Laundry", new LProvider(1,-6.36055,106.83329,"Clean Laundry",6500));
//        hashLaundry.put("Aishy Laundry", new LProvider(1,-6.35897,106.82355,"Aishy Laundry",7000));
//        hashLaundry.put("Miku Laundry", new LProvider(1,-6.35210,106.83303,"Miku Laundry",5000));
//        hashLaundry.put("Wayang Laundry", new LProvider(1,-6.34835,106.82955,"Wayang Laundry",9000));

    }
}
