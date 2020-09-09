package com.example.pf_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Button btnAceptar;
    private EditText txtCodigo;
    private EditText txtDescripcion;
    private Spinner ComboFenomeno;
    private Spinner ComboLocalidad;
    private EditText txtLongitud;
    private EditText txtLatitud;
    private EditText txtAltitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* txtCodigo = (EditText)findViewById(R.id.txtCodigo);
        btnAceptar = (Button)findViewById(R.id.btnAceptar);
        txtDescripcion = (EditText)findViewById(R.id.txtdescripcion);
        ComboFenomeno = (Spinner) findViewById(R.id.comboFenomenos);
        ComboLocalidad = (Spinner)findViewById(R.id.comboLocalidad);
        txtLatitud = (EditText)findViewById(R.id.txtlatitud);
        txtAltitud = (EditText)findViewById(R.id.txtaltitud);
        txtLongitud = (EditText)findViewById(R.id.txtlongitud);



        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                startActivity(intent);
            }
        });
*/



    }




}
