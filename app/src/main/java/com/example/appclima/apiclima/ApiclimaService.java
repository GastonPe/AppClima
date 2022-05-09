package com.example.appclima.apiclima;

import com.example.appclima.models.Clima;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiclimaService {

    @GET("forecast.json?")
    Call<Clima> obtenerDatosClima(@Query("key") String key, @Query("q") String latlon, @Query("days") int dias);
}
