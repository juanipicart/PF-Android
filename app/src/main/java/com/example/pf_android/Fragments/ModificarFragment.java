package com.example.pf_android.Fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.method.ScrollingMovementMethod;
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

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.pf_android.Apis.APIService;
import com.example.pf_android.Models.Fenomeno;
import com.example.pf_android.Models.Localidad;
import com.example.pf_android.Models.Observacion;
import com.example.pf_android.R;
import com.example.pf_android.remote.ApiUtils;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModificarFragment extends Fragment {

    private static final String TAG = "Modificar fragment";


    private Button btnAceptar;
    private TextView txtCodigo;
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
    private Button btnConfirmar;
    private Button btnCancelar;

    String codigo;
    String descripcion;
    String fenomeno;
    String departamento;
    String localidad;
    String zona;
    String longitud;
    String latitud;
    String altitud;
    String fecha;
    String usuario;
    Long id;

    Bundle bundle = new Bundle();
    View mView = null;
    AwesomeValidation awesomeValidation;

    public ArrayList<Fenomeno> listaFenomenos = new ArrayList<>();
    public ArrayList<Localidad> listaLocalidades = new ArrayList<>();
    public static ArrayAdapter<String> fenomenoAdapter;
    public static ArrayAdapter localidadAdapter;
    private APIService mAPIService;

    public ModificarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar,container,false);
        this.mView=view;

        fechaCreacion = (TextView) mView.findViewById(R.id.txtFecha);
        txtCodigo = (TextView) mView.findViewById(R.id.txtCodigo);
        txtDescripcion = (EditText) mView.findViewById(R.id.txtDescripcion);
        comboFenomeno = (Spinner) mView.findViewById(R.id.comboFenomenos);
        //comboDeptos = (Spinner) getView().findViewById(R.id.comboDeptos);
        comboLocalidad = (Spinner) mView.findViewById(R.id.comboLocalidad);
        txtLatitud = (EditText) mView.findViewById(R.id.txtLatitud);
        txtAltitud = (EditText) mView.findViewById(R.id.txtAltitud);
        txtLongitud = (EditText) mView.findViewById(R.id.txtLongitud);
        btnConfirmar = (Button) mView.findViewById(R.id.btnModify);
        btnCancelar = (Button) mView.findViewById(R.id.btnCancel);

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

        bundle = getArguments();
        codigo = bundle.getString("CODIGO");
        descripcion = bundle.getString("DESCRIPCION");
        fenomeno = bundle.getString("FENOMENO");
        departamento = bundle.getString("DEPARTAMENTO");
        localidad = bundle.getString("LOCALIDAD");
        zona = bundle.getString("ZONA");
        longitud = bundle.getString("LONGITUD");
        latitud = bundle.getString("LATITUD");
        altitud = bundle.getString("ALTITUD");
        fecha = bundle.getString("FECHA");
        id = bundle.getLong("ID");
        usuario = bundle.getString("USUARIO");

        txtCodigo.setText(codigo);
        txtDescripcion.setText(descripcion);
        txtDescripcion.setMovementMethod(new ScrollingMovementMethod());
        txtLongitud.setText(longitud);
        txtLatitud.setText(latitud);
        txtAltitud.setText(altitud);
        fechaCreacion.setText(fecha);


        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fenomeno = comboFenomeno.getSelectedItem().toString();
                //deptoValue = comboDeptos.getSelectedItem().toString();
                localidad = comboLocalidad.getSelectedItem().toString();
                codigo = txtCodigo.getText().toString();
                descripcion = txtDescripcion.getText().toString();
                latitud = txtLatitud.getText().toString();
                longitud = txtLongitud.getText().toString();
                altitud = txtAltitud.getText().toString();
                fecha = fechaCreacion.getText().toString();

                Observacion obs = new Observacion(Float.valueOf(altitud), codigo, descripcion, "PENDIENTE", fecha, fenomeno, Float.valueOf(latitud), localidad,
                        Float.valueOf(longitud), usuario);

                if (!awesomeValidation.validate()) {
                    Toast.makeText(getActivity(), "Datos inválidos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Alta de observación exitoso!", Toast.LENGTH_SHORT).show();
                    bundle.putString("CODIGO", codigo);
                    bundle.putString("DESCRIPCION", descripcion);
                    bundle.putString("FENOMENO", fenomeno);
                    bundle.putString("LOCALIDAD", localidad);
                    bundle.putString("LATITUD", latitud);
                    bundle.putString("LONGITUD", longitud);
                    bundle.putString("ALTITUD", altitud);
                    bundle.putString("FECHA", fecha);

                    modificarObservacion(obs, id);
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

        return view;
    }

    private void getFenomenos() {
        Call<ArrayList<Fenomeno>> call = mAPIService.getFenomenos();

        call.enqueue(new Callback<ArrayList<Fenomeno>>() {
            @Override
            public void onResponse(Call<ArrayList<Fenomeno>> call, Response<ArrayList<Fenomeno>> response) {
                if (response.isSuccessful()) {
                    ArrayList<String> fenomenosNombre = new ArrayList<>();
                    listaFenomenos = response.body();
                    for (int i=0; i < listaFenomenos.size();i++) {
                        fenomenosNombre.add(listaFenomenos.get(i).getNombreFen());
                    }
                    fenomenoAdapter = new ArrayAdapter(mView.getContext(), android.R.layout.simple_spinner_dropdown_item,
                            fenomenosNombre );
                    comboFenomeno.setAdapter(fenomenoAdapter);
                    comboFenomeno.setSelection(fenomenoAdapter.getPosition(fenomeno));

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
                    ModificarFragment.localidadAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, localidadesNombre);
                    comboLocalidad.setAdapter(localidadAdapter);
                    comboLocalidad.setSelection(localidadAdapter.getPosition(localidad));

                } else {
                    Log.e("LOCALIDAD", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Localidad>> call, Throwable t) {

            }
        });
    }

    private void modificarObservacion(Observacion observacion, long id){
        Call<Observacion> call = mAPIService.modifyObservacion(observacion, id);
        call.enqueue(new Callback<Observacion>() {
            @Override
            public void onResponse(Call<Observacion> call, Response<Observacion> response) {
                if (response.isSuccessful()) {
                    Log.i("success", "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Observacion> call, Throwable t) {
                Log.i("error", "post to API failed.");
            }
        });
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
}