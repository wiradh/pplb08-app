package ppl.b08.warunglaundry.view.pengguna;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import ppl.b08.warunglaundry.R;

public class ProfileActivity extends AppCompatActivity {

    private EditText namaEdt;
    private EditText noHPEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
}
