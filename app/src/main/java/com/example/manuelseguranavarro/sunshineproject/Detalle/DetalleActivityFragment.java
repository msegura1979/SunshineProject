package com.example.manuelseguranavarro.sunshineproject.Detalle;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import com.example.manuelseguranavarro.sunshineproject.Util;
import com.example.manuelseguranavarro.sunshineproject.data.WeatherContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetalleActivityFragment extends Fragment implements LoaderCallbacks<Cursor> {
    //Log para detectar errores
    private static final String LOG_TAG = DetalleActivityFragment.class.getSimpleName();
    //Hashtag compartido
    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    private static final int DETALLE_LOADER=0;
    //Esta variable la convertimos en variable miembro
    private String mForecastStr;
    private ShareActionProvider mShareActionProvider;
    private static final int DETAIL_LOADER = 0;



    private static final String[] FORECAST_COLUMNS = {
            WeatherContract.WeatherEntry.TABLE_NAME + "." + WeatherContract.WeatherEntry._ID,
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
    };

    // these constants correspond to the projection defined above, and must change if the
    // projection changes
    private static final int COL_WEATHER_ID = 0;
    private static final int COL_WEATHER_DATE = 1;
    private static final int COL_WEATHER_DESC = 2;
    private static final int COL_WEATHER_MAX_TEMP = 3;
    private static final int COL_WEATHER_MIN_TEMP = 4;

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
        if (mForecastStr != null){
            shareActionProvider.setShareIntent(compartirForecastIntent());
        }
       /* if (shareActionProvider != null ) {
            shareActionProvider.setShareIntent(compartirForecastIntent());
        } else {
            Log.d(LOG_TAG, "Share Action Provider is null?");
        }*/

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "In onCreateLoader");
        Intent intent = getActivity().getIntent();
        if (intent == null) {
            return null;
        }

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                getActivity(),
                intent.getData(),
                FORECAST_COLUMNS,
                null,
                null,
                null
        );
    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v(LOG_TAG, "In onLoadFinished");
        if (!data.moveToFirst()) { return; }

        String dateString = Util.formatDate(
                data.getLong(COL_WEATHER_DATE));

        String weatherDescription =
                data.getString(COL_WEATHER_DESC);

        boolean isMetric = Util.isMetric(getActivity());

        String high = Util.formatTemperature(
                data.getDouble(COL_WEATHER_MAX_TEMP), isMetric);

        String low = Util.formatTemperature(
                data.getDouble(COL_WEATHER_MIN_TEMP), isMetric);

        mForecastStr = String.format("%s - %s - %s/%s", dateString, weatherDescription, high, low);

        TextView detailTextView = (TextView)getView().findViewById(R.id.textodetalle);
        detailTextView.setText(mForecastStr);

        // If onCreateOptionsMenu has already happened, we need to update the share intent now.
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(compartirForecastIntent());
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
