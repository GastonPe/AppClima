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
import com.example.appclima.models.Hour;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ClimaPorHoraAdapter extends RecyclerView.Adapter<ClimaPorHoraAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Hour> climaArrayList;

    public ClimaPorHoraAdapter(Context context, ArrayList<Hour> climaArrayList) {
        this.context = context;
        this.climaArrayList = climaArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.clima_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClimaPorHoraAdapter.ViewHolder viewHolder, int position) {

        Hour hour = climaArrayList.get(position);

        Glide.with(context).load("https:".concat(hour.getCondition().getIcon()))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.ivIcono);

        String temperatura = hour.getTemp_c()+"º";
        viewHolder.tvTemperatura.setText(temperatura);

        DateFormat readFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm", Locale.getDefault());
        DateFormat writeFormat = new SimpleDateFormat( "HH:mm", Locale.getDefault());
        Date date = null;
        try{
            date = readFormat.parse(hour.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null){
            viewHolder.tvHora.setText(writeFormat.format(date));
        }

    }

    @Override
    public int getItemCount() {
        return climaArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHora, tvTemperatura;
        private ImageView ivIcono;

        public ViewHolder(View view) {
            super(view);

            tvHora = view.findViewById(R.id.tvHora);
            tvTemperatura = view.findViewById(R.id.tvTemperatura);
            ivIcono = view.findViewById(R.id.ivIcono);
        }
    }

}