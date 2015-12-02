package com.example.manuelseguranavarro.sunshineproject.Detalle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manuelseguranavarro.sunshineproject.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetalleActivityFragment extends Fragment {
    //Log para detectar errores
    private static final String LOG_TAG = DetalleActivityFragment.class.getSimpleName();
    //Hashtag compartido
    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    private static final int DETALLE_LOADER=0;
    //Esta variable la convertimos en variable miembro
    private String mForecastStr;
    public DetalleActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detalle, container, false);

        Intent intent = getActivity().getIntent();

        if (intent != null) {
            mForecastStr = intent.getDataString();
        }
        if (null != mForecastStr) {
            ((TextView) rootView.findViewById(R.id.textodetalle))
                    .setText(mForecastStr);
        /*if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            mForecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            ((TextView)rootView.findViewById(R.id.textodetalle)).setText(mForecastStr);

        }*/
        }
        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.detalle_fragmet, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);

        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if (shareActionProvider != null ) {
            shareActionProvider.setShareIntent(compartirForecastIntent());
        } else {
            Log.d(LOG_TAG, "Share Action Provider is null?");
        }

    }

    //Metodo para poder compartir
    private  Intent compartirForecastIntent(){
        //Creamos un intent compartido que utiliza ACTION_SEND
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        //Evitamos que la actividad que compartimos se hubique en otra pila de actividad
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        //Indicamos a android que el texto no tiene formato
        shareIntent.setType("text/plain");
        //Compartimos el hashtag y el string
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                mForecastStr + FORECAST_SHARE_HASHTAG);
        return shareIntent;
    }
}
