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
        //membuat variable button untuk dapat diakses melalui layout fragment_help
        Button faqBtn = (Button) v.findViewById(R.id.aboutfaq);
        Button tutorialBtn = (Button) v.findViewById(R.id.tutorial);
        Button aboutusBtn = (Button) v.findViewById(R.id.aboutus);
        //Initializing NavigationView


//jika button ini diklik, akan memidahkan activity ke halaman faq

        faqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HelpFragmentFAQActivity.class);
                startActivity(intent);
            // v = inflater.inflate(R.layout.fragment, container, false);
            }
        });
//jika button ini diklik, akan memidahkan activity ke halaman tutorial

        tutorialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HelpFragmentTutorialActivity.class);
                startActivity(intent);
            }
        });
//jika button ini diklik, akan memidahkan activity ke halaman aboutus
        aboutusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HelpFragmentWarungLaundryActivity.class);
                startActivity(intent);
            }
        });



        return v;
    }
}
