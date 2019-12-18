package com.example.adk37_daovannamproject02.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adk37_daovannamproject02.uliti.Defile;
import com.example.adk37_daovannamproject02.model.ObjectACity;
import com.example.adk37_daovannamproject02.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterListView extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<ObjectACity> list;

    public AdapterListView(Context context, int layout, ArrayList<ObjectACity> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);
        TextView tvNameList = (TextView) convertView.findViewById(R.id.tvNameList);
        TextView tvDateList = (TextView) convertView.findViewById(R.id.tvDateList);
        ImageView imgClousList = convertView.findViewById(R.id.imgClousList);
        TextView tvTempList = (TextView) convertView.findViewById(R.id.tvTempList);
        TextView tvTempMaxList = (TextView) convertView.findViewById(R.id.tvTempMaxList);
        TextView tvTempMinList = (TextView) convertView.findViewById(R.id.tvTempMinList);
        ListView lvList = convertView.findViewById(R.id.lvList);

        tvNameList.setText(list.get(position).getNameCity());
        tvDateList.setText(list.get(position).getDaynow());
        Picasso.with(context).load(Defile.URL_HOMEICON + list.get(position).getIconnow() + Defile.PNG).into(imgClousList);
        tvTempList.setText(Math.round(list.get(position).getTeampnow()*1)/1+""+list.get(position).getUnit());
        tvTempMaxList.setText(Math.round(list.get(position).getTempMaxToDay()*1)/1+"°/");
        tvTempMinList.setText(Math.round(list.get(position).getTempMinToDay()*1)/1+"°");

        return convertView;
    }
}
