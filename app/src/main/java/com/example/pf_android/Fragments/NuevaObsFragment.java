package com.example.pf_android.Fragments;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.pf_android.Apis.APIService;
import com.example.pf_android.LoginActivity;
import com.example.pf_android.Models.Fenomeno;
import com.example.pf_android.Models.Localidad;
import com.example.pf_android.Models.Observacion;
import com.example.pf_android.R;
import com.example.pf_android.remote.ApiUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NuevaObsFragment extends Fragment {

    private static String API_BASE_URL = "http://192.168.210.4:8081/TareaPDT_JSF/faces/rest/";
    private static Retrofit retrofit;
    private static Gson gson;
    private APIService mAPIService;

    private static final String TAG = "MainActivity";
    public static final String codigo = "CODIGO";
    public static final String descripcion = "DESCRIPCION";
    public static final String fenomeno = "FENOMENO";
    public static final String depto = "DEPARTAMENTO";
    public static final String localidad = "LOCALIDAD";
    public static final String zona = "ZONA";
    public static final String longitud = "LONGITUD";
    public static final String latitud = "LATITUD";
    public static final String altitud = "ALTITUD";
    public static final String fecha = "FECHA";

    private Button btnAceptar;
    private EditText txtCodigo;
    private EditText txtDescripcion;
    private Spinner comboFenomeno;
    private Spinner comboDeptos;
    private Spinner comboLocalidad;
    private Spinner comboZona;
    private EditText txtLongitud;
    private EditText txtLatitud;
    private EditText txtAltitud;
    private TextView fechaCreacion;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    String fenomenoValue;
    String deptoValue;
    String localidadValue;
    String zonaValue;
    String codigoValue;
    String descripcionValue;
    String latValue;
    String longValue;
    String altValue;
    String fechaValue;
    String usuario;

    TextView selectedFenomeno;
    TextView selectedDepartamento;
    TextView selectedLocalidad;
    TextView selectedZona;

    AppCompatActivity app = new AppCompatActivity();
    AwesomeValidation awesomeValidation;
    NuevaObsListener nuevaObsListener;
    Bundle  bundle = new Bundle();

    ArrayList<Fenomeno> listaFenomenos = new ArrayList<>();
    ArrayList<Localidad> listaLocalidades = new ArrayList<>();
    ArrayAdapter fenomenoAdapter;
    ArrayAdapter localidadAdapter;

    public interface NuevaObsListener {
        void onBundleSent(Bundle bundle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nueva_observacion_fragment,container,false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.210.4:8081/TareaPDT_JSF/faces/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        fechaCreacion = (TextView) getView().findViewById(R.id.fechaCreacion);
        txtCodigo = (EditText) getView().findViewById(R.id.txtCodigo);
        btnAceptar = (Button) getView().findViewById(R.id.btnAceptar);
        txtDescripcion = (EditText) getView().findViewById(R.id.txtdescripcion);
        comboFenomeno = (Spinner) getView().findViewById(R.id.comboFenomenos);
        //comboDeptos = (Spinner) getView().findViewById(R.id.comboDeptos);
        comboLocalidad = (Spinner) getView().findViewById(R.id.comboLocalidad);
        //comboZona = (Spinner) getView().findViewById(R.id.comboZona);
        txtLatitud = (EditText) getView().findViewById(R.id.txtlatitud);
        txtAltitud = (EditText) getView().findViewById(R.id.txtaltitud);
        txtLongitud = (EditText) getView().findViewById(R.id.txtlongitud);
        fechaCreacion = (EditText) getView().findViewById(R.id.fechaCreacion);
        usuario = getArguments().getString("usuario");

        mAPIService = ApiUtils.getAPIService();

        getFenomenos();
        getLocalidades();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(getActivity(), R.id.txtCodigo,
                RegexTemplate.NOT_EMPTY, R.string.invalidCodigo);
        awesomeValidation.addValidation(getActivity(), R.id.txtdescripcion,
                RegexTemplate.NOT_EMPTY, R.string.invalidDesc);
        awesomeValidation.addValidation(getActivity(), R.id.txtlatitud,
                RegexTemplate.NOT_EMPTY, R.string.invalidLat);
        awesomeValidation.addValidation(getActivity(), R.id.txtlongitud,
                RegexTemplate.NOT_EMPTY, R.string.invalidLong);
        awesomeValidation.addValidation(getActivity(), R.id.txtaltitud,
                RegexTemplate.NOT_EMPTY, R.string.invalidAlt);


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fenomenoValue = comboFenomeno.getSelectedItem().toString();
                deptoValue = comboDeptos.getSelectedItem().toString();
                localidadValue = comboLocalidad.getSelectedItem().toString();
                zonaValue = comboZona.getSelectedItem().toString();
                codigoValue = txtCodigo.getText().toString();
                descripcionValue = txtDescripcion.getText().toString();
                latValue = txtLatitud.getText().toString();
                longValue = txtLongitud.getText().toString();
                altValue = txtAltitud.getText().toString();
                fechaValue = fechaCreacion.getText().toString();

                Observacion obs = new Observacion(Float.valueOf(altValue), codigoValue, descripcionValue, "ACTIVO", fechaValue, fenomenoValue, Float.valueOf(latValue), localidadValue,
                        Float.valueOf(longValue), usuario);

                selectedFenomeno = (TextView) comboFenomeno.getSelectedView();
                selectedDepartamento = (TextView) comboDeptos.getSelectedView();
                selectedLocalidad = (TextView) comboLocalidad.getSelectedView();
                selectedZona = (TextView) comboZona.getSelectedView();

                if (!awesomeValidation.validate() || validarSpinner() == false) {
                    Toast.makeText(getActivity(), "Datos inválidos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Alta de observación exitoso!", Toast.LENGTH_SHORT).show();
                    bundle.putString(codigo, codigoValue);
                    bundle.putString(descripcion, descripcionValue);
                    bundle.putString(fenomeno, fenomenoValue);
                    bundle.putString(depto, deptoValue);
                    bundle.putString(localidad, localidadValue);
                    bundle.putString(zona, zonaValue);
                    bundle.putString(latitud, latValue);
                    bundle.putString(longitud, longValue);
                    bundle.putString(altitud, altValue);
                    bundle.putString(fecha, fechaValue);

                    createObservacion(obs);
                    DetalleObsFragment detalleObsFragment = new DetalleObsFragment();
                    detalleObsFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_fragment, detalleObsFragment, "Encuentro el fragment");
                    fragmentTransaction.addToBackStack(null).commit();


                }
            }
        });

        fechaCreacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy: " + day + "/" + month + "/" + year);
                String date = day + "/" + month + "/" + year;
                fechaCreacion.setText(date);

            }
        };

    }

    private boolean validarSpinner() {

        boolean exito = true;
        if (fenomenoValue.equals("Seleccione una opción")) {
            String errorString = selectedFenomeno.getResources().getString(R.string.invalidFenom);
            selectedFenomeno.setError(errorString);
            exito = false;
        }
        if (deptoValue.equals("Seleccione una opción")) {
            String errorString = selectedDepartamento.getResources().getString(R.string.invalidDepto);
            selectedDepartamento.setError(errorString);
            exito = false;
        }
        if (localidadValue.equals("Seleccione una opción")) {
            String errorString = selectedLocalidad.getResources().getString(R.string.invalidLocalidad);
            selectedLocalidad.setError(errorString);
            exito = false;
        }
        if (zonaValue.equals("Seleccione una opción")) {
            String errorString = selectedZona.getResources().getString(R.string.invalidZona);
            selectedZona.setError(errorString);
            exito = false;
        }
        return exito;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NuevaObsListener) {
            nuevaObsListener = (NuevaObsListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement NuevaObservacionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        nuevaObsListener = null;
    }

    private void getFenomenos() {
        Call<ArrayList<Fenomeno>> call = mAPIService.getFenomenos();

        call.enqueue(new Callback<ArrayList<Fenomeno>>() {
            @Override
            public void onResponse(Call<ArrayList<Fenomeno>> call, Response<ArrayList<Fenomeno>> response) {
                if (response.isSuccessful()) {
                   listaFenomenos = response.body();
                   ArrayList<String> fenomenosNombre = new ArrayList<>();
                   for (int i=0; i < listaFenomenos.size();i++) {
                        fenomenosNombre.add(listaFenomenos.get(i).getNombreFen());
                   }
                   fenomenoAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                            fenomenosNombre );
                   comboFenomeno.setAdapter(fenomenoAdapter);

                } else {
                    Log.e("FENOMENO:", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Fenomeno>> call, Throwable t) {
                    Log.w("MyTag", "requestFailed", t);
            }
        });
    }

    private void getLocalidades() {
        Call<ArrayList<Localidad>> call = mAPIService.getLocalidades();
        call.enqueue(new Callback<ArrayList<Localidad>>() {
            @Override
            public void onResponse(Call<ArrayList<Localidad>> call, Response<ArrayList<Localidad>> response) {
                if (response.isSuccessful()) {
                    listaLocalidades = response.body();
                    ArrayList<String> localidadesNombre = new ArrayList<>();
                    for (int i=0; i< listaLocalidades.size(); i++) {
                        localidadesNombre.add(listaLocalidades.get(i).getNombre());
                    }
                    localidadAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, localidadesNombre);
                    comboLocalidad.setAdapter(localidadAdapter);
                } else {
                    Log.e("LOCALIDAD", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Localidad>> call, Throwable t) {

            }
        });
    }

    private void createObservacion(Observacion observacion) {
        mAPIService.saveObservacion(observacion).enqueue(new Callback<Observacion>() {
            @Override
            public void onResponse(Call<Observacion> call, Response<Observacion> response) {
                if (response.isSuccessful()) {
                    Log.i("success", "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Observacion> call, Throwable t) {
                Log.i("success", "post submitted to API.");
            }
        });
    }

}
