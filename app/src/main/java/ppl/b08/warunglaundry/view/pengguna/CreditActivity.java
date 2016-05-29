package ppl.b08.warunglaundry.view.pengguna;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ppl.b08.warunglaundry.R;

/**
 * Created by Andi Fajar on 29/04/2016.
 * View for Credits
 */
public class CreditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        getSupportActionBar().setTitle("My Credits");
    }
}
