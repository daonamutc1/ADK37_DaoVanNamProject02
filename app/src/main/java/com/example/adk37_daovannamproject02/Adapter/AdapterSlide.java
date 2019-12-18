package com.example.adk37_daovannamproject02.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.adk37_daovannamproject02.R;
import com.example.adk37_daovannamproject02.model.ObjectACity;
import com.example.adk37_daovannamproject02.uliti.Defile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class AdapterSlide extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<ObjectACity> list;
    TextView tvName, tvtoday, tvTeamp, tvTempMax, tvTempMin, tvHumidity, tvStatus, tvHouseHourly0, tvHumi1Hourly0, tvTempHourly0, tvHouseHourly1, tvHumi1Hourly1, tvTempHourly1, tvHouseHourly2, tvHumi1Hourly2, tvTempHourly2, tvHouseHourly3, tvHumi1Hourly3, tvTempHourly3, tvHouseHourly4, tvHumi1Hourly4, tvTempHourly4, tvHouseHourly5, tvHumi1Hourly5, tvTempHourly5, tvHouseHourly6, tvHumi1Hourly6, tvTempHourly6, tvHouseHourly7, tvHumi1Hourly7, tvTempHourly7, tvDayDaily0, tvHumidityDaily0, tvTempMaxDaily0, tvTempMinDaily0, tvDayDaily1, tvHumidityDaily1, tvTempMaxDaily1, tvTempMinDaily1, tvDayDaily2, tvHumidityDaily2, tvTempMaxDaily2, tvTempMinDaily2, tvDayDaily3, tvHumidityDaily3, tvTempMaxDaily3, tvTempMinDaily3, tvDayDaily4, tvHumidityDaily4, tvTempMaxDaily4, tvTempMinDaily4, tvhourlychart, tvdailychart;
    ImageView imgBack, imCloud, imgNext, imIconHouseHourly0, imIconHouseHourly1, imIconHouseHourly2, imIconHouseHourly3, imIconHouseHourly4, imIconHouseHourly5, imIconHouseHourly6, imIconHouseHourly7, imCloudtDaily0, imCloudtDaily1, imCloudtDaily2, imCloudtDaily3, imCloudtDaily4;
    LineChartView lineChartViewhourly, lineChartViewdaily;
    RecyclerView rvHourly, rvDaily;
    AdapterRecycleHourly recycleHourlyAdapter;
    AdapterRecycleDaily recycleDailyAdapter;
    boolean GPS;

    //Để hiển thị view sau khi tìm kiếm ở đâu đó
    //Contructor
    public AdapterSlide(Context context, ArrayList<ObjectACity> list, boolean GPS) {
        this.context = context;
        this.list = list;
        this.GPS = GPS;
    }

    //Số lượng slide
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    //Định nghĩa các ô cần gán
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        //slide là item
        View view = inflater.inflate(R.layout.acity, container, false);

        //Khai báo ánh xạ
        Anhxa(view);
        //Chart
        //Gán giá trị
        setValue(position, view);
        //Chart hourly
        chartHourly(lineChartViewhourly, position);
        //ChartDaily
        chartDaily(lineChartViewdaily, position);
        //Trong set không muốn thêm view vào
        tvhourlychart.setText(view.getResources().getString(R.string.hourly) + " (" + list.get(position).getUnit() + ")");
        tvdailychart.setText(view.getResources().getString(R.string.daily) + " (" + list.get(position).getUnit() + ")");

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    private void setValue(int position, View view) {
        tvName.setText(list.get(position).getNameCity() + "");
        if (position == 0&&GPS) {
            //Định kích thước icon trong textview
            Drawable drawable = view.getResources().getDrawable(R.drawable.iconlocationwhite);
            drawable.setBounds(0, 0, 40, 40);
            tvName.setCompoundDrawables(drawable, null, null, null);
        }

        tvtoday.setText(list.get(position).getDaynow() + "");
        if (position == 0) {
            imgBack.setVisibility(View.GONE);
        } else {
            imgBack.setVisibility(View.VISIBLE);
        }

        Picasso.with(context).load(Defile.URL_HOMEICON + list.get(position).getIconnow() + Defile.PNG).into(imCloud);

        tvTeamp.setText(Math.round(list.get(position).getTeampnow() * 1) / 1 + "" + list.get(position).getUnit());

        if (position == list.size() - 1) {
            imgNext.setVisibility(View.GONE);
        } else {
            imgNext.setVisibility(View.VISIBLE);
        }
        //chính
        tvTempMax.setText(Math.round(list.get(position).getTempMaxToDay() * 1) / 1 + "°/");
        tvTempMin.setText(Math.round(list.get(position).getTempMinToDay() * 1) / 1 + "°|");
        tvHumidity.setText(" Hum: " + Math.round(list.get(position).getHumiditynow() * 1) / 1 + "%");
        tvStatus.setText(list.get(position).getStatusnow());
        //Hourly
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recycleHourlyAdapter = new AdapterRecycleHourly(list.get(position).getHourlies(), context);
        rvHourly.setAdapter(recycleHourlyAdapter);
        rvHourly.setLayoutManager(layoutManager1);

        //Daily
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recycleDailyAdapter = new AdapterRecycleDaily(list.get(position).getDailies(), context);
        rvDaily.setAdapter(recycleDailyAdapter);
        rvDaily.setLayoutManager(layoutManager);
    }

    private void Anhxa(View view) {
        //Khai báo ánh xạ
        tvName = view.findViewById(R.id.tvName);
        tvtoday = view.findViewById(R.id.tvtoday);
        imgBack = view.findViewById(R.id.imgBack);
        imCloud = view.findViewById(R.id.imCloud);
        tvTeamp = view.findViewById(R.id.tvTeamp);
        imgNext = view.findViewById(R.id.imgNext);
        tvTempMax = view.findViewById(R.id.tvTempMax);
        tvTempMin = view.findViewById(R.id.tvTempMin);
        tvHumidity = view.findViewById(R.id.tvHumidity);
        tvStatus = view.findViewById(R.id.tvStatus);
        rvHourly = view.findViewById(R.id.rvHourly);
        rvDaily = view.findViewById(R.id.rvDaily);
        //Chart
        lineChartViewhourly = view.findViewById(R.id.lineChartHourly);
        lineChartViewdaily = view.findViewById(R.id.lineChartDaily);
        tvhourlychart = view.findViewById(R.id.tvhourlychart);
        tvdailychart = view.findViewById(R.id.tvdailychart);
    }

    public void chartHourly(LineChartView lineChartViewhourly, int position) {
        List yAxisValueshourly = new ArrayList();
        List axisValueshourly = new ArrayList();

        //Khai báo dòng xuất hiện trong biểu đồ của trục y
        Line line = new Line(yAxisValueshourly);


        //Gán dữ liệu cho trục ngang, x

        for (int i = 0; i < list.get(position).getHourlies().size(); i++) {
            axisValueshourly.add(i, new AxisValue(i).setLabel(list.get(position).getHourlies().get(i).getHour()));
        }
        //Gán dữ liệu cho trục đứng, y
        int ymin = (int) Math.round(list.get(position).getHourlies().get(0).getTemp() * 1 / 1);
        int ymax = (int) Math.round(list.get(position).getHourlies().get(0).getTemp() * 1 / 1);
        for (int i = 0; i < list.get(position).getHourlies().size(); i++) {
            yAxisValueshourly.add(new PointValue(i, (int) Math.round(list.get(position).getHourlies().get(i).getTemp() * 1 / 1)));
            if (ymin > (int) Math.round(list.get(position).getHourlies().get(i).getTemp() * 1 / 1)) {
                ymin = (int) Math.round(list.get(position).getHourlies().get(i).getTemp() * 1 / 1);
            }
            if (ymax < (int) Math.round(list.get(position).getHourlies().get(i).getTemp() * 1 / 1)) {
                ymax = (int) Math.round(list.get(position).getHourlies().get(i).getTemp() * 1 / 1);
            }
        }

        List lines = new ArrayList();

        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        lineChartViewhourly.setLineChartData(data);

        //Hiển thị dữ liệu cho các trục x, y
        //Trục x (Trục ngang)
        Axis axis = new Axis();
        axis.setValues(axisValueshourly);
        data.setAxisXBottom(axis);
        //Trục y
        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

        //Đổi màu đường line
        line.setColor(Color.parseColor("#FFFFFF"));
        //Bán kính điểm chấm
        line.setPointRadius(3);
        //Chiều dầy đường kẻ
        line.setStrokeWidth(1);
        //Cỡ chữ
        axis.setTextSize(12);
        //Màu chữ
        axis.setTextColor(Color.parseColor("#FFFFFF"));
        yAxis.setTextColor(Color.parseColor("#FFFFFF"));
        //Mô tả mép trái trục y
//        yAxis.setName("Mô tả trục y");
        lineChartViewhourly.setViewportCalculationEnabled(false);
        lineChartViewhourly.setZoomEnabled(false);
        final Viewport maxViewport = new Viewport(0, ymax + 5, 8, ymin - 5);
        lineChartViewhourly.setMaximumViewport(maxViewport);
        lineChartViewhourly.setCurrentViewport(maxViewport);
    }

    private void chartDaily(LineChartView lineChartViewdaily, int position) {
        List yAxisValuesdaily = new ArrayList();
        List axisValuesdaily = new ArrayList();
        List yAxisValuesdailymin = new ArrayList();

        //Khai báo dòng xuất hiện trong biểu đồ của trục y
        Line linedailymax = new Line(yAxisValuesdaily);
        Line linedailymin = new Line(yAxisValuesdailymin);

        //Gán dữ liệu cho trục ngang, x
        for (int i = 0; i < list.get(position).getDailies().size(); i++) {
            axisValuesdaily.add(i, new AxisValue(i).setLabel(list.get(position).getDailies().get(i).getDay()));
        }
        //Gán dữ liệu cho trục đứng, y
        int ymin = (int) Math.round(list.get(position).getDailies().get(0).getTempMin() * 1 / 1);
        int ymax = (int) Math.round(list.get(position).getDailies().get(0).getTempMax() * 1 / 1);
        for (int i = 0; i < list.get(position).getDailies().size(); i++) {
            yAxisValuesdaily.add(new PointValue(i, (int) Math.round(list.get(position).getDailies().get(i).getTempMax() * 1 / 1)));
            yAxisValuesdailymin.add(new PointValue(i, (int) Math.round(list.get(position).getDailies().get(i).getTempMin() * 1 / 1)));
            if (ymin > (int) Math.round(list.get(position).getDailies().get(i).getTempMin() * 1 / 1)) {
                ymin = (int) Math.round(list.get(position).getDailies().get(i).getTempMin() * 1 / 1);
            }
            if (ymax < (int) Math.round(list.get(position).getDailies().get(i).getTempMax() * 1 / 1)) {
                ymax = (int) Math.round(list.get(position).getDailies().get(i).getTempMax() * 1 / 1);
            }
        }

        List linedailymaxlist = new ArrayList();

        linedailymaxlist.add(linedailymax);
        linedailymaxlist.add(linedailymin);


        LineChartData datadailymax = new LineChartData();
        datadailymax.setLines(linedailymaxlist);

        lineChartViewdaily.setLineChartData(datadailymax);

        //Hiển thị dữ liệu cho các trục x, y
        //Trục x (Trục ngang)
        Axis axisdaily = new Axis();
        axisdaily.setValues(axisValuesdaily);
        datadailymax.setAxisXBottom(axisdaily);
        //Trục y
        Axis yAxisdaily = new Axis();
        datadailymax.setAxisYLeft(yAxisdaily);


        //Đổi màu đường line
        linedailymax.setColor(Color.parseColor("#FFFFFF"));
        linedailymin.setColor(Color.parseColor("#FFFFFF"));
        //Set bán kính point
        linedailymax.setPointRadius(3);
        linedailymin.setPointRadius(3);
        //Chiều dầy đường kẻ
        linedailymax.setStrokeWidth(1);
        linedailymin.setStrokeWidth(1);
        //Hiện giá trị tại các point

        //Cỡ chữ
        axisdaily.setTextSize(12);
        //Màu chữ
        axisdaily.setTextColor(Color.parseColor("#FFFFFF"));
        yAxisdaily.setTextColor(Color.parseColor("#FFFFFF"));
        //Mô tả mép trái trục y
//        yAxis.setName("Mô tả trục y");

        lineChartViewdaily.setViewportCalculationEnabled(false);
        lineChartViewdaily.setZoomEnabled(false);
        final Viewport maxViewport1 = new Viewport(0, ymax + 5, 4, ymin - 5);
        lineChartViewdaily.setMaximumViewport(maxViewport1);
        lineChartViewdaily.setCurrentViewport(maxViewport1);
    }
}