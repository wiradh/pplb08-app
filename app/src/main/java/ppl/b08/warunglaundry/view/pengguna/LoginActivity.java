package ppl.b08.warunglaundry.view.pengguna;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.business.C;
import ppl.b08.warunglaundry.business.PreferencesManager;
import ppl.b08.warunglaundry.business.VolleySingleton;

/**
 * Created by Andi Fajar on 07/04/2016.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText emailEt;
    private EditText paswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get saved token in local database
        PreferencesManager manager = PreferencesManager.getInstance(this);
     //   Log.e("tojen", "onCreate: " + manager.getToken());
        if (manager.getToken() != null && !manager.getToken().isEmpty()) {
         //   Log.e("tojen", "onCreate: " + manager.getToken());

            //mengecek jika rolenya sebagai customer, maka dialihkan ke HomeActivity mili pengguna
            //jika value dari getRoleValue tidak sama dengan CU, maka login sebagai penyedia, dialihkan ke halaman HomeActivity milik penyedia

            Intent intent = manager.getRoleValue().equalsIgnoreCase("CU") ?
                    new Intent(this, ppl.b08.warunglaundry.view.pengguna.HomeActivity.class) :
                    new Intent(this, ppl.b08.warunglaundry.view.penyedia.HomeActivity.class);
            startActivity(intent);
            finish();
        }

        // inflate
        //pada baqian ini, mengambil konteks EditText dari layout login
        emailEt = (EditText) findViewById(R.id.email_txt);
        paswordEt = (EditText) findViewById(R.id.password_txt);
        //mengambil konteks button pada layout login
        Button registerBtn = (Button) findViewById(R.id.register);
        Button loginBtn = (Button) findViewById(R.id.login);
        //mengambil konteks textview
        TextView changeBtn = (TextView) findViewById(R.id.change_btn);

        //jika button ini diklik, maka mengalihkan LoginActivity ke RegisterActivity

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //Jika button ini diklik, akan menjalankan method login()
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //jika button ini diklik, akan mengubah tampilan login sesuai dengan role.
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ppl.b08.warunglaundry.view.penyedia.LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //method ini akan mengecek input dari user
    private void login() {
        final String email = emailEt.getText().toString();
        final String pass = paswordEt.getText().toString();
        //jika email kosong, akan memberikan notifikasi email wajib diisi
        if (email.isEmpty()) {
            Toast.makeText(this, "Email wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }
        //jika password kosong, akan memberikan notifikasi password wjaib diisi.
        if (pass.isEmpty()) {
            Toast.makeText(this, "Password wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }
        // create request

        String url = C.HOME_URL + "/login";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject ob = new JSONObject(response);
                    int r = ob.getInt("status");
                    if (r == 1) {
//mengambil value dari response
                        String token = ob.getString("token");
                        String role = ob.getString("role");
                        //jika role tidak sama dengan role customer, maka akan memberikan toast penyedia jasa laundry
                        if (!role.equalsIgnoreCase("CU")) {
                            Toast.makeText(LoginActivity.this, "Anda adalah penyedia jasa laundry silahkan login pada halaman penyedia jasa", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //local storage
                        PreferencesManager.getInstance(LoginActivity.this).setToken(token);
                        PreferencesManager.getInstance(LoginActivity.this).setRoleValue(role);
                        PreferencesManager.getInstance(LoginActivity.this).setName(ob.getString("name"));
                        PreferencesManager.getInstance(LoginActivity.this).setEmail(ob.getString("email"));
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                    //    Log.e("asd", "onResponse: asdds"+ ob.toString());
                        Toast.makeText(LoginActivity.this, "Email/password tidak sesuai", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                Log.e("volley", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> re = new HashMap<>();
                re.put("email", email);
                re.put("password", pass);
                return re;
            }
        };

        // send request
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}
