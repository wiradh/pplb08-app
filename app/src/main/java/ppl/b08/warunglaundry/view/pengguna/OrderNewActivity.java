package ppl.b08.warunglaundry.view.pengguna;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ppl.b08.warunglaundry.Entity.LProvider;
import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.adapter.NewOrderAdapter;
import ppl.b08.warunglaundry.business.C;
import ppl.b08.warunglaundry.business.MyLocation;
import ppl.b08.warunglaundry.business.VolleySingleton;

/**
 *
 */
public class OrderNewActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    HashMap<String, LProvider> hashLaundry;
    HashMap<String, Marker> markers;
    LatLng  myLoc;
    static final float zoom = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_new);
        getSupportActionBar().setTitle("New Order");

        if (!C.isOnline(this)) {
            Toast.makeText(OrderNewActivity.this, "Tidak terkoneksi internet!!", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        hashLaundry = new HashMap<>();
        markers = new HashMap<>();
        mapFragment.getMapAsync(this);


    }

    public void getListLaundry() {

        String url = C.HOME_URL+"/getLaundry";
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject hasil = new JSONObject(response);

                    JSONArray arr = hasil.getJSONArray("laundry");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject ob = (JSONObject) arr.get(i);
                        long id = ob.getLong("id");
                        String name = ob.getString("nama");
                        String detil = ob.getString("detail");
                        Double rate = ob.getDouble("rate");
                        double harga = ob.getDouble("harga");
                        double jangkauan =ob.getDouble("jangkauan");
                        String alamat = ob.getString("alamat");
                        double longitude = ob.getDouble("longitude");
                        double latitude = ob.getDouble("latitude");
                        String telp = ob.getString("telepon");
                        String lastLogin = ob.getString("last_login");
                        LProvider provider = new LProvider(id,name,detil,harga,alamat,rate,jangkauan,longitude,latitude,telp,lastLogin);
                        hashLaundry.put(name, provider);
                    }
                    getMyLocation();
                } catch (JSONException e) {
                    Toast.makeText(OrderNewActivity.this, "Kesalahan jaringan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderNewActivity.this, "Kesalahan jaringan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> a = new HashMap<>();
                a.put("lattitude", "0");
                a.put("longitude", "1");
                return a;
            }
        };

//        hashLaundry.put("Sejahtera Laundry", new LProvider(1,-6.35628,106.83539,"Sejahtera Laundry",6000));
//        hashLaundry.put("Clean Laundry", new LProvider(1,-6.36055,106.83329,"Clean Laundry",6500));
//        hashLaundry.put("Aishy Laundry", new LProvider(1,-6.35897,106.82355,"Aishy Laundry",7000));
//        hashLaundry.put("Miku Laundry", new LProvider(1,-6.35210,106.83303,"Miku Laundry",5000));
//        hashLaundry.put("Wayang Laundry", new LProvider(1,-6.34835,106.82955,"Wayang Laundry",9000));
//
//        getMyLocation();

        VolleySingleton.getInstance(this).addToRequestQueue(req);
    }

    public void getMyLocation() {
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    C.MY_PERMISSION_ACCESS_COURSE_LOCATION);
        }

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(OrderNewActivity.this, "Gagal mendapat izin lokasi", Toast.LENGTH_SHORT).show();
            return  ;
        }

//        mMap.setMyLocationEnabled(true);

        MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
            @Override
            public void gotLocation(Location location){
                myLoc = new LatLng(location.getLatitude(), location.getLongitude());
                updateJarak();
                mMap.addMarker(new MarkerOptions().position(myLoc).title("Saya").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLoc, zoom));
                setListView();
            }
        };
        MyLocation myLocation = new MyLocation();

        try {
            myLocation.getLocation(this, locationResult);
        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(OrderNewActivity.this, "Gagal mendapatkan izin lokasi", Toast.LENGTH_SHORT).show();

        }


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getListLaundry();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").snippet("lalalala").flat(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.3656374,106.8409036), zoom));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.getTitle().equalsIgnoreCase("saya")) return;
                //TODO
               // Toast.makeText(OrderNewActivity.this, "Harga laundry " + hashLaundry.get(marker.getTitle()).getHarga(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderNewActivity.this, OrderNewProfileActivity.class);
                intent.putExtra(C.KEY_LAUNDRY_ID, hashLaundry.get(marker.getTitle()).getId());
                intent.putExtra(C.KEY_LAUNDRY, hashLaundry.get(marker.getTitle()));
                intent.putExtra(C.KEY_LONG, myLoc.longitude);
                intent.putExtra(C.KEY_LAT, myLoc.latitude);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(intent);
            }
        });
    }

    public void updateJarak() {
        for (LProvider lProvider : hashLaundry.values()) {
           // Log.e("ASDASD", "updateJarak: " + lProvider.getNama());
            lProvider.calculateDistance(myLoc.latitude, myLoc.longitude);
            LatLng latLng = new LatLng(lProvider.getLat(), lProvider.getLon());
            markers.put(lProvider.getNama(),mMap.addMarker(new MarkerOptions().position(latLng).title(lProvider.getNama()).snippet("Klik untuk melihat")));
        }
    }

    public void setListView(){
        Collection<LProvider> locations =  hashLaundry.values();
        ArrayList<LProvider> items = new ArrayList<>(locations);
//        Log.e("SADSADSD", "setListView: " + items);
        //NewOrderAdapter adapter = new NewOrderAdapter( items);
        final NewOrderAdapter adapter = new NewOrderAdapter(OrderNewActivity.this, items);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Marker a = markers.get(((LProvider)adapter.getItem(position)).getNama());
                mMap.animateCamera(CameraUpdateFactory.newLatLng(a.getPosition()));
                a.showInfoWindow();
            }
        });


    }
}
