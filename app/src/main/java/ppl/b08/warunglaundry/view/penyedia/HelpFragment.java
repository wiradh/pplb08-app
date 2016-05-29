package ppl.b08.warunglaundry.view.penyedia;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ppl.b08.warunglaundry.R;
import ppl.b08.warunglaundry.view.pengguna.HelpFragmentTutorialActivity;
import ppl.b08.warunglaundry.view.pengguna.HelpFragmentWarungLaundryActivity;

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
        View v = inflater.inflate(R.layout.fragment_help, container, false);
        Button newOrderBtn = (Button) v.findViewById(R.id.aboutfaq);
        Button historyOrderBtn = (Button) v.findViewById(R.id.tutorial);
        Button currentOrderBtn = (Button) v.findViewById(R.id.aboutus);
        //Initializing NavigationView



        newOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ppl.b08.warunglaundry.view.penyedia.HelpFragmentFAQActivity.class);
                startActivity(intent);
                // v = inflater.inflate(R.layout.fragment, container, false);
            }
        });

        historyOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HelpFragmentTutorialActivity.class);
                startActivity(intent);
            }
        });

        currentOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HelpFragmentWarungLaundryActivity.class);
                startActivity(intent);
            }
        });

        // WebView browser = (WebView) v.findViewById(R.id.webview);
      //  browser.loadUrl("http://warung-laundry.com/page/help");
        return v;
    }

}
