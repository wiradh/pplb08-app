package ppl.b08.warunglaundry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ppl.b08.warunglaundry.Entity.LProvider;
import ppl.b08.warunglaundry.R;

/**
 * Created by Andi Fajar on 30/04/2016.
 */
public class NewOrderAdapter extends BaseAdapter {

    ArrayList<LProvider> items;
    Context context;

    public NewOrderAdapter(ArrayList<LProvider> items) {
        this.items = items;
    }
    public NewOrderAdapter(Context context, ArrayList<LProvider> items) {
        this.context = context;
        this.items =  items;
      //  Log.e("ASD", "NewOrderAdapter: " + items);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_new_order, parent, false);
        }



        ViewHolder holder = new ViewHolder();
        holder.nama = (TextView) convertView.findViewById(R.id.nama_txt);
        holder.jarak = (TextView) convertView.findViewById(R.id.jarak_txt);
        holder.harga = (TextView) convertView.findViewById(R.id.harga_txt);
        holder.position = position;
        convertView.setTag(holder);

        holder.nama.setText(items.get(position).getNama());
        holder.jarak.setText(String.format("%.02f", items.get(position).getJarak())+" meter");
        holder.harga.setText("Rp"+String.format("%.02f", items.get(position).getHarga()));

        return convertView;
    }

    static class ViewHolder {
        public TextView nama;
        public TextView jarak;
        public TextView harga;
        public int position;
    }
}
