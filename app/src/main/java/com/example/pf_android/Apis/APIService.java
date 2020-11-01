package com.example.pf_android.Apis;

import com.example.pf_android.Models.Departamento;
import com.example.pf_android.Models.Fenomeno;
import com.example.pf_android.Models.Localidad;
import com.example.pf_android.Models.Login;
import com.example.pf_android.Models.Observacion;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    @GET("ubicaciones/fenomenos")
    Call<ArrayList<Fenomeno>> getFenomenos();

    @GET("observaciones/usuario/{username}")
    Call<ArrayList<Observacion>> getObservaciones(@Path("username") String username);

    @POST("usuarios/login")
    Call<Login> savePost(@Body Login login);

    @POST("observaciones")
    Call<Observacion> saveObservacion(@Body Observacion observacion);

    @GET("ubicaciones/localidades/{depto}")
    Call<ArrayList<Localidad>> getLocalidades(@Path("depto") String nombre_depto);

    @GET("ubicaciones/departamentos")
    Call<ArrayList<Departamento>> getDepartamentos();

    @DELETE("observaciones/{id}")
    Call<Observacion> deleteObservacion(@Path("id") long id);

    @PUT("observaciones/{id}")
    Call<Observacion> modifyObservacion(@Body Observacion observacion, @Path("id") long id);

}
