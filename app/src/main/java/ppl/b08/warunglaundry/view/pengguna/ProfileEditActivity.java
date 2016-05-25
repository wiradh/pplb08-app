package ppl.b08.warunglaundry.view.pengguna;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ppl.b08.warunglaundry.R;

public class ProfileEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        Button updateBtn = (Button) findViewById(R.id.update1);


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Toast toast = Toast.makeText(getApplicationContext(), "Profile Has Been Updated", Toast.LENGTH_SHORT);
                toast.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 600);
                final Toast toast2 = Toast.makeText(getApplicationContext(), "Redirected", Toast.LENGTH_SHORT);
                toast.show();

                Handler handler2 = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 600);

                Intent intent = new Intent(ProfileEditActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
