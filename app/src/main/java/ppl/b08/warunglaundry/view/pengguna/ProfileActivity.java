package ppl.b08.warunglaundry.view.pengguna;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ppl.b08.warunglaundry.R;

public class ProfileActivity extends AppCompatActivity {

    private EditText nameEdt;
    private EditText noHPEdt;
    Button kembali = (Button) findViewById(R.id.back_btn);
    final Button ubah = (Button) findViewById(R.id.ubah_btn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameEdt = (EditText) findViewById(R.id.name_txt);
        noHPEdt = (EditText) findViewById(R.id.phone_txt);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
