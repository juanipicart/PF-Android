package com.example.pf_android.Apis;

import com.example.pf_android.Models.Fenomeno;
import com.example.pf_android.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIService {

    @GET("ubicaciones/fenomenos")
    Call<ArrayList<Fenomeno>> getFenomenos();

    @POST("usuarios/login")
    Call<Usuario> savePost(@Body Usuario usuario);

}
