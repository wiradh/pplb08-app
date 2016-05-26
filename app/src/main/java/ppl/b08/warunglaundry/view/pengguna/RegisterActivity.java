package ppl.b08.warunglaundry.view.pengguna;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
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
public class RegisterActivity extends AppCompatActivity {

    private EditText namaEdt;
    private EditText emailEdt;
    private EditText passwordEdt;
    private EditText rePasswordEdt;
    private EditText noHPEdt;
    private CheckBox checkBox;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        namaEdt = (EditText) findViewById(R.id.name_txt);
        emailEdt = (EditText) findViewById(R.id.email_txt);
        passwordEdt = (EditText) findViewById(R.id.password_txt);
        rePasswordEdt = (EditText) findViewById(R.id.re_password_txt);
        noHPEdt = (EditText) findViewById(R.id.phone_txt);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        Button daftarBtn = (Button) findViewById(R.id.daftar_btn);

        daftarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }


    private void register() {

        final String name = namaEdt.getText().toString();
        final String email = emailEdt.getText().toString();
        final String pasword = passwordEdt.getText().toString();
        String rePassword = rePasswordEdt.getText().toString();
        final String noHP = noHPEdt.getText().toString();


        if (name.isEmpty() || email.isEmpty()|| pasword.isEmpty() ||
                rePassword.isEmpty() || noHP.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Semua data harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!C.isValidEmail(email)) {
            Toast.makeText(RegisterActivity.this, "Email tidak valid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pasword.equals(rePassword)) {
            Toast.makeText(RegisterActivity.this, "Password tidak sesuai", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!checkBox.isChecked()) {
            Toast.makeText(RegisterActivity.this, "Silahkan centang bahwa data yang dimasukkan telah benar", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = C.HOME_URL + "/register";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject hasil = null;
                try {
                    hasil = new JSONObject(response);
                    int status = hasil.getInt("status");
                    if(status == 1){
                        Log.e(TAG, "onResponse: sukses" );
                        PreferencesManager.getInstance(RegisterActivity.this).setToken(hasil.getString("token"));
                        PreferencesManager.getInstance(RegisterActivity.this).setRoleValue("CU");
                        PreferencesManager.getInstance(RegisterActivity.this).setPhone(noHP);
                        PreferencesManager.getInstance(RegisterActivity.this).setName(name);
                        PreferencesManager.getInstance(RegisterActivity.this).setEmail(email);
                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Email telah terdaftar", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(RegisterActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onErrorResponse: " + error.toString() );
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> input = new HashMap<>();
                input.put("name",name);
                input.put("password",pasword);
                input.put("nomor_hp",noHP);
                input.put("email",email);
                return input;
            }
        };

        //TODO dummy

     VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}
