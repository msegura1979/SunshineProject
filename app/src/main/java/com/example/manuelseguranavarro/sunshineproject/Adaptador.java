package com.example.manuelseguranavarro.sunshineproject;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Manuel on 2/12/15.
 */
public class Adaptador extends CursorAdapter {

    public Adaptador (Context context, Cursor c, int flags){
        super(context,c,flags);
    }

    private String formatHighLows(double high, double low) {
        boolean isMetric = Util.isMetric(mContext);
        String highLowStr = Util.formatTemperature(high, isMetric) + "/" + Util.formatTemperature(low, isMetric);
        return highLowStr;
    }

    /*
        This is ported from FetchWeatherTask --- but now we go straight from the cursor to the
        string.
     */
    private String convertCursorRowToUXFormat(Cursor cursor) {

        // get row indices for our cursor



        String highAndLow = formatHighLows(
                cursor.getDouble(MainActivityFragment.COL_WEATHER_MAX_TEMP),
                cursor.getDouble(MainActivityFragment.COL_WEATHER_MIN_TEMP));

        return Util.formatDate(cursor.getLong(MainActivityFragment.COL_WEATHER_DATE)) +
                " - " + cursor.getString(MainActivityFragment.COL_WEATHER_DESC) +
                " - " + highAndLow;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_forecast, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv = (TextView)view;
        tv.setText(convertCursorRowToUXFormat(cursor));
    }
}
