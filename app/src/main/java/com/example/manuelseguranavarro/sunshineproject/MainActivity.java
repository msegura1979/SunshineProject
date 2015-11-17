package com.example.manuelseguranavarro.sunshineproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.manuelseguranavarro.sunshineproject.Settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        //Accion para el acceso a los mapas
        if (id == R.id.acceso_map){
            preferenciasDelMapa();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
   private void preferenciasDelMapa(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String localizacion = preferences.getString(getString(R.string.pref_localizacion_key), getString(R.string.pref_valor_defecto));
        Uri obtenLocalizacion = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q",localizacion).build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(obtenLocalizacion);

        if(intent.resolveActivity(getPackageManager())!= null){
            startActivity(intent);
        }else{
            Log.d(LOG_TAG, "No puede realizar la llamada a la " + localizacion + "No tienes app de mapas instalada");
        }

    }

}
