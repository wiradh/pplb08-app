package ppl.b08.warunglaundry.view.pengguna;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.business.PreferencesManager;

/**
 * Created by Andi Fajar , Edit by M Risky Negoro P 07/04/2016, 26/05/2016.
 */
public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
         NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View header = navigationView.inflateHeaderView(R.layout.header_customer_drawer);
        ImageView image2 = (ImageView) header.findViewById(R.id.profile_image);
       image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //testing
                //Toast.makeText(HomeActivity.this,
                  //      "The favorite list would appear on clicking this icon",
                    //    Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent2);
            }
        });
        TextView text1 = (TextView) header.findViewById(R.id.name_txt);
       // text1.setText("wiradh");
        text1.setText(PreferencesManager.getInstance(HomeActivity.this).getName());

        TextView text = (TextView) header.findViewById(R.id.email_txt);
        text.setText(PreferencesManager.getInstance(HomeActivity.this).getEmail());
       // text.setText("wira@wira.com");
        OrderFragment fragment = new OrderFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
        //View header = navigationView.inflateHeaderView(R.layout.header_customer_drawer);
        //TextView text = (TextView) header.findViewById(R.id.email_txt);
       // text.setText("bearmiku@bear.com");
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){

                    case R.id.credits:
                        Intent intent2 = new Intent(HomeActivity.this, CreditActivity.class);
                        startActivity(intent2);
                        return true;
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.order:
                        OrderFragment fragment = new OrderFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame,fragment);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.logout:
                        if (PreferencesManager.getInstance(HomeActivity.this).clear()) {
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        };
                        return true;
                    case R.id.help:
                        HelpFragment fragmentHelp = new HelpFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragmentHelp).commit();
                        return true;
                    // For rest of the options we just show a toast on click


                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
                //View header = LayoutInflater.from(this).inflate(R.layout.header_customer_drawer, null);
               // TextView text = (TextView) header.findViewById(R.id.email_txt);
                //text.setText("HELLO");

            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();



    }
}
