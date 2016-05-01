package ppl.b08.warunglaundry.view.penyedia;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ppl.b08.warunglaundry.Entity.Order;
import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.business.C;

public class ChangeOrderNextActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    LatLng myLoc;
    static final float zoom = 15;
    long orderId;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_order_next);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        final Button ubah = (Button) findViewById(R.id.ubah_btn);

        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubah();
            }
        });

        orderId = getIntent().getLongExtra(C.KEY_ORDER, -1);
        if (orderId == -1) finish();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getOrder();

        LatLng place = new LatLng(order.getLat(),order.getLng());
        mMap.addMarker(new MarkerOptions().position(place).title(order.getNamaCustomer()).snippet("Lokasi customer").flat(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, zoom));

    }

    public void getOrder() {
        order = new Order(orderId, 1, "Bear Ruang", 2, 0,"08996222482", -6.3656374,106.8409036, "Rumah warna kuning, pager hijau nomer 41");
        double harga = 7000;

        TextView namaTxt = (TextView) findViewById(R.id.nama_txt);
        TextView status = (TextView) findViewById(R.id.status_txt);

        EditText phoneTxt = (EditText) findViewById(R.id.phone_txt);
        EditText detil = (EditText) findViewById(R.id.detil_txt);
        EditText berat = (EditText) findViewById(R.id.berat_txt);
        EditText hargaSatuan = (EditText) findViewById(R.id.harga_satuan_txt);
        EditText hargaTxt = (EditText) findViewById(R.id.harga_txt);

        namaTxt.setText(order.getNamaCustomer());
        phoneTxt.setText(order.getPhone());
        detil.setText(order.getDetilLokasi());
        status.setText("Status : "+order.getStatusStr());
        status.setTextColor(Color.parseColor(order.getColor()));
        String message = order.getBerat()==0?"-":String.format("%.02f", order.getBerat())+" kg";
        berat.setText(message);
        hargaSatuan.setText(String.format("%.02f", harga));
        hargaTxt.setText(String.format("%.02f", harga*order.getBerat()));
    }

    public void ubah(){
        int status = order.getStatus();


        if (status == 2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ubah status pesanan");
            builder.setMessage("Silahkan pilih salah satu status pesanan");
            builder.setNegativeButton("Cancelled", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ChangeOrderNextActivity.this, "Pesanan dibatalkan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            builder.setPositiveButton("On Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ChangeOrderNextActivity.this, "Status pesanan berubah On Progress", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            builder.setNeutralButton("Kembali", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            builder.show();


        } else
        if (status == 3 || status == 4) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ubah status pesanan");
            final String next = status == 3 ? "done" : "complete";
            builder.setMessage("Silahkan pilih salah satu status pesanan");
            builder.setPositiveButton(next, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ChangeOrderNextActivity.this, "Status pesanan berubah "+ next, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            builder.setNeutralButton("Kembali", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            builder.show();

        }
    }
}
