package com.example.pf_android.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pf_android.Apis.APIService;
import com.example.pf_android.MainActivity;
import com.example.pf_android.Models.Observacion;
import com.example.pf_android.R;
import com.example.pf_android.remote.ApiUtils;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetalleObsFragment extends Fragment {

    private static String API_BASE_URL = ApiUtils.BASE_URL;
    private static Retrofit retrofit;
    private static Gson gson;
    private APIService mAPIService;

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
    private Button btnEliminar;
    private Button btnModificar;


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

    private EditText txtComboFen;
    Bundle bundle = new Bundle();
    View mView = null;
    ProgressDialog nDialog;

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
        txtLongitud = (TextView) mView.findViewById(R.id.txtLongitud);
        txtLatitud = (TextView) mView.findViewById(R.id.txtLatitud);
        txtAltitud = (TextView) mView.findViewById(R.id.txtAltitud);
        txtFecha = (TextView) mView.findViewById(R.id.txtFecha);
        btnEliminar = (Button) mView.findViewById(R.id.btnDelete);
        btnModificar = (Button) mView.findViewById(R.id.btnModify);

        mAPIService = ApiUtils.getAPIService();
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
        txtFenomeno.setText(fenomeno);
        txtDepartamento.setText(departamento);
        txtLocalidad.setText(localidad);
        txtLongitud.setText(longitud);
        txtLatitud.setText(latitud);
        txtAltitud.setText(altitud);
        txtFecha.setText(fecha);

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoBasico();
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarObservacion();
            }
        });

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

    private void eliminarObservacion (long id) {
        Call<Observacion> call = mAPIService.deleteObservacion(id);
        call.enqueue(new Callback<Observacion>() {
            @Override
            public void onResponse(Call<Observacion> call, Response<Observacion> response) {
                if (response.isSuccessful()) {

                    Log.i("success", "post submitted to API." + response.body().toString());
                } else {
                    Toast.makeText(getActivity(), "Ocurrió un error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Observacion> call, Throwable t) {
                Log.i("success", "post submitted to API.");
            }
        });
    }

    private void mostrarDialogoBasico (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Eliminacion");
        builder.setMessage("Quieres eliminar esta observacion?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        eliminarObservacion(id);

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
                                Toast.makeText(getActivity(), "Se elimino la observacion", Toast.LENGTH_LONG).show();
                                ListarObservaciones listarFragment = new ListarObservaciones();
                                bundle.putString("usuario", usuario);
                                listarFragment.setArguments(bundle);
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.container_fragment, listarFragment, "Encuentro el fragment");
                                fragmentTransaction.addToBackStack(null).commit();
                            }
                        }, 1000);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void modificarObservacion () {

        ModificarFragment modificarFragment = new ModificarFragment();
        modificarFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, modificarFragment, "Encuentro el fragment");
        fragmentTransaction.addToBackStack(null).commit();
    }


}
