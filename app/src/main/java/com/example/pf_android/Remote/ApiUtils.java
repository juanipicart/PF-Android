package com.example.pf_android.Remote;

import com.example.pf_android.Apis.APIService;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.1.45:8092/TareaPDT_JSF/faces/rest/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}