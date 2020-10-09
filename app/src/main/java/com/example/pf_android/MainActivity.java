package com.example.pf_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pf_android.Fragments.DetalleObsFragment;
import com.example.pf_android.Fragments.ListarObservaciones;
import com.example.pf_android.Fragments.MainFragment;
import com.example.pf_android.Fragments.NuevaObsFragment;
import com.example.pf_android.Fragments.ObservacionesProvider;
import com.example.pf_android.Fragments.ObservacionesService;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NuevaObsFragment.NuevaObsListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private DetalleObsFragment detalleObsFragment;
    private NuevaObsFragment nuevaObsFragment;
    private ListarObservaciones listObsFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        nuevaObsFragment = new NuevaObsFragment();
        detalleObsFragment = new DetalleObsFragment();
        listObsFragment = new ListarObservaciones();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new MainFragment());
        fragmentTransaction.commit();

        Calendar cal = Calendar.getInstance();
        Intent i = new Intent(this, ObservacionesService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60000, pintent);



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.nuevaObs) {
            closeAllFragments();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new NuevaObsFragment());
            fragmentTransaction.commit();
        }

        if (item.getItemId() == R.id.consultarObs) {
            closeAllFragments();
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
            ListarObservaciones listarObservaciones = (ListarObservaciones) getSupportFragmentManager().findFragmentById(R.id.container_observacionesFragment);

            if (listarObservaciones == null) {
                listarObservaciones = ListarObservaciones.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.container_fragment, listarObservaciones).commit();
            }
        }

        if (item.getItemId() == R.id.salir) {
            finishAffinity();
        }

        return false;
    }

    @Override
    public void onBundleSent(Bundle bundle) {
        detalleObsFragment.setArguments(bundle);

    }

     /*@Override
     public void onBackPressed() {

         Fragment f = getSupportFragmentManager().findFragmentById(R.id.container_fragment);
         if (f instanceof NuevaObsFragment || f instanceof DetalleObsFragment) {//the fragment on which you want to handle your back press
             Log.i("BACK PRESSED", "BACK PRESSED");
         }else{
             super.onBackPressed();
         }
     }*/
     public void closeAllFragments() {
         for (Fragment fragment : getSupportFragmentManager().getFragments()) {
             getSupportFragmentManager().beginTransaction().remove(fragment).commit();
         }
     }
}