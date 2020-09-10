package com.example.pf_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button btnAceptar;
    private EditText txtCodigo;
    private EditText txtDescripcion;
    private Spinner ComboFenomeno;
    private Spinner ComboLocalidad;
    private EditText txtLongitud;
    private EditText txtLatitud;
    private EditText txtAltitud;
    private TextView fechaCreacion;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fechaCreacion = (TextView) findViewById(R.id.fechaCreacion);
        txtCodigo = (EditText)findViewById(R.id.txtCodigo);
        btnAceptar = (Button)findViewById(R.id.btnAceptar);
        txtDescripcion = (EditText)findViewById(R.id.txtdescripcion);
        ComboFenomeno = (Spinner) findViewById(R.id.comboFenomenos);
        ComboLocalidad = (Spinner)findViewById(R.id.comboLocalidad);
        txtLatitud = (EditText)findViewById(R.id.txtlatitud);
        txtAltitud = (EditText)findViewById(R.id.txtaltitud);
        txtLongitud = (EditText)findViewById(R.id.txtlongitud);

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

        View selectedView = ComboFenomeno.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView;
            if (selectedTextView.getText().toString().equals("Seleccione una opción")) {
                String errorString = selectedTextView.getResources().getString(R.string.invalidFenom);
                selectedTextView.setError(errorString);
            }
            else {
                selectedTextView.setError(null);
            }
        }


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (awesomeValidation.validate()) {
                    Toast.makeText(getApplicationContext(), "Alta de observación exitoso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Datos inválidos", Toast.LENGTH_LONG).show();
                }
                /*
                Intent intent = new Intent(MainActivity.this, DetalleObservacion.class);

                Bundle b = new Bundle();
                b.putString("CODIGO", txtCodigo.getText().toString());
                b.putString("DESCRIPCION", txtDescripcion.getText().toString());
                b.putString("FENOMENO", ComboFenomeno.getSelectedItem().toString());
                b.putString("LOCALIDAD", ComboLocalidad.getSelectedItem().toString());
                b.putString("ALTITUD", txtAltitud.getText().toString());
                b.putString("LATITUD", txtLatitud.getText().toString());
                b.putString("LONGITUD", txtLongitud.getText().toString());

                intent.putExtras(b);

                startActivity(intent);*/
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




}
