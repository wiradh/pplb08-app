package ppl.b08.warunglaundry.view.pengguna;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ppl.b08.warunglaundry.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        Button newOrderBtn = (Button) v.findViewById(R.id.new_order_btn);
        Button historyOrderBtn = (Button) v.findViewById(R.id.history_order_btn);
        Button currentOrderBtn = (Button) v.findViewById(R.id.current_order_btn);

        newOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrderNewActivity.class);
                startActivity(intent);
            }
        });

        historyOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryOrderActivity.class);
                startActivity(intent);
            }
        });

        currentOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CurrentOrderActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

}
