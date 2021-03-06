package ppl.b08.warunglaundry.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ppl.b08.warunglaundry.Entity.Order;
import ppl.b08.warunglaundry.R;

/**
 * Created by Andi Fajar on 01/05/2016.
 * Adapter for History Order Activity
 */
public class HistoryOrderCAdapter extends BaseAdapter {

    ArrayList<Order> items;
    Context context;

    /**
     *
     * @param items list of Order
     * @param context running activity
     */
    public HistoryOrderCAdapter(ArrayList<Order> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            // Inflate a new View every time a new row requires one.
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_current_order, parent, false);
        }

        // Inflate every sub view
        ViewHolder holder = new ViewHolder();
        holder.nama = (TextView) convertView.findViewById(R.id.nama_txt);
        holder.berat = (TextView) convertView.findViewById(R.id.berat_txt);
        holder.status = (TextView) convertView.findViewById(R.id.status_txt);
        holder.id = (TextView) convertView.findViewById(R.id.id_txt);
        convertView.setTag(holder);

        // Bind with order
        holder.status.setTextColor(Color.parseColor(items.get(position).getColor()));
        String message = items.get(position).getHargaTotal()==0?"Canceled":"Rp"+String.format("%.02f", items.get(position).getHargaTotal());
        holder.status.setText(message);
        String berat =  String.format("%.02f", items.get(position).getBerat())+" kg";
        holder.berat.setText(berat);
        holder.id.setText("Order - "+items.get(position).getId());
        holder.nama.setText(items.get(position).getNamaProvider());
        holder.position = position;

        return convertView;
    }

    /**
     * Sub View Holder
     */
    static class ViewHolder {
        public TextView nama;
        public TextView id;
        public TextView berat;
        public TextView status;
        public int position;
    }
}
