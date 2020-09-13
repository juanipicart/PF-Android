package com.example.pf_android.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pf_android.MainActivity;
import com.example.pf_android.R;

public class DetalleObsFragment extends Fragment {

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

    private EditText txtComboFen;
    Bundle bundle = new Bundle();
    View mView = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_observacion_fragment,container,false);
        this.mView=view;
        txtCodigo = (TextView) mView.findViewById(R.id.txtCodigo);
        txtDescripcion = (TextView) mView.findViewById(R.id.txtDescripcion);
        txtFenomeno = (TextView) mView.findViewById(R.id.txtFenomeno);
        txtDepartamento = (TextView) mView.findViewById(R.id.txtDepartamento);
        txtLocalidad = (TextView) mView.findViewById(R.id.txtLocalidad);
        txtZona = (TextView) mView.findViewById(R.id.txtZona);
        txtLongitud = (TextView) mView.findViewById(R.id.txtLongitud);
        txtLatitud = (TextView) mView.findViewById(R.id.txtLatitud);
        txtAltitud = (TextView) mView.findViewById(R.id.txtAltitud);
        txtFecha = (TextView) mView.findViewById(R.id.txtFecha);

        bundle = getArguments();

        codigo = bundle.getString(NuevaObsFragment.codigo);
        descripcion = bundle.getString(NuevaObsFragment.descripcion);
        fenomeno = bundle.getString(NuevaObsFragment.fenomeno);
        departamento = bundle.getString(NuevaObsFragment.depto);
        localidad = bundle.getString(NuevaObsFragment.localidad);
        zona = bundle.getString(NuevaObsFragment.zona);
        longitud = bundle.getString(NuevaObsFragment.longitud);
        latitud = bundle.getString(NuevaObsFragment.latitud);
        altitud = bundle.getString(NuevaObsFragment.altitud);
        fecha = bundle.getString(NuevaObsFragment.fecha);

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
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    public void actualizarBundle() {

        codigo = bundle.getString(NuevaObsFragment.codigo);
        descripcion = bundle.getString(NuevaObsFragment.descripcion);
        fenomeno = bundle.getString(NuevaObsFragment.fenomeno);
        departamento = bundle.getString(NuevaObsFragment.depto);
        localidad = bundle.getString(NuevaObsFragment.localidad);
        zona = bundle.getString(NuevaObsFragment.zona);
        longitud = bundle.getString(NuevaObsFragment.longitud);
        latitud = bundle.getString(NuevaObsFragment.latitud);
        altitud = bundle.getString(NuevaObsFragment.altitud);
        fecha = bundle.getString(NuevaObsFragment.fecha);

    }



}
