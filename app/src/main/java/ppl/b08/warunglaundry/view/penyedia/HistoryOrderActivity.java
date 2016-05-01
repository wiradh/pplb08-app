package ppl.b08.warunglaundry.view.penyedia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ppl.b08.warunglaundry.Entity.Order;
import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.adapter.HistoryOrderPAdapter;

public class HistoryOrderActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order2);
        getSupportActionBar().setTitle("History Order");

        listView = (ListView) findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        getAndSyncListView();
    }

    public void getAndSyncListView(){
        String[] namaPemesan = {"Bear ruang", "Wira DH", "Abdul Soclin", "Downy Wangkito", "Bang Kohir"};
        double harga = 6000;
        double[] berat = {3, 2, 0, 5, 0};
        int[] status = {5,5,0,5,0};

        ArrayList<Order> items = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            items.add(new Order(i, i, namaPemesan[i], harga*berat[i], berat[i], status[i] ));
        }

        HistoryOrderPAdapter adapter = new HistoryOrderPAdapter(items, this);
        listView.setAdapter(adapter);

    }
}
