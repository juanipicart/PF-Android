package com.example.pf_android.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.example.pf_android.Apis.APIService;
import com.example.pf_android.Models.Departamento;
import com.example.pf_android.Models.Fenomeno;
import com.example.pf_android.Models.Localidad;
import com.example.pf_android.Models.Observacion;
import com.example.pf_android.R;
import com.example.pf_android.Remote.ApiUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevaObsFragment extends Fragment {

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
    public static final String id = "ID";
    private static final String TAG = "MainActivity";
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
    String imagenValue;
    String usuario;
    String idValue;
    String imagePath;
    Bitmap bitmap;
    TextView selectedFenomeno;
    TextView selectedDepartamento;
    TextView selectedLocalidad;
    TextView selectedZona;
    AppCompatActivity app = new AppCompatActivity();
    AwesomeValidation awesomeValidation;
    NuevaObsListener nuevaObsListener;
    Bundle bundle = new Bundle();
    ArrayList<Fenomeno> listaFenomenos = new ArrayList<>();
    ArrayList<Localidad> listaLocalidades = new ArrayList<>();
    ArrayList<Departamento> listaDepartamentos = new ArrayList<>();
    ArrayAdapter fenomenoAdapter;
    ArrayAdapter localidadAdapter;
    ArrayAdapter departamentoAdapter;
    HashMap<String, Long> departamentos = new HashMap<>();
    private APIService mAPIService;
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
    private Button btnImagen;
    private ImageView imagen;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private static final int LOCATION_CODE = 1002;

    private LocationManager locManager;
    private Location loc;
    ProgressDialog nDialog;

    Boolean location = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nueva_observacion_fragment, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        fechaCreacion = (TextView) getView().findViewById(R.id.fechaCreacion);
        txtCodigo = (EditText) getView().findViewById(R.id.txtCodigo);
        btnAceptar = (Button) getView().findViewById(R.id.btnAceptar);
        txtDescripcion = (EditText) getView().findViewById(R.id.txtdescripcion);
        comboFenomeno = (Spinner) getView().findViewById(R.id.comboFenomenos);
        comboDeptos = (Spinner) getView().findViewById(R.id.comboDeptos);
        comboLocalidad = (Spinner) getView().findViewById(R.id.comboLocalidad);
        txtLatitud = (EditText) getView().findViewById(R.id.txtlatitud);
        txtAltitud = (EditText) getView().findViewById(R.id.txtaltitud);
        txtLongitud = (EditText) getView().findViewById(R.id.txtlongitud);
        btnImagen = (Button) getView().findViewById(R.id.btnImagen);
        imagen = (ImageView) getView().findViewById(R.id.imagenAlta);
        usuario = getArguments().getString("USUARIO");

        mAPIService = ApiUtils.getAPIService();

        getFenomenos();
        getDepartamentos();
        getLocalidades("ARTIGAS");

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(getActivity(), R.id.txtCodigo,
                RegexTemplate.NOT_EMPTY, R.string.invalidCodigo);
        awesomeValidation.addValidation(getActivity(), R.id.txtaltitud, new SimpleCustomValidation() {
            @Override
            public boolean compare(String altitud) {
                Long max = Long.valueOf(514);
                if (!altitud.isEmpty()) {
                    Double a = Double.parseDouble(altitud);
                    if (a > max) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                        return true;
                }
            }
        }, R.string.error_altitud);
        awesomeValidation.addValidation(getActivity(), R.id.txtlongitud, new SimpleCustomValidation() {
            @Override
            public boolean compare(String longitud) {
                if (!longitud.isEmpty()) {
                    Double l = Double.parseDouble(longitud);
                    if (l < -58.43) {
                        return false;
                    } else if (l > -53.18) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }, R.string.error_longitud);
        awesomeValidation.addValidation(getActivity(), R.id.txtlatitud, new SimpleCustomValidation() {
            @Override
            public boolean compare(String latitud) {
                if (!latitud.isEmpty()) {
                    Double l = Double.parseDouble(latitud);
                if (l < -34.98) {
                    return false;
                } else if (l > -30.08) {
                    return false;
                } else {
                    return true;
                } } else {
                    return true;
                }
            }
        }, R.string.error_latitud);
        awesomeValidation.addValidation(getActivity(), R.id.fechaCreacion, new SimpleCustomValidation() {
            @Override
            public boolean compare(String s) {
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date fecha = null;
                if (!s.isEmpty()) {
                    try {
                        fecha = format.parse(s);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (fecha.after(Calendar.getInstance().getTime())) {
                        Toast.makeText(getActivity(), "La fecha no puede ser futura", Toast.LENGTH_SHORT).show();
                        return false;
                    } else {
                        return true;
                    } } else {
                        return true;
                    }
                }
        }, R.string.error_fecha);
        awesomeValidation.addValidation(getActivity(), R.id.txtdescripcion,
                RegexTemplate.NOT_EMPTY, R.string.invalidDesc);
        awesomeValidation.addValidation(getActivity(), R.id.txtlatitud,
                RegexTemplate.NOT_EMPTY, R.string.invalidLat);
        awesomeValidation.addValidation(getActivity(), R.id.txtlongitud,
                RegexTemplate.NOT_EMPTY, R.string.invalidLong);
        awesomeValidation.addValidation(getActivity(), R.id.txtaltitud,
                RegexTemplate.NOT_EMPTY, R.string.invalidAlt);
        awesomeValidation.addValidation(getActivity(), R.id.fechaCreacion,
                RegexTemplate.NOT_EMPTY, R.string.invalidFecha);


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(comboFenomeno.getSelectedItem() == null || comboDeptos.getSelectedItem() == null || comboLocalidad.getSelectedItem() == null)) {
                    fenomenoValue = comboFenomeno.getSelectedItem().toString();
                    deptoValue = comboDeptos.getSelectedItem().toString();
                    localidadValue = comboLocalidad.getSelectedItem().toString();
                }
                codigoValue = txtCodigo.getText().toString();
                descripcionValue = txtDescripcion.getText().toString();
                latValue = txtLatitud.getText().toString();
                longValue = txtLongitud.getText().toString();
                altValue = txtAltitud.getText().toString();
                fechaValue = fechaCreacion.getText().toString();
                if (!(bitmap == null)) {
                    imagenValue = convertImageToBase64();
                }

                selectedFenomeno = (TextView) comboFenomeno.getSelectedView();
                selectedLocalidad = (TextView) comboLocalidad.getSelectedView();

                if (awesomeValidation.validate()) {

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
                    bundle.putString("IMAGEN", imagenValue);
                    bundle.putString("USUARIO", usuario);

                    Observacion obs = new Observacion(Float.valueOf(altValue), codigoValue, descripcionValue, "PENDIENTE", fechaValue, fenomenoValue, Float.valueOf(latValue), localidadValue,
                            Float.valueOf(longValue), usuario, imagenValue);

                    createObservacion(obs);



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
                fechaCreacion.setError(null);

            }
        };

        comboDeptos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String departamento = comboDeptos.getSelectedItem().toString();
                getLocalidades(departamento);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                        //No esta el permiso, hay que pedirlo
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //Muestro el pop up
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        //Ya tiene el permiso
                        pickImageFromGallery();
                    }
                } else {
                    //La versión del SDK android es menor a 23
                    pickImageFromGallery();
                }
            }
        });

        txtLatitud.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (txtLatitud.getText().toString().isEmpty() && location) {
                    mostrarDialogoUbicación();
                }
            }
        });

        txtLongitud.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (txtLongitud.getText().toString().isEmpty() && location) {
                    mostrarDialogoUbicación();
                }
            }
        });

        txtAltitud.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (txtAltitud.getText().toString().isEmpty() && location) {
                    mostrarDialogoUbicación();
                }
            }
        });
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
                    for (int i = 0; i < listaFenomenos.size(); i++) {
                        fenomenosNombre.add(listaFenomenos.get(i).getNombreFen());
                    }
                    fenomenoAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                            fenomenosNombre);
                    comboFenomeno.setAdapter(fenomenoAdapter);

                } else {
                    Log.e("FENOMENO:", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Fenomeno>> call, Throwable t) {
                Log.e("error", "post to API failed.");
                Toast.makeText(getActivity(), "Ocurrió un error de conexión.", Toast.LENGTH_SHORT).show();
                btnAceptar.setFocusable(true);
                btnAceptar.setEnabled(false);
            }
        });
    }

    private void getLocalidades(String nombre_depto) {
        Call<ArrayList<Localidad>> call = mAPIService.getLocalidades(nombre_depto);
        call.enqueue(new Callback<ArrayList<Localidad>>() {
            @Override
            public void onResponse(Call<ArrayList<Localidad>> call, Response<ArrayList<Localidad>> response) {
                if (response.isSuccessful()) {
                    listaLocalidades = response.body();
                    ArrayList<String> localidadesNombre = new ArrayList<>();
                    for (int i = 0; i < listaLocalidades.size(); i++) {
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
                Log.e("error", "post to API failed.");
                Toast.makeText(getActivity(), "Ocurrió un error de conexión.", Toast.LENGTH_SHORT).show();
                btnAceptar.setFocusable(true);
                btnAceptar.setEnabled(false);
            }
        });
    }

    private void getDepartamentos() {
        Call<ArrayList<Departamento>> call = mAPIService.getDepartamentos();
        call.enqueue(new Callback<ArrayList<Departamento>>() {
            @Override
            public void onResponse(Call<ArrayList<Departamento>> call, Response<ArrayList<Departamento>> response) {
                if (response.isSuccessful()) {
                    listaDepartamentos = response.body();
                    ArrayList<String> departamentosNombre = new ArrayList<>();
                    for (int i = 0; i < listaDepartamentos.size(); i++) {
                        departamentosNombre.add(listaDepartamentos.get(i).getNombre());
                    }
                    departamentoAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, departamentosNombre);
                    comboDeptos.setAdapter(departamentoAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Departamento>> call, Throwable t) {
                Log.e("error", "post to API failed.");
                Toast.makeText(getActivity(), "Ocurrió un error de conexión.", Toast.LENGTH_SHORT).show();
                btnAceptar.setFocusable(true);
                btnAceptar.setEnabled(false);
            }
        });
    }

    private void createObservacion(Observacion observacion) {
        mAPIService.saveObservacion(observacion).enqueue(new Callback<Observacion>() {
            @Override
            public void onResponse(Call<Observacion> call, Response<Observacion> response) {
                if (response.isSuccessful()) {
                    Log.i("success", "post submitted to API.");
                    Long id = (long) response.body().getId();
                    bundle.putLong("ID", id);
                    Toast.makeText(getActivity(), "Alta de observación exitoso!", Toast.LENGTH_SHORT).show();
                    DetalleObsFragment detalleObsFragment = new DetalleObsFragment();
                    detalleObsFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_fragment, detalleObsFragment, "Encuentro el fragment");
                    fragmentTransaction.addToBackStack(null).commit();
                } else if (response.code() == 400) {
                    txtCodigo.setError("El código ya existe en el sistema. Debe ingresar otro.");
                    Toast.makeText(getActivity(), "El código ya existe en el sistema. Debe ingresar otro.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Ocurrió un error al realizar el alta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Observacion> call, Throwable t) {
                Log.e("error", "post to API failed.");
                Toast.makeText(getActivity(), "Ocurrió un error al realizar el alta.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface NuevaObsListener {
        void onBundleSent(Bundle bundle);
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            }
            case LOCATION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == -1 && requestCode == IMAGE_PICK_CODE){
            imagen.setImageURI(data.getData());
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String convertImageToBase64() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imageString;
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        txtLongitud.setText(String.valueOf(loc.getLongitude()));
        txtAltitud.setText(String.valueOf(loc.getAltitude()));
        txtLatitud.setText(String.valueOf(loc.getLatitude()));
        location = true;
    }

    private void mostrarDialogoUbicación (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Obtener ubicación");
        builder.setMessage("Desea obtener su ubicación e ingresarla al formulario?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        nDialog = new ProgressDialog(getActivity());
                        nDialog.setMessage("Cargando...");
                        nDialog.setIndeterminate(false);
                        nDialog.setCancelable(true);
                        nDialog.show();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                nDialog.dismiss();
                                //Check runtime permission
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                                            == PackageManager.PERMISSION_DENIED) {
                                        //No esta el permiso, hay que pedirlo
                                        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                                        //Muestro el pop up
                                        requestPermissions(permissions, LOCATION_CODE);
                                    } else {
                                        //Ya tiene el permiso
                                        getLocation();
                                    }
                                } else {
                                    //La versión del SDK android es menor a 23
                                    getLocation();
                                }
                            }
                        }, 100);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        location = false;
                    }
                })
                .setCancelable(false)
                .show();
    }
}
