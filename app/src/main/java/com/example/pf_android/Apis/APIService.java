package com.example.pf_android.Apis;

import com.example.pf_android.Models.Fenomeno;
import com.example.pf_android.Models.Localidad;
import com.example.pf_android.Models.Login;
import com.example.pf_android.Models.Observacion;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @GET("ubicaciones/fenomenos")
    Call<ArrayList<Fenomeno>> getFenomenos();

    @POST("usuarios/login")
    Call<Login> savePost(@Body Login login);

    @POST("observaciones")
    Call<Observacion> saveObservacion(@Body Observacion observacion);

    @GET("ubicaciones/localidades")
    Call<ArrayList<Localidad>> getLocalidades();

}
