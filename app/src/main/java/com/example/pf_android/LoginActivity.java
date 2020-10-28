package com.example.pf_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pf_android.Apis.APIService;
import com.example.pf_android.Models.Usuario;
import com.example.pf_android.remote.ApiUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static String API_BASE_URL = "http://192.168.210.4:8081/TareaPDT_JSF/faces/rest/";
    private static Retrofit retrofit;
    private static Gson gson;

    private Button button;
    private EditText userTxt, passTxt;
    private String usuario, pass;
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.button);
        userTxt = findViewById(R.id.etUser);
        passTxt = findViewById(R.id.etPassword);

        mAPIService = ApiUtils.getAPIService();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = userTxt.getText().toString().trim();
                pass = passTxt.getText().toString().trim();
                if (!TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(pass)) {
                    sendPost(usuario, pass);
                }
            }
        });
    }

    public void sendPost(final String username, String passname) {
        Usuario u = new Usuario(username, passname);
        mAPIService.savePost(u).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if(response.isSuccessful()) {
                    Log.i("success", "post submitted to API." + response.body().toString());
                    Toast.makeText(LoginActivity.this, "bIENVENIDO!!", Toast.LENGTH_LONG).show();

                    StartMenu(username);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Usuario y/o contraseña invalida", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("fail", "Unable to submit post to API.");
                Toast.makeText(LoginActivity.this, "Fallo", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void StartMenu (String usuario)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
    }


}