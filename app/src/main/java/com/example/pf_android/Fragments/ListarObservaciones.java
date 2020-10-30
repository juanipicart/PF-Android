package com.example.pf_android.Fragments;

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
import android.widget.ListView;
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

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import retrofit2.Retrofit;

public class ListarObservaciones extends Fragment {

    ListView mObservacionesList;
    ArrayAdapter<Observacion> mObservacionAdapter;
    private static String API_BASE_URL = "http://192.168.210.4:8081/TareaPDT_JSF/faces/rest/";
    private static Retrofit retrofit;
    private static Gson gson;
    private APIService mService;


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

        GetObservaciones tarea = new GetObservaciones();
        tarea.execute();
        mObservacionesList.setAdapter(mObservacionAdapter);
        return root;
    }

    public class GetObservaciones extends AsyncTask<Void, Void, Boolean> {

        private ArrayAdapter<Observacion> mObservacionAdapter;

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result = true;
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                String urlServicio = "http://192.168.210.4:8081/TareaPDT_JSF/faces/rest/observaciones";
                url = new URL(urlServicio);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("content-type", "application/json");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                JSONArray respJSON = new JSONArray(getResponseText(in));

                List<Observacion> observaciones = new ArrayList<>();
                for (int i = 0; i < respJSON.length(); i++) {
                    JSONObject obj = respJSON.getJSONObject(i);
                    String codigo = obj.getString("codigo_OBS");
                    String descripcion = obj.getString("descripcion");
                    String fenomeno = obj.getJSONObject("fenomeno").getString("nombreFen");
                    String localidad = obj.getJSONObject("localidad").getString("nombreLoc");
                    float longitud = BigDecimal.valueOf(obj.getDouble("longitud")).floatValue();
                    float latitud = BigDecimal.valueOf(obj.getDouble("latitud")).floatValue();
                    float altitud = BigDecimal.valueOf(obj.getDouble("altitud")).floatValue();
                    String fecha = obj.getString("fecha");
                    String estado = obj.getString("estado");
                    int id = obj.getInt("id");
                    String usuario = obj.getString("usuario");
                    observaciones.add(new Observacion(altitud,codigo,descripcion,estado,fecha,fenomeno,id,latitud,localidad,longitud,usuario));
                }
                mObservacionAdapter = new ObservacionAdapter(getActivity(), observaciones);
            } catch (Exception ex) {
                Log.e("Servicio REST", "Error!", ex);
                result = false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean == true) {
                mObservacionesList.setAdapter(mObservacionAdapter);
                addOnItemClickListener(mObservacionesList);
            }
        }

        private String getResponseText(InputStream inStream) {
            return new Scanner(inStream).useDelimiter("\\A").next();
        }
    }

    private void addOnItemClickListener(final ListView mCiudadesList) {
        mObservacionesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Observacion observacion = (Observacion) mObservacionesList.getItemAtPosition(i);
                    Bundle  bundle = new Bundle();
                    bundle.putString("CODIGO", observacion.getCodigo());
                    bundle.putString("DESCRIPCION", observacion.getDescripcion());
                    bundle.putString("FENOMENO", observacion.getCodigo());
                    bundle.putString("LOCALIDAD", observacion.getLocalidad());
                    bundle.putString("LATITUD", String.valueOf(observacion.getLatitud()));
                    bundle.putString("LONGITUD", String.valueOf(observacion.getLongitud()));
                    bundle.putString("ALTITUD", String.valueOf(observacion.getAltitud()));
                    bundle.putString("FECHA", observacion.getFecha());

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
}