package ppl.b08.warunglaundry.view.penyedia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import ppl.b08.warunglaundry.R;

/**
 * Created by user on 5/29/2016.
 */
public class HelpFragmentTutorialActivity extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_help_tutorial);
    WebView browser = (WebView) findViewById(R.id.webview);
    browser.loadUrl("http://warung-laundry.com/land/#");
}
}
