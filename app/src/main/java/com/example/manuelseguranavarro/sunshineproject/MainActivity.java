package com.example.manuelseguranavarro.sunshineproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.manuelseguranavarro.sunshineproject.Detalle.DetalleActivity;
import com.example.manuelseguranavarro.sunshineproject.Detalle.DetalleActivityFragment;
import com.example.manuelseguranavarro.sunshineproject.Settings.SettingsActivity;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback{
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private String mlocation;
    private final String FORECASTFRAGMENT_TAG = "FFTAG";

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        mlocation = Util.getPreferredLocation(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_logo);
        if (findViewById(R.id.weather_detail_container) != null){
            twoPane = true;

            if (savedInstanceState == null){
                getSupportFragmentManager().beginTransaction().replace(R.id.weather_detail_container,
                        new DetalleActivityFragment()).commit();
            }
        }else {
            twoPane = false;
            getSupportActionBar().setElevation(0f);
        }

        MainActivityFragment mainActivityFragment = ((MainActivityFragment)getSupportFragmentManager().
                findFragmentById(R.id.fragment));
        mainActivityFragment.setUseTodayLayout(!twoPane);
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

       String localizacion = Util.getPreferredLocation(this);
       Uri obtenLocalizacion = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q",localizacion).build();
       Intent intent = new Intent(Intent.ACTION_VIEW);
       intent.setData(obtenLocalizacion);

       if(intent.resolveActivity(getPackageManager())!= null){
           startActivity(intent);
       }else{
           Log.d(LOG_TAG, "No puede realizar la llamada a la " + localizacion + "No tienes app de mapas instalada");
       }

    }


    @Override
    protected void onResume() {
        super.onResume();
        String location = Util.getPreferredLocation( this );
        // update the location in our second pane using the fragment manager
      /*  if (location != null && !location.equals(mlocation)) {
            MainActivityFragment ff = (MainActivityFragment)getSupportFragmentManager().findFragmentByTag(FORECASTFRAGMENT_TAG);
            if ( null != ff ) {
                ff.onLocationChanged();
            }*/
        if (location != null && !location.equals(mlocation)) {
            MainActivityFragment ff = (MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
            if ( null != ff ) {
                ff.onLocationChanged();
            }
            DetalleActivityFragment df = (DetalleActivityFragment)getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
            if ( null != df ) {
                df.onLocationChanged(location);
            }

            mlocation = location;
        }
    }

    @Override
    public void onItemSelected(Uri contentUri) {
        if (twoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putParcelable(DetalleActivityFragment.DETAIL_URI, contentUri);

            DetalleActivityFragment fragment = new DetalleActivityFragment();
            fragment.setArguments(args);

            //Sustituimos el DetalleActivityFragment que existe en el weather_detail_container.xml utilizando DETAILFRAGEMT_TAG
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.weather_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetalleActivity.class)
                    .setData(contentUri);
            startActivity(intent);
        }
    }

}
