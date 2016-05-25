package ppl.b08.warunglaundry.view.pengguna;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
    //private void login() {
        //final String email = email1.getText().toString();
        //final String pass = pass1.getText().toString();
        //if (email.isEmpty()) {
           // Toast.makeText(this, "Email wajib diisi", Toast.LENGTH_SHORT).show();
          //  return;
        //}
        //if (pass.isEmpty()) {
           // Toast.makeText(this, "Passeord wajib diisi", Toast.LENGTH_SHORT).show();
          //  return;
        //}
        //String url = C.HOME_URL + "/getDetail";
        //StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
          //  @Override
        //    public void onResponse(String response) {

      //          try {
    //                JSONObject ob = new JSONObject(response);
  //                  int r = ob.getInt("status");
//                    if (r == 1) {

                        //String token = ob.getString("token");
                        //String role = ob.getString("role");
                        //PreferencesManager.getInstance(ProfileEditActivity.this).setToken(token);
                        //PreferencesManager.getInstance(ProfileEditActivity.this).setRoleValue(role);
                        //PreferencesManager.getInstance(ProfileEditActivity.this).setName(ob.getString("name"));
                       // PreferencesManager.getInstance(ProfileEditActivity.this).setEmail(ob.getString("email"));
                      //  Intent intent = new Intent(ProfileEditActivity.this, ProfileActivity.class);
                     //   startActivity(intent);
                      //  finish();

                   // } else {
                        //    Log.e("asd", "onResponse: asdds"+ ob.toString());
                    //    Toast.makeText(ProfileEditActivity.this, "Email/password tidak sesuai", Toast.LENGTH_SHORT).show();
                  //  }
                //} catch (Exception e) {
                //    Toast.makeText(ProfileEditActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
              //      e.printStackTrace();
            //    }
          //  }
        //}, new Response.ErrorListener() {
            //@Override
            //public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(ProfileEditActivity.this, "Kesalahan jaringan, coba kembali nanti", Toast.LENGTH_SHORT).show();
            //    Log.e("volley", error.getMessage());
          //  }
        //}) {
            //@Override
            //protected Map<String, String> getParams() throws AuthFailureError {
               // HashMap<String,String> re = new HashMap<>();
               // re.put("email", email);
             //   re.put("password", pass);
           //     return re;
         //   }
       // };

     //   VolleySingleton.getInstance(this).addToRequestQueue(request);
    //}
}