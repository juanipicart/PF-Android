package com.example.pf_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pf_android.Apis.APIService;
import com.example.pf_android.Fragments.DetalleObsFragment;
import com.example.pf_android.Fragments.MainFragment;
import com.example.pf_android.Fragments.NuevaObsFragment;
import com.example.pf_android.Models.Login;
import com.example.pf_android.remote.ApiUtils;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private static String API_BASE_URL = ApiUtils.BASE_URL;
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
                else if(usuario.isEmpty() || pass.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Las credenciales no pueden ser vacias", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void sendPost(final String username, String passname) {
        Login u = new Login(username, passname);
        mAPIService.savePost(u).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                if(response.isSuccessful()) {
                    Log.i("success", "post submitted to API." + response.body().toString());
                    Toast.makeText(LoginActivity.this, "Bienvenido, " + usuario, Toast.LENGTH_LONG).show();

                    StartMenu(username);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Usuario y/o contraseña invalida", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.e("fail", "Unable to submit post to API.");
                Toast.makeText(LoginActivity.this, "No es posible ingresar en este momento.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void StartMenu (String usuario)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
        finish();
    }


}