package ppl.b08.warunglaundry.view.pengguna;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.business.PreferencesManager;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profil Pengguna");
        TextView namaEdt = (TextView) findViewById(R.id.name1);
        TextView emailEdt = (TextView) findViewById(R.id.email1);
        TextView noHPEdt = (TextView) findViewById(R.id.telpon1);
        Button updateBtn = (Button) findViewById(R.id.ubah_btn);
        Button kembali = (Button) findViewById(R.id.back_btn);
        namaEdt.setText(PreferencesManager.getInstance(ProfileActivity.this).getToken());
        emailEdt.setText(PreferencesManager.getInstance(ProfileActivity.this).getEmail());
        noHPEdt.setText("0815765626221");





        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                startActivity(intent);
            }
        });
    }
}
