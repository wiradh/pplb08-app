package ppl.b08.warunglaundry.view.penyedia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import ppl.b08.warunglaundry.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText namaEdt;
    private EditText emailEdt;
    private EditText passwordEdt;
    private EditText rePasswordEdt;
    private EditText noHPEdt;
    private EditText alamatEdt;
    private EditText jangkauanEdt;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        namaEdt = (EditText) findViewById(R.id.name_txt);
        emailEdt = (EditText) findViewById(R.id.email_txt);
        passwordEdt = (EditText) findViewById(R.id.password_txt);
        rePasswordEdt = (EditText) findViewById(R.id.re_password_txt);
        noHPEdt = (EditText) findViewById(R.id.phone_txt);
        alamatEdt = (EditText) findViewById(R.id.alamat_txt);
        jangkauanEdt = (EditText) findViewById(R.id.jangkauan_txt);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        Button daftarBtn = (Button) findViewById(R.id.daftar_btn);

        daftarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register(){
        String name = namaEdt.getText().toString();
        String email = emailEdt.getText().toString();
        String pasword = passwordEdt.getText().toString();
        String rePassword = rePasswordEdt.getText().toString();
        String noHP = noHPEdt.getText().toString();
        String alamat = alamatEdt.getText().toString();
        boolean isCheck = checkBox.isSelected();
    }
}
