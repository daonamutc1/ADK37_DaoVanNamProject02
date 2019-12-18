package com.example.adk37_daovannamproject02.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adk37_daovannamproject02.uliti.Defile;
import com.example.adk37_daovannamproject02.R;
import com.example.adk37_daovannamproject02.model.Hourly;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRecycleHourly extends RecyclerView.Adapter<AdapterRecycleHourly.Viewhoder> {

    List<Hourly> hourlyList;
    Context context;

    public AdapterRecycleHourly(List<Hourly> hourlyList, Context context) {
        this.hourlyList = hourlyList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_hourly, parent, false);
        Viewhoder viewhoder = new Viewhoder(view);
        return viewhoder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhoder holder, int position) {
        Hourly hourly = hourlyList.get(position);
        holder.tvHouseHourly.setText(hourly.getHour());

        String icon = hourly.getIcon();

        Picasso.with(context).load(Defile.URL_HOMEICON + icon + Defile.PNG).into(holder.imIconHouseHourly);

        holder.tvHumi1Hourly.setText(Math.round(hourly.getHum()*1)/1 + "%");
        holder.tvTempHourly.setText(Math.round(hourly.getTemp()*1)/1 + "°");
        if (position == hourlyList.size() - 1) {
            holder.linkedoch.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return hourlyList.size();
    }

    public class Viewhoder extends RecyclerView.ViewHolder {
        TextView tvHouseHourly, tvHumi1Hourly, tvTempHourly;
        ImageView imIconHouseHourly;
        LinearLayout linkedoch;

        public Viewhoder(@NonNull View itemView) {
            super(itemView);
            tvHouseHourly = itemView.findViewById(R.id.tvHouseHourly);
            tvHumi1Hourly = itemView.findViewById(R.id.tvHumi1Hourly);
            tvTempHourly = itemView.findViewById(R.id.tvTempHourly);
            imIconHouseHourly = itemView.findViewById(R.id.imIconHouseHourly);
            linkedoch = itemView.findViewById(R.id.linkedoch);
        }
    }
}
