package com.example.appclima;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appclima.apiclima.ApiclimaService;
import com.example.appclima.models.Clima;
import com.example.appclima.models.Condition;
import com.example.appclima.models.Day;
import com.example.appclima.models.Forecastday;
import com.example.appclima.models.Hour;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements LocationListener {

    final static String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    final static int PERMISSIONS_ALL = 1;
    LocationManager locationManager;

    private Retrofit retrofit;
    private String apiKey = "AQUÍ VA TU API KEY";

    private TextView tvCiudad, tvRegion, tvTemperatura, tvEstado;
    private ImageView ivIcono;
    private RecyclerView rvClimaPorHora, rvClimaPorDia;
    private ArrayList<Hour> climaArrayList;
    private ClimaPorHoraAdapter climaPorHoraAdapter;
    private ClimaPorDiaAdapter climaPorDiaAdapter;
    private ArrayList<Forecastday> climaPorDiaArrayList;
    private ImageView btnBuscar;
    private TextInputLayout inCiudad;
    private TextInputEditText etCiudad;

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private String latlon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppClima);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tvCiudad = findViewById(R.id.tvCiudad);
        tvRegion = findViewById(R.id.tvRegion);
        tvTemperatura = findViewById(R.id.tvTemperatura);
        tvEstado = findViewById(R.id.tvEstado);
        ivIcono = findViewById(R.id.ivIcono);
        rvClimaPorHora = findViewById(R.id.rvClimaPorHora);
        rvClimaPorDia = findViewById(R.id.rvClimaPorDia);
        btnBuscar = findViewById(R.id.btnBuscar);
        inCiudad = findViewById(R.id.inCiudad);
        etCiudad = findViewById(R.id.etCiudad);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT>=23){
            requestPermissions(PERMISSIONS, PERMISSIONS_ALL);
        }
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        climaArrayList = new ArrayList<>();
        climaPorHoraAdapter = new ClimaPorHoraAdapter(this, climaArrayList);
        rvClimaPorHora.setAdapter(climaPorHoraAdapter);

        climaPorDiaArrayList = new ArrayList<>();
        climaPorDiaAdapter = new ClimaPorDiaAdapter(this, climaPorDiaArrayList);
        rvClimaPorDia.setAdapter(climaPorDiaAdapter);

        pedirUbicacion();

        btnBuscar.setOnClickListener(view -> {
            latlon = inCiudad.getEditText().getText().toString();
            obtenerDatos(latlon);
            etCiudad.setText("");
        });
    }

    private void obtenerDatos(String latlon) {
        ApiclimaService service = retrofit.create(ApiclimaService.class);

        Call<Clima> climaRespuesta = service.obtenerDatosClima(apiKey, latlon, 7);

        climaRespuesta.enqueue(new Callback<Clima>() {
            @Override
            public void onResponse(@NonNull Call<Clima> call, @NonNull Response<Clima> response) {
                if (response.isSuccessful()) {
                    Clima clima = response.body();

                    climaArrayList.clear();
                    climaPorDiaArrayList.clear();

                    tvCiudad.setText(clima.getLocation().getName());
                    tvRegion.setText(clima.getLocation().getRegion());
                    tvTemperatura.setText(clima.getCurrent().getTemp_c().concat("º"));
                    Glide.with(MainActivity.this).load("https:".concat(clima.getCurrent().getCondition().getIcon()))
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(ivIcono);
                    tvEstado.setText(clima.getCurrent().getCondition().getText().toUpperCase());

                    String currentTime = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());

                    for (int i = 0; i < clima.getForecast().getForecastday().get(0).getHour().size(); i++) {

                        String horas = clima.getForecast().getForecastday().get(0).getHour().get(i).getTime();

                        DateFormat readFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm", Locale.getDefault());
                        DateFormat writeFormat = new SimpleDateFormat( "HH:mm", Locale.getDefault());
                        Date date = null;
                        try{
                            date = readFormat.parse(horas);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        double temperatura = clima.getForecast().getForecastday().get(0).getHour().get(i).getTemp_c();
                        Condition icono = clima.getForecast().getForecastday().get(0).getHour().get(i).getCondition();

                        //Si la hora es mayor o igual se agregan los datos al array para pasarlos al adapter
                        if(writeFormat.format(date).compareTo(currentTime) >= 0){
                            climaArrayList.add(new Hour(horas, temperatura, icono));
                        }

                    }
                    climaPorHoraAdapter.notifyDataSetChanged();

                    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    for (int i = 0; i < clima.getForecast().getForecastday().size(); i++) {

                        String fechaPorDia = clima.getForecast().getForecastday().get(i).getDate();
                        Day dia = clima.getForecast().getForecastday().get(i).getDay();

                        //Si el dia es mayor o igual se agregan los datos al array para pasarlos al adapter
                        if(fechaPorDia.compareTo(currentDate) >= 0){
                            climaPorDiaArrayList.add(new Forecastday(fechaPorDia, dia));
                        }
                    }
                    climaPorDiaAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Clima> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //latlon = location.getLatitude()+","+location.getLongitude();
        latlon = df.format(location.getLatitude())+","+df.format(location.getLongitude());
        obtenerDatos(latlon);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pedirUbicacion();
        }
    }

    public void pedirUbicacion(){
        if (locationManager == null){
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1000, this);
            }
        }
    }
}