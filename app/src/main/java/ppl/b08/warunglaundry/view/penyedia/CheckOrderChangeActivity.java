package ppl.b08.warunglaundry.view.penyedia;

import android.content.DialogInterface;
import android.content.Intent;
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

public class CheckOrderChangeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    LatLng myLoc;
    static final float zoom = 15;
    long orderId;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order_change);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        Button kembali = (Button) findViewById(R.id.back_btn);
        final Button pesan = (Button) findViewById(R.id.ambil_btn);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(CheckOrderChangeActivity.this);
                builder.setTitle("Ambil pesanan Laundry ini?");
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder.setPositiveButton("Ambil", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        kirimRequestOrder();
                    }
                });
                builder.show();
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
        order = new Order(orderId, 1, "Bear Ruang", 0, 0,"08996222482", -6.3656374,106.8409036, "Rumah warna kuning, pager hijau nomer 41");

        TextView namaTxt = (TextView) findViewById(R.id.nama_txt);
        EditText phoneTxt = (EditText) findViewById(R.id.phone_txt);
        EditText detil = (EditText) findViewById(R.id.detil_txt);

        namaTxt.setText(order.getNamaCustomer());
        phoneTxt.setText(order.getPhone());
        detil.setText(order.getDetilLokasi());

    }

    public void kirimRequestOrder() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Terima kasih");
        String message = "Pesanan ini telah ditambahkan ke order.";
        builder.setMessage(message);

        builder.setPositiveButton("Pesan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i = new Intent(CheckOrderChangeActivity.this,HomeActivity.class);

                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish(); // to end the current activity
                Toast.makeText(CheckOrderChangeActivity.this, "Status pesanan telah berubah menjadi accepted", Toast.LENGTH_LONG).show();

            }
        });
        builder.show();
    }
}
