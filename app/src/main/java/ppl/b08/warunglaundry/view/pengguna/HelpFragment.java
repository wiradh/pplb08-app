package ppl.b08.warunglaundry.view.pengguna;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.business.C;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {


    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_help, container, false);
        WebView browser = (WebView) v.findViewById(R.id.webview);
        browser.loadUrl("http://warung-laundry.com/page/help");
        return v;
    }

}
