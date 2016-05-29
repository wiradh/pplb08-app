package ppl.b08.warunglaundry.view.pengguna;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import ppl.b08.warunglaundry.R;

/**
 * Created by user on 5/29/2016.
 */
public class HelpFragmentWarungLaundryActivity extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //set halaman layout
    setContentView(R.layout.activity_help_aboutwarunglaundry);
    WebView browser = (WebView) findViewById(R.id.webview);
    //akan menampilkan tampilan web aboutus
    browser.loadUrl("http://warung-laundry.com/land/#");
}
}
