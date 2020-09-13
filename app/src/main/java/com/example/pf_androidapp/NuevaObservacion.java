package com.example.pf_androidapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class NuevaObservacion extends AppCompatActivity {

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

    NavigationView navigationView;
    Toolbar toolbar;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fechaCreacion = (TextView) findViewById(R.id.fechaCreacion);
        txtCodigo = (EditText)findViewById(R.id.txtCodigo);
        btnAceptar = (Button)findViewById(R.id.btnAceptar);
        txtDescripcion = (EditText)findViewById(R.id.txtdescripcion);
        comboFenomeno = (Spinner) findViewById(R.id.comboFenomenos);
        comboDeptos = (Spinner) findViewById(R.id.comboDeptos);
        comboLocalidad = (Spinner)findViewById(R.id.comboLocalidad);
        comboZona = (Spinner) findViewById(R.id.comboZona);
        txtLatitud = (EditText)findViewById(R.id.txtlatitud);
        txtAltitud = (EditText)findViewById(R.id.txtaltitud);
        txtLongitud = (EditText)findViewById(R.id.txtlongitud);
        fechaCreacion = (EditText) findViewById(R.id.fechaCreacion);






        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.txtCodigo,
                RegexTemplate.NOT_EMPTY, R.string.invalidCodigo);
        awesomeValidation.addValidation(this, R.id.txtdescripcion,
                RegexTemplate.NOT_EMPTY, R.string.invalidDesc);
        awesomeValidation.addValidation(this, R.id.txtlatitud,
                RegexTemplate.NOT_EMPTY, R.string.invalidLat);
        awesomeValidation.addValidation(this, R.id.txtlongitud,
                RegexTemplate.NOT_EMPTY, R.string.invalidLong);
        awesomeValidation.addValidation(this, R.id.txtaltitud,
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
                    Toast.makeText(getApplicationContext(), "Datos inválidos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Alta de observación exitoso!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, DetalleObservacion.class);

                    intent.putExtra(codigo, codigoValue);
                    intent.putExtra(descripcion, descripcionValue);
                    intent.putExtra(fenomeno, fenomenoValue);
                    intent.putExtra(depto, deptoValue);
                    intent.putExtra(localidad, localidadValue);
                    intent.putExtra(zona, zonaValue);
                    intent.putExtra(latitud, latValue);
                    intent.putExtra(longitud, longValue);
                    intent.putExtra(altitud, altValue);
                    intent.putExtra(fecha, fechaValue);

                    if (validarSpinner()) {
                        startActivity(intent); }

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
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){


        getMenuInflater().inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuitem){
        int id = menuitem.getItemId();

        if(id == R.id.botonSalir) {
            finishAffinity();
        }

        return super.onOptionsItemSelected(menuitem);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
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

}
