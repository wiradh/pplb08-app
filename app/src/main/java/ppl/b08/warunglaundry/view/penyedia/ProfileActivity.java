package ppl.b08.warunglaundry.view.penyedia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import ppl.b08.warunglaundry.Entity.LCustomer;
import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.business.PreferencesManager;

public class ProfileActivity extends AppCompatActivity {

    private static TextView nametext;
    private static TextView emailtext;
    private static TextView telpontext;
    LCustomer users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        //Button updateBtn = (Button) findViewById(R.id.update1);
        //Button changeBtn = (Button) findViewById(R.id.change1);
        ImageView Btn1 = (ImageView) findViewById(R.id.profileimage1);
        nametext = (TextView) findViewById(R.id.name1);
        emailtext = (TextView) findViewById(R.id.email1);
        telpontext = (TextView) findViewById(R.id.telpon1);

        PreferencesManager manager = PreferencesManager.getInstance(this);
        //checkProfile();
    }
    }

