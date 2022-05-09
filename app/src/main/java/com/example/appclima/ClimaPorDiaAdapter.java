package com.example.appclima;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appclima.models.Forecastday;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ClimaPorDiaAdapter extends RecyclerView.Adapter<ClimaPorDiaAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Forecastday> climaArrayList;

    public ClimaPorDiaAdapter(Context context, ArrayList<Forecastday> climaArrayList) {
        this.context = context;
        this.climaArrayList = climaArrayList;
    }

    @NonNull
    @Override
    public ClimaPorDiaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.clima_rv_dia_item, parent, false);
        return new ClimaPorDiaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClimaPorDiaAdapter.ViewHolder viewHolder, int position) {

        Forecastday forecastday = climaArrayList.get(position);

        Glide.with(context).load("https:".concat(forecastday.getDay().getCondition().getIcon()))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.ivIcono);

        String temperaturaMinima = forecastday.getDay().getMintemp_c()+"º";
        viewHolder.tvTempMin.setText(temperaturaMinima);

        String temperaturaMaxima = forecastday.getDay().getMaxtemp_c()+"º";
        viewHolder.tvTempMax.setText(temperaturaMaxima);

        DateFormat readFormat = new SimpleDateFormat( "yyyy-MM-dd", Locale.getDefault());
        DateFormat writeFormat = new SimpleDateFormat( "dd-MM-yyyy", Locale.getDefault());
        Date date = null;
        try{
            date = readFormat.parse(forecastday.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null){
            viewHolder.tvFecha.setText(writeFormat.format(date));
        }



    }

    @Override
    public int getItemCount() {
        return climaArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFecha, tvTempMin, tvTempMax;
        private ImageView ivIcono;

        public ViewHolder(View view) {
            super(view);

            tvFecha = view.findViewById(R.id.tvFecha);
            tvTempMin = view.findViewById(R.id.tvTempMin);
            tvTempMax = view.findViewById(R.id.tvTempMax);
            ivIcono = view.findViewById(R.id.ivIcono);
        }
    }

}
