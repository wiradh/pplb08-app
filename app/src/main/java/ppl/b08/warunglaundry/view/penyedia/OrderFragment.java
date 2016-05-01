package ppl.b08.warunglaundry.view.penyedia;


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
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        Button checkOrderBtn = (Button) v.findViewById(R.id.check_order_btn);
        Button historyOrderBtn = (Button) v.findViewById(R.id.history_order_btn);
        Button changeOrderStatusBtn = (Button) v.findViewById(R.id.change_status_btn);

        checkOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CheckOrderActivity.class);
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

        changeOrderStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangeOrderActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

}
