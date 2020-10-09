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
import com.example.pf_android.Entities.Observacion;
import com.example.pf_android.R;

import java.util.Calendar;

public class NuevaObsFragment extends Fragment {

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

    TextView selectedFenomeno;
    TextView selectedDepartamento;
    TextView selectedLocalidad;
    TextView selectedZona;

    AppCompatActivity app = new AppCompatActivity();
    AwesomeValidation awesomeValidation;
    NuevaObsListener nuevaObsListener;
    Bundle  bundle = new Bundle();

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        fechaCreacion = (TextView) getView().findViewById(R.id.fechaCreacion);
        txtCodigo = (EditText) getView().findViewById(R.id.txtCodigo);
        btnAceptar = (Button) getView().findViewById(R.id.btnAceptar);
        txtDescripcion = (EditText) getView().findViewById(R.id.txtdescripcion);
        comboFenomeno = (Spinner) getView().findViewById(R.id.comboFenomenos);
        comboDeptos = (Spinner) getView().findViewById(R.id.comboDeptos);
        comboLocalidad = (Spinner) getView().findViewById(R.id.comboLocalidad);
        comboZona = (Spinner) getView().findViewById(R.id.comboZona);
        txtLatitud = (EditText) getView().findViewById(R.id.txtlatitud);
        txtAltitud = (EditText) getView().findViewById(R.id.txtaltitud);
        txtLongitud = (EditText) getView().findViewById(R.id.txtlongitud);
        fechaCreacion = (EditText) getView().findViewById(R.id.fechaCreacion);

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

                selectedFenomeno = (TextView) comboFenomeno.getSelectedView();
                selectedDepartamento = (TextView) comboDeptos.getSelectedView();
                selectedLocalidad = (TextView) comboLocalidad.getSelectedView();
                selectedZona = (TextView) comboZona.getSelectedView();

                if (!awesomeValidation.validate() || validarSpinner() == false) {
                    Toast.makeText(getActivity(), "Datos inválidos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Alta de observación exitoso!", Toast.LENGTH_SHORT).show();

                    ContentValues values = new ContentValues();
                    values.put(ObservacionesProvider.CODIGO, codigoValue);
                    values.put(ObservacionesProvider.DESCRIPCION, descripcionValue);
                    values.put(ObservacionesProvider.FENOMENO, fenomenoValue);
                    values.put(ObservacionesProvider.DEPARTAMENTO, deptoValue);
                    values.put(ObservacionesProvider.LOCALIDAD, localidadValue);
                    values.put(ObservacionesProvider.ZONA, zonaValue);
                    values.put(ObservacionesProvider.LONGITUD, longValue);
                    values.put(ObservacionesProvider.LATITUD, latValue);
                    values.put(ObservacionesProvider.ALTITUD, altValue);
                    values.put(ObservacionesProvider.DATE, fechaValue);
                    Uri uri = getActivity().getContentResolver().insert(ObservacionesProvider.CONTENT_URI, values);

                    Cursor cursor = getActivity().getContentResolver().query(ObservacionesProvider.CONTENT_URI, null, null, null, null);
                    Log.i("MSG", "Estas son las observaciones en la base de datos local");
                    while (cursor.moveToNext()) {
                        String codigo = cursor.getString(1);
                        String descripcion = cursor.getString(2);
                        String fenomeno = cursor.getString(3);
                        String departamento = cursor.getString(4);
                        String localidad = cursor.getString(5);
                        String zona = cursor.getString(6);
                        String longitud = cursor.getString(7);
                        String latitud = cursor.getString(8);
                        String altitud = cursor.getString(9);
                        String date = cursor.getString(10);
                        String StringToLog = "Código: " + codigo + ", Descripción: " + descripcion + ", Fenomeno: " + fenomeno + ", Departamento: " + departamento + ", Localidad: " + localidad +
                                ", Zona: " + zona + ", Longitud: " + longitud + ", Latitud: " + latitud + ", Altitud: " + altitud + ", Fecha " + date;
                        Log.i("Observación", StringToLog);
                    }

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


}
