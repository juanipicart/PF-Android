package com.example.pf_android.Apis;

import com.example.pf_android.Models.Fenomeno;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FenomenosApi {

    @GET("ubicaciones/fenomenos")
    Call<ArrayList<Fenomeno>> getFenomenos();
}
