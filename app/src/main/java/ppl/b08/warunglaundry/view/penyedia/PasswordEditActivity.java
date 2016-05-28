package ppl.b08.warunglaundry.view.penyedia;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.business.C;
import ppl.b08.warunglaundry.business.PreferencesManager;
import ppl.b08.warunglaundry.business.VolleySingleton;

public class PasswordEditActivity extends AppCompatActivity {

    private EditText nameEdt;
    private EditText noHPEdt;
    private EditText emailEdt;
    private EditText passEdt;
    private EditText rePassEdt;
    String name;
    String noHP;
    String email;
    String password;
    String repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_edit2);
        getSupportActionBar().setTitle("Ubah Password");
        Intent i = getIntent();
        Bundle data = i.getExtras();
        passEdt = (EditText) findViewById(R.id.password_txt);
        rePassEdt = (EditText) findViewById(R.id.repassword_txt);
        Button kembali = (Button) findViewById(R.id.back_btn);
        final Button update = (Button) findViewById(R.id.updt_btn);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        name = data.getString("nama");
        email = data.getString("email");
        noHP = data.getString("noHp");

    }

    private void update() {
        password = passEdt.getText().toString();
        repassword = rePassEdt.getText().toString();

        if (password.isEmpty() || repassword.isEmpty()) {
            Toast.makeText(PasswordEditActivity.this, "Semua data harus dalam keadaan terisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(repassword)) {
            Toast.makeText(PasswordEditActivity.this, "Password tidak sesuai", Toast.LENGTH_SHORT).show();
            return;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi perubahan");
        String message = "Apakah anda yakin ingin melakukan perubahan ini?";
        builder.setMessage(message);
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                kirimRequestUpdate();
            }
        });
        builder.show();
    }

    private void kirimRequestUpdate() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informasi");
        String message = "Data anda sudah diperbaharui dengan data terbaru";
        builder.setMessage(message);

        builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(PasswordEditActivity.this, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish(); // to end the current activity
            }
        });

        String url = C.HOME_URL + "/setDetails";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject hasil = null;
                try {
                    hasil = new JSONObject(response);
                    int status = hasil.getInt("status");
                    if (status == 1) {
                        builder.show();
                    } else {
                        Toast.makeText(PasswordEditActivity.this, "Email sudah terdaftar, silahkan coba kembali", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(PasswordEditActivity.this, "Terjadi kesalahan jaringan, silahkan coba kembali", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PasswordEditActivity.this, "Terjadi kesalahan jaringan, silahkan coba kembali", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> input = new HashMap<>();
                String token = PreferencesManager.getInstance(PasswordEditActivity.this).getToken();
                input.put("token", token);
                input.put("name", name);
                input.put("password", password);
                input.put("email",email);
                input.put("nomor_hp", noHP);
                return input;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}