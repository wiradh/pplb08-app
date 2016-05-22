package ppl.b08.warunglaundry.view.penyedia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.business.C;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        WebView browser = (WebView) findViewById(R.id.webview);
        browser.loadUrl(C.HOME_URL+"/page/help");
    }
}
