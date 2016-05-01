package ppl.b08.warunglaundry.view.penyedia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ppl.b08.warunglaundry.Entity.Order;
import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.adapter.ChangeOrderPAdapter;

public class ChangeOrderActivity extends AppCompatActivity {

    ListView listView;

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
            }
        });

        getAndSyncListView();
    }

    public void getAndSyncListView() {
        String[] namaPemesan = {"Bear ruang", "Wira DH", "Abdul Soclin", "Downy Wangkito"};
        double[] harga = {6000, 6500, 7000, 5000, 9000};
        double[] berat = {0, 2, 4, 0};
        int[] status = {2,4,3,2};

        ArrayList<Order> items = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            items.add(new Order(i, i, namaPemesan[i],  status[i], berat[i]));
        }

        ChangeOrderPAdapter adapter = new ChangeOrderPAdapter(items, this);
        listView.setAdapter(adapter);
    }
}
