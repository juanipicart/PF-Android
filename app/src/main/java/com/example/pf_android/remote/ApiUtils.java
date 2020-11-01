package com.example.pf_android.remote;

import android.content.Intent;

import com.example.pf_android.Apis.APIService;
import com.example.pf_android.MainActivity;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.1.42:8092/TareaPDT_JSF/faces/rest/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}