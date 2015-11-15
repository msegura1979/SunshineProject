package com.example.manuelseguranavarro.sunshineproject.Detalle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manuelseguranavarro.sunshineproject.R;
import com.example.manuelseguranavarro.sunshineproject.Settings.SettingsActivity;

public class DetalleActivity extends AppCompatActivity {
//    TextView textView = (TextView)findViewById(R.id.detalle_tiempo);
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


       /* Bundle bundle = getIntent().getExtras();
        String datos =bundle.getString("ID");
        textView.setText(datos);*/



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

    /**
     * A placeholder fragment containing a simple view.
     */
  /*  public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

      @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detalle, container, false);

            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
                String detalle_extra = intent.getStringExtra(Intent.EXTRA_TEXT);
                ((TextView)rootView.findViewById(R.id.textodetalle)).setText(detalle_extra);
                }



            return rootView;
        }
    }*/

}
