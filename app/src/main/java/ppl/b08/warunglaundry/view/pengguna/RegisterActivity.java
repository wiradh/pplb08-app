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

        if (!checkBox.isSelected()) {
            Toast.makeText(RegisterActivity.this, "Silahkan centang bahwa data yang dimasukkan telah benar", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = C.HOME_URL + "/register";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int r = response.getInt("status");
                    if (r == 1) {
                        //TODO handle kalo sukses
                        PreferencesManager.getInstance(RegisterActivity.this).setIdValue(123);

                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(RegisterActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> re = new HashMap<>();
                re.put("nama", name);
                re.put("role", "0");
                re.put("email", email);
                re.put("pasword", pasword);
                re.put("telepon", noHP);
                return re;
            }
        };

        //TODO dummy
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    //    VolleySingleton.getInstance(this).addToRequestQueue(request);
    }


}
