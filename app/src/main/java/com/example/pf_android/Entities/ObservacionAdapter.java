package com.example.pf_android.Entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pf_android.Models.Observacion;
import com.example.pf_android.R;

import org.w3c.dom.Text;

import java.util.List;

public class ObservacionAdapter extends ArrayAdapter<Observacion> {

    public ObservacionAdapter(Context context, List<Observacion> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (null == convertView) {
            convertView = inflater.inflate(R.layout.list_item_observacion, parent, false);
        }

        TextView fenomeno = (TextView) convertView.findViewById(R.id.txtObsFenom);
        TextView fecha = (TextView) convertView.findViewById(R.id.txtFecha);
        TextView localidad = (TextView) convertView.findViewById(R.id.txtLocalidad);

//        Observacion observacion = getItem(position);
//
//        fenomeno.setText(observacion.getFenomeno() + " - ");
//        fecha.setText(observacion.getDate());
//        localidad.setText(observacion.getLocalidad());
//        departamento.setText(observacion.getDepartmanto() + ", ");

        fenomeno.setText(observacion.getFenomeno() + " - ");
        fecha.setText(observacion.getFecha());
        localidad.setText(observacion.getLocalidad());

        return convertView;
    }
}
