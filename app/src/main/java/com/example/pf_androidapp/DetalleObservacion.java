package com.example.pf_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class DetalleObservacion extends AppCompatActivity {

    private Button btnAceptar;
    private EditText txtCodigo;
    private EditText txtDescripcion;
    private Spinner ComboFenomeno;
    private Spinner ComboLocalidad;
    private EditText txtLongitud;
    private EditText txtLatitud;
    private EditText txtAltitud;

    private EditText txtComboFen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_observacion);

        setContentView(R.layout.activity_detalle_observacion);

        txtComboFen = (EditText) ComboFenomeno.getText();
        txtCodigo = (EditText)findViewById(R.id.txtCodigo);
        btnAceptar = (Button)findViewById(R.id.btnAceptar);
        txtDescripcion = (EditText)findViewById(R.id.txtdescripcion);
        ComboFenomeno = (Spinner) findViewById(R.id.comboFenomenos);
        ComboLocalidad = (Spinner)findViewById(R.id.comboLocalidad);
        txtLatitud = (EditText)findViewById(R.id.txtlatitud);
        txtAltitud = (EditText)findViewById(R.id.txtaltitud);
        txtLongitud = (EditText)findViewById(R.id.txtlongitud);



        Bundle bundle = this.getIntent().getExtras();

        txtCodigo.setText("Codigo:" + bundle.getString("CODIGO"));
        txtDescripcion.setText("Descripcion: " + bundle.getString("DESCRIPCION"));
        txtDescripcion.setText("Descripcion: " + bundle.getString("DESCRIPCION"));
        txtDescripcion.setText("Descripcion: " + bundle.getString("DESCRIPCION"));
    }

    private int getSelectedItem(Spinner spinner, String value){
        int index = 0;
        for (int i=0; i<spinner.getCount();i++) {
            if (spinner.getItemAtPosition(i).equals(value))
                index = i;
        }
    }

}