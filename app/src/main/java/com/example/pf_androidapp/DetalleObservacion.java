package com.example.pf_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetalleObservacion extends AppCompatActivity {

    private TextView txtCodigo;
    private TextView txtDescripcion;
    private TextView txtFenomeno;
    private TextView txtDepartamento;
    private TextView txtLocalidad;
    private TextView txtZona;
    private TextView txtLongitud;
    private TextView txtLatitud;
    private TextView txtAltitud;
    private TextView txtFecha;

    private EditText txtComboFen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_observacion);

        txtCodigo = (TextView) findViewById(R.id.txtCodigo);
        txtDescripcion = (TextView) findViewById(R.id.txtDescripcion) ;
        txtFenomeno = (TextView) findViewById(R.id.txtFenomeno);
        txtDepartamento = (TextView) findViewById(R.id.txtDepartamento);
        txtLocalidad = (TextView) findViewById(R.id.txtLocalidad);
        txtZona = (TextView) findViewById(R.id.txtZona);
        txtLongitud = (TextView) findViewById(R.id.txtLongitud);
        txtLatitud = (TextView) findViewById(R.id.txtLatitud);
        txtAltitud = (TextView) findViewById(R.id.txtAltitud);
        txtFecha = (TextView) findViewById(R.id.txtFecha);

        Intent intent = this.getIntent();
        String codigo = intent.getStringExtra(MainActivity.codigo);
        String descripcion = intent.getStringExtra(MainActivity.descripcion);
        String fenomeno = intent.getStringExtra(MainActivity.fenomeno);
        String departamento = intent.getStringExtra(MainActivity.depto);
        String localidad = intent.getStringExtra(MainActivity.localidad);
        String zona = intent.getStringExtra(MainActivity.zona);
        String longitud = intent.getStringExtra(MainActivity.longitud);
        String latitud = intent.getStringExtra(MainActivity.latitud);
        String altitud = intent.getStringExtra(MainActivity.altitud);
        String fecha = intent.getStringExtra(MainActivity.fecha);

        txtCodigo.setText(codigo);
        txtDescripcion.setText(descripcion);
        txtDescripcion.setMovementMethod(new ScrollingMovementMethod());
        txtFenomeno.setText(fenomeno);
        txtDepartamento.setText(departamento);
        txtLocalidad.setText(localidad);
        txtZona.setText(zona);
        txtLongitud.setText(longitud);
        txtLatitud.setText(latitud);
        txtAltitud.setText(altitud);
        txtFecha.setText(fecha);

    }

    @Override
    public void onBackPressed() {
        //Bloqueo el botón de BACK
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

}