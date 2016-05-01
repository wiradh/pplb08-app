package ppl.b08.warunglaundry.view.penyedia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ppl.b08.warunglaundry.Entity.Order;
import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.adapter.ChangeOrderPAdapter;
import ppl.b08.warunglaundry.business.C;

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
                startActivity(intent);
            }
        });

    }

    public void getAndSyncListView() {
        String[] namaPemesan = {"Bear ruang", "Wira DH", "Abdul Soclin", "Downy Wangkito"};
        double harga = 6000;
        double[] berat = {0, 0, 0, 0};

        ArrayList<Order> items = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            items.add(new Order(i, i, namaPemesan[i],  0, berat[i]));
        }

        adapter = new ChangeOrderPAdapter(items, this);
        listView.setAdapter(adapter);
    }
}
