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
import com.example.adk37_daovannamproject02.model.Daily;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRecycleDaily extends RecyclerView.Adapter<AdapterRecycleDaily.Viewhoder> {
    List<Daily> dailyList;

    Context context;
    public AdapterRecycleDaily(List<Daily> dailyList, Context context) {
        this.dailyList = dailyList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_daily, parent, false);
        Viewhoder viewhoder = new Viewhoder(view);
        return viewhoder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhoder holder, int position) {
        Daily daily = dailyList.get(position);

        //Ngày hôm nay
        if (position==0){
            holder.tvDayDaily.setText(context.getResources().getString(R.string.today));
        }else {
            holder.tvDayDaily.setText(daily.getDay());
        }
        String icon = daily.getIcon();
        Picasso.with(context).load(Defile.URL_HOMEICON +icon+Defile.PNG).into(holder.imCloudtDaily);

        holder.tvHumidityDaily.setText(Math.round(daily.getHum()*1)/1+"%");

        holder.tvTempMaxDaily.setText(Math.round(daily.getTempMax()*1)/1+"°");

        holder.tvTempMinDaily.setText(Math.round(daily.getTempMin()*1)/1+"°");

        //Ẩn dòng kẻ cuối cùng
        if (position==dailyList.size()-1){
            holder.lnkedaily.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }

    public class Viewhoder extends RecyclerView.ViewHolder {
        TextView tvDayDaily, tvHumidityDaily, tvTempMaxDaily, tvTempMinDaily;
        ImageView imCloudtDaily;
        LinearLayout lnkedaily;
        public Viewhoder(@NonNull View itemView) {
            super(itemView);
            tvDayDaily = itemView.findViewById(R.id.tvDayDaily);
            tvHumidityDaily = itemView.findViewById(R.id.tvHumidityDaily);
            tvTempMaxDaily = itemView.findViewById(R.id.tvTempMaxDaily);
            tvTempMinDaily = itemView.findViewById(R.id.tvTempMinDaily);
            imCloudtDaily = itemView.findViewById(R.id.imCloudtDaily);
            lnkedaily = itemView.findViewById(R.id.lnkedaily);
        }
    }
}
