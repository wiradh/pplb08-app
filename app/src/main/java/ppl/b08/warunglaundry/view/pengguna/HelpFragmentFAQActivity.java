package ppl.b08.warunglaundry.view.pengguna;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import ppl.b08.warunglaundry.R;

/**
 * Created by user on 5/29/2016.
 */
public class HelpFragmentFAQActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set layout content
        setContentView(R.layout.activity_help_faq);
        WebView browser = (WebView) findViewById(R.id.webview);
        //akan menampilkan halaman faq
        browser.loadUrl("http://warung-laundry.com/land/faq");
    }


}
