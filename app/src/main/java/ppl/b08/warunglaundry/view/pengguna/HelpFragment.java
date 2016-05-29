package ppl.b08.warunglaundry.view.pengguna;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ppl.b08.warunglaundry.R;

/**
 * Create by M Risky
 */
public class HelpFragment extends android.support.v4.app.Fragment {

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_help, container, false);
        Button newOrderBtn = (Button) v.findViewById(R.id.aboutfaq);
        Button historyOrderBtn = (Button) v.findViewById(R.id.tutorial);
        Button currentOrderBtn = (Button) v.findViewById(R.id.aboutus);
        //Initializing NavigationView



        newOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HelpFragmentFAQActivity.class);
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



        return v;
    }
}
