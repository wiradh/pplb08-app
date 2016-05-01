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
import ppl.b08.warunglaundry.adapter.HistoryOrderCAdapter;
import ppl.b08.warunglaundry.business.C;

public class HistoryOrderActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
        getSupportActionBar().setTitle("History Order");

        listView = (ListView) findViewById(R.id.list);

        getAndSyncListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HistoryOrderActivity.this, HistoryOrderDetailActivity.class);
                intent.putExtra(C.KEY_ORDER, id);
                startActivity(intent);
            }
        });
    }

    public void getAndSyncListView(){
        String[] namaLaundry = {"Sejahtera Laundry", "Clean Laundry", "Aishy Laundry", "Miku Laundry", "Wayang Laundry"};
        double[] harga = {6000, 6500, 7000, 5000, 9000};
        double[] berat = {3, 2, 4, 5, 1, 3};
        String[] status = {"dilaporkan", "penjemputan", "dicuci", "pengeringan", "disetrika", "pengantaran","selesai"};
        int[] pos1 = {0, 1, 0, 4, 3, 2};
        int[] pos2 = {3, 1, 2, 4, 5, 3};

        ArrayList<Order> items = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            items.add(new Order(6-i, namaLaundry[pos1[i]], 5, berat[pos2[i]], harga[pos1[i]] * berat[pos2[i]]));
        }

        HistoryOrderCAdapter adapter = new HistoryOrderCAdapter(items, this);
        listView.setAdapter(adapter);
    }
}
