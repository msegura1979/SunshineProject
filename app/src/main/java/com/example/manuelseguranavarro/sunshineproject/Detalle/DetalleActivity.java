package com.example.manuelseguranavarro.sunshineproject.Detalle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

import com.example.manuelseguranavarro.sunshineproject.R;
import com.example.manuelseguranavarro.sunshineproject.Settings.SettingsActivity;

public class DetalleActivity extends AppCompatActivity {

    //Log para detectar errores
    private static final String LOG_TAG = DetalleActivityFragment.class.getSimpleName();
    //Hashtag compartido
    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    //Esta variable la convertimos en variable miembro
    private String mForecastStr;
    private ShareActionProvider mShareActionProvider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    //Incluimos el PlaceHolder que contine
                    .add(R.id.container, new DetalleActivityFragment())
                    .commit();
        }*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detalle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
