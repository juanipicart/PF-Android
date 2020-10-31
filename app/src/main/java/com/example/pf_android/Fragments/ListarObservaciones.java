package com.example.pf_android.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigDecimal;

import com.example.pf_android.Apis.APIService;
import com.example.pf_android.Entities.ObservacionAdapter;
import com.example.pf_android.Models.Observacion;
import com.example.pf_android.R;
import com.example.pf_android.remote.ApiUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListarObservaciones extends Fragment {

    ListView mObservacionesList;
    ArrayAdapter<Observacion> mObservacionAdapter;
    private static String API_BASE_URL = "http://192.168.210.4:8081/TareaPDT_JSF/faces/rest/";
    private static Retrofit retrofit;
    private static Gson gson;
    private APIService mService;
    private String usuario;
    private ArrayList<Observacion> listaObservaciones = new ArrayList<>();
    private TextView txtResultado;

    public ListarObservaciones() {

    }

    public static ListarObservaciones newInstance() {
        ListarObservaciones fragment = new ListarObservaciones();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mService = ApiUtils.getAPIService();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_listar_observaciones, container, false);

        mObservacionesList = (ListView) root.findViewById(R.id.observaciones_list);
        usuario = getArguments().getString("usuario");
        getObservaciones(usuario);
        mObservacionesList.setAdapter(mObservacionAdapter);
        txtResultado = (TextView) root.findViewById(R.id.txtResultado);
        return root;
    }


    private void addOnItemClickListener(final ListView mCiudadesList) {
        mObservacionesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Observacion observacion = (Observacion) mObservacionesList.getItemAtPosition(i);
                    Bundle  bundle = new Bundle();
                    bundle.putLong("ID", observacion.getId());
                    bundle.putString("CODIGO", observacion.getCodigo());
                    bundle.putString("DESCRIPCION", observacion.getDescripcion());
                    bundle.putString("FENOMENO", observacion.getFenomeno());
                    bundle.putString("LOCALIDAD", observacion.getLocalidad());
                    bundle.putString("DEPARTAMENTO", observacion.getDepartamento());
                    bundle.putString("LATITUD", String.valueOf(observacion.getLatitud()));
                    bundle.putString("LONGITUD", String.valueOf(observacion.getLongitud()));
                    bundle.putString("ALTITUD", String.valueOf(observacion.getAltitud()));
                    bundle.putString("FECHA", observacion.getFecha());
                    bundle.putString("DEPARTAMENTO", observacion.getDepartamento());
                    bundle.putString("USUARIO", usuario);

                    DetalleObsFragment detalleObsFragment = new DetalleObsFragment();
                    detalleObsFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_fragment, detalleObsFragment, "Encuentro el fragment");
                    fragmentTransaction.addToBackStack(null).commit();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private String formatFecha(String fecha) {
        String[] parse = fecha.split("T");
        String[] date = parse[0].split("-");
        String fechaFormateada = date[2] + "/" + date[1] + "/" + date[0];
        return fechaFormateada;
    }

    private void getObservaciones (String username){

        Call<ArrayList<Observacion>> call = mService.getObservaciones(username);
        call.enqueue(new Callback<ArrayList<Observacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Observacion>> call, Response<ArrayList<Observacion>> response) {
                if (response.isSuccessful())
                {
                    listaObservaciones = response.body();
                    mObservacionAdapter = new ObservacionAdapter(getActivity(),listaObservaciones);
                    mObservacionesList.setAdapter(mObservacionAdapter);
                    addOnItemClickListener(mObservacionesList);

                    if (listaObservaciones.size() == 0)
                    {
                        txtResultado.setText("No tiene observaciones registradas");
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Observacion>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error de comunicacion con la API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}