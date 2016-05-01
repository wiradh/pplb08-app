package ppl.b08.warunglaundry.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import ppl.b08.warunglaundry.Entity.Order;

/**
 * Created by Andi Fajar on 01/05/2016.
 */
public class HistoryOrderPAdapter extends BaseAdapter {

    ArrayList<Order> items;
    Context context;

    public HistoryOrderPAdapter(ArrayList<Order> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
