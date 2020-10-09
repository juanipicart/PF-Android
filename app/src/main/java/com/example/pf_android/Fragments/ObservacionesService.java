package com.example.pf_android.Fragments;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ObservacionesService extends Service {
    public ObservacionesService() {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Cursor cursor = getContentResolver().query(ObservacionesProvider.CONTENT_URI, null, null, null, null);
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
        return flags;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Servicio destruido",
                Toast.LENGTH_LONG).show();

    }

    public class AlarmReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {

        }
    }
}