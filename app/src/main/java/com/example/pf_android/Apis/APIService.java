package com.example.pf_android.Apis;

import com.example.pf_android.Models.Fenomeno;
import com.example.pf_android.Models.Login;

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

}
