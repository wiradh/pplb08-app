package ppl.b08.warunglaundry.view.pengguna;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ppl.b08.warunglaundry.Entity.LCustomer;
import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.business.C;
import ppl.b08.warunglaundry.business.PreferencesManager;
import ppl.b08.warunglaundry.business.VolleySingleton;

public class ProfileActivity extends AppCompatActivity {

    private static TextView nametext;
    private static TextView emailtext;
    private static TextView telpontext;
    private static String token;
    private static String token2;
    LCustomer users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button updateBtn = (Button) findViewById(R.id.update1);
        Button changeBtn = (Button) findViewById(R.id.change1);
        ImageView Btn1 = (ImageView) findViewById(R.id.profileimage1);
        nametext = (TextView) findViewById(R.id.name1);
         emailtext = (TextView) findViewById(R.id.email1);
         telpontext = (TextView) findViewById(R.id.telpon1);

       // PreferencesManager manager = PreferencesManager.getInstance(LoginActivity.this.getToken());
        //token = "yPtUr1xcENVlBTv9+5+FP85eUiWqUhLzSQpWS0ppRe4=";
token = PreferencesManager.getInstance(ProfileActivity.this).getToken();
        getProfile();
     //   token2 = token;
//get detail
checkProfile();


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Toast toast = Toast.makeText(getApplicationContext(), "Redirected", Toast.LENGTH_SHORT);
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

                Intent intent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                startActivity(intent);
            }
        });


        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Toast toast = Toast.makeText(getApplicationContext(), "Password Has Been Changed", Toast.LENGTH_SHORT);
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

                Intent intent = new Intent(ProfileActivity.this, ProfileEditPassActivity.class);
                startActivity(intent);
            }
        });


    }
    private void checkProfile() {


        String url = C.HOME_URL + "/getDetails";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject ob = new JSONObject(response);
                    int r = ob.getInt("status");
                    if (r == 1) {

                        String token = ob.getString("token");
                        String role = token2 ;
                       // String role = ob.getString("id");
                       // PreferencesManager.getInstance(ProfileActivity.this).getToken(token);
                       // PreferencesManager.getInstance(ProfileActivity.this).getRoleValue(role);
                     //   PreferencesManager.getInstance(ProfileActivity.this).getName(ob.getString("name"));
                        //PreferencesManager.getInstance(ProfileActivity.this).setEmail(ob.getString("email"));
                        Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                        nametext.setText(ob.getString("nomer_hp"));
                       // nametext.setText("bayu");
                        emailtext.setText(ob.getString("nomer_hp"));
                      //  startActivity(intent);
                      //  finish();
                        Toast.makeText(ProfileActivity.this, "role"+role+"", Toast.LENGTH_SHORT).show();

                    } else {
                        //    Log.e("asd", "onResponse: asdds"+ ob.toString());
                        Toast.makeText(ProfileActivity.this, "Email/password tidak sesuai", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                  //  Toast.makeText(ProfileActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ProfileActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                Log.e("volley", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> re = new HashMap<>();
                re.put("token", PreferencesManager.getInstance(ProfileActivity.this).getToken());
                re.put("id", token);
                //re.put("email", getname2);
                //re.put("email",getemail2);
                //re.put("password", gettelpon2);
                return re;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
    private void getProfile() {


        String url = C.HOME_URL + "/getData/" + token;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject ob = new JSONObject(response);
                //    int r = ob.getInt("status");
                  //  if (r == 1) {

                        String isToken = ob.getString("id");
                    token2 = isToken;
                        String role = ob.getString("username");
                 //   Toast.makeText(ProfileActivity.this, "role"+role+"", Toast.LENGTH_SHORT).show();

                    // PreferencesManager.getInstance(ProfileActivity.this).setToken(token);
                        //PreferencesManager.getInstance(ProfileActivity.this).setRoleValue(role);
                        //PreferencesManager.getInstance(ProfileActivity.this).setName(ob.getString("name"));
                        //PreferencesManager.getInstance(ProfileActivity.this).setEmail(ob.getString("email"));
                        //    Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                        //nametext.setText(ob.getString("id"));

                        // nametext.setText("bayu");
                        //emailtext.setText(ob.getString("username"));
                        //  startActivity(intent);
                        //  finish();

//                    } else {
                        //    Log.e("asd", "onResponse: asdds"+ ob.toString());
  //                      Toast.makeText(ProfileActivity.this, "Email/password tidak sesuai", Toast.LENGTH_SHORT).show();
    //                }
                } catch (Exception e) {
                    //  Toast.makeText(ProfileActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ProfileActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                Log.e("volley", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> re = new HashMap<>();
                re.put("token", PreferencesManager.getInstance(ProfileActivity.this).getToken());
                re.put("id", token);
                //re.put("email", getname2);
                //re.put("email",getemail2);
                //re.put("password", gettelpon2);
                return re;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

}