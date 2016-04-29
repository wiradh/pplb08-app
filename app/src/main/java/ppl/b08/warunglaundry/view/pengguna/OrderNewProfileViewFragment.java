package ppl.b08.warunglaundry.view.pengguna;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ppl.b08.warunglaundry.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderNewProfileViewFragment extends Fragment {


    public OrderNewProfileViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_new_profile_view, container, false);
    }

}
