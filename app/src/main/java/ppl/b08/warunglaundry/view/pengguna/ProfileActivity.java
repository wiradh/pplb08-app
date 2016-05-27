package ppl.b08.warunglaundry.view.pengguna;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONException;
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
    private static String name;
    private static String token2;
    private static String id;

    //for user info to another activity
    private static String username;
    private static String no_hp;
    private static String emailUser;
    LCustomer users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button updateBtn = (Button) findViewById(R.id.update1);
        Button backBtn = (Button) findViewById(R.id.change1);
        ImageView Btn1 = (ImageView) findViewById(R.id.profileimage1);
        nametext = (TextView) findViewById(R.id.name1);
         emailtext = (TextView) findViewById(R.id.email1);
         telpontext = (TextView) findViewById(R.id.telpon1);

       // PreferencesManager manager = PreferencesManager.getInstance(LoginActivity.this.getToken());
        //token = "yPtUr1xcENVlBTv9+5+FP85eUiWqUhLzSQpWS0ppRe4=";
token = PreferencesManager.getInstance(ProfileActivity.this).getToken();
        getIdData();
      //  getDetails();
     //   getProfile();
     //   token2 = token;
//get detail
//checkProfile();


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
                Bundle data = new Bundle();
                data.putString("nama", username);
                data.putString("email", emailUser);
                data.putString("noHp", no_hp);
               intent.putExtras(data);
                startActivity(intent);
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     final Toast toast = Toast.makeText(getApplicationContext(), "Password Has Been Changed", Toast.LENGTH_SHORT);
             //   toast.show();
/**
               // Handler handler = new Handler();
               // handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 600);
               // final Toast toast2 = Toast.makeText(getApplicationContext(), "Redirected", Toast.LENGTH_SHORT);
               // toast.show();
                //memunculkan toast setelah 600ms
               // Handler handler2 = new Handler();
               // handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 600);
        */

                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


    }
    /**
    private void checkProfile() {


        String url = C.HOME_URL + "/getDetails";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject ob = new JSONObject(response);
                    int r = ob.getInt("status");
                    if (r == 1) {

                        String tokenize = ob.getString("token");
                        String role = token2 ;
                   //namaEdt.setText(user.getString("name"));
                        nametext.setText(ob.getString("name"));
                        telpontext.setText(ob.getString("nomor_hp"));
                        emailtext.setText(ob.getString("email"));
                      Toast.makeText(ProfileActivity.this, "role"+(ob.getString("nomor_hp"))+"", Toast.LENGTH_SHORT).show();

                    } else {
                        //    Log.e("asd", "onResponse: asdds"+ ob.toString());
                        Toast.makeText(ProfileActivity.this, "Email/password tidak sesuai", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ProfileActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
                Log.e("volley", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> re = new HashMap<>();
            //    re.put("token", PreferencesManager.getInstance(ProfileActivity.this).getToken());
             //   re.put("id", token2);
                //re.put("email", getname2);
                //re.put("email",getemail2);
                //re.put("password", gettelpon2);
                return re;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
    private void getProfile() {


        String url = C.HOME_URL + "/getData/" + PreferencesManager.getInstance(ProfileActivity.this).getToken();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

             //   Toast.makeText(ProfileActivity.this, istoken2, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject ob = new JSONObject(response);
                //    int r = ob.getInt("status");
                  //  if (r == 1) {

                    String isToken = ob.getString("id");
                    token2 = isToken;
                   name = ob.getString("name");
                    Toast.makeText(ProfileActivity.this, token2, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                   // Toast.makeText(ProfileActivity.this, istoken2, Toast.LENGTH_SHORT).show();
                      Toast.makeText(ProfileActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
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
               // re.put("token", PreferencesManager.getInstance(ProfileActivity.this).getToken());
               // re.put("id", token2);
                //re.put("email", getname2);
                //re.put("email",getemail2);
                //re.put("password", gettelpon2);
                return re;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
*/
    public void getIdData() {
    String url = C.HOME_URL+"/getData/"+token;
    //    String url = C.HOME_URL+"/getData/"+PreferencesManager.getInstance(ProfileActivity.this).getToken();
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject hasil = new JSONObject(response);
                    id = hasil.getString("id");
                    Toast.makeText(ProfileActivity.this, id, Toast.LENGTH_SHORT).show();

                    getUserDetails();
                } catch (JSONException e) {
                    Toast.makeText(ProfileActivity.this, "Kesalahan jaringan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, "Kesalahan jaringan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(req);
    }

    public void getUserDetails() {

        String url = C.HOME_URL+"/getDetails";
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject hasil = new JSONObject(response);
                    int status = hasil.getInt("status");
                    if (status == 1 ){
                        JSONObject user = hasil.getJSONObject("user");
                        username = user.getString("name");
                         no_hp = user.getString("nomor_hp");
                         emailUser = user.getString("email");
                        nametext.setText(user.getString("name"));
                        telpontext.setText(user.getString("nomor_hp"));
                        emailtext.setText(user.getString("email"));

                       PreferencesManager.getInstance(ProfileActivity.this).setEmail(emailUser);

                        //testing to toast users email
                      //  Toast.makeText(ProfileActivity.this, user.getString("email"), Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(ProfileActivity.this, "Terjadi kesalahan, silahkan coba kembali", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(ProfileActivity.this, "Kesalahan jaringan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, "Kesalahan jaringan, silahkan coba lagiY", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> a = new HashMap<>();
                a.put("token", PreferencesManager.getInstance(ProfileActivity.this).getToken());
                a.put("id",id);
                return a;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(req);
    }
}