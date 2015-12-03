package com.example.manuelseguranavarro.sunshineproject;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Manuel on 2/12/15.
 */
public class Adaptador extends CursorAdapter {

    private static final int VIEW_TYPE_TODAY =0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;
    private static final int VIEW_TYPE_COUNT = 1;

    public static class ViewHolder {
        public final ImageView iconoView;
        public final TextView diaView;
        public final TextView descripcionView;
        public final TextView altaTempView;
        public final TextView bajaTempView;

        public ViewHolder(View view){
            iconoView = (ImageView)view.findViewById(R.id.list_item_icon);
            diaView = (TextView)view.findViewById(R.id.list_item_date_textview);
            descripcionView =(TextView)view.findViewById(R.id.list_item_forecast_textview);
            altaTempView = (TextView)view.findViewById(R.id.list_item_high_textview);
            bajaTempView = (TextView)view.findViewById(R.id.list_item_low_textview);
        }


    }

    public Adaptador (Context context, Cursor c, int flags){
        super(context,c,flags);
    }

    @Override
    public int getItemViewType (int posicion){
        //Si la posicion en la lista es 0 entonces nos dira que el View_type_today de lo contrario VIEW_TYPE_FUTURE_DAY
        return (posicion == 0)?VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    @Override
    public int getViewTypeCount(){
        return VIEW_TYPE_COUNT;
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
        int tipoVista = getItemViewType(cursor.getPosition());
        int layoutId = -1;

        //Elegimos el tipo de vista segun la id
        if (tipoVista == VIEW_TYPE_TODAY){
            layoutId = R.layout.list_item_forecast_today;
        }else if (tipoVista == VIEW_TYPE_FUTURE_DAY){
            layoutId = R.layout.list_item_forecast;
        }

        View view = LayoutInflater.from(context).inflate(layoutId,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //Lectura del tiempo con el ID del icono
        int tiempoId = cursor.getInt(MainActivityFragment.COL_WEATHER_ID);

        //Holder para el icono
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        viewHolder.iconoView.setImageResource(R.drawable.ic_temperature_hdpi);

        //Leemos los datos del cursor
        //Holder para el dia
        long datosEnMilesimas = cursor.getLong(MainActivityFragment.COL_WEATHER_DATE);
        viewHolder.diaView.setText(Util.getFriendlyDayString(context, datosEnMilesimas));


        //leemos el tiempo desde el cursos
        //Holder para descripcion
        String descripcion = cursor.getString(MainActivityFragment.COL_WEATHER_DESC);

        viewHolder.descripcionView.setText(descripcion);

        //Leemos las preferencias de usuario si es metric o imperial
        boolean esMetric = Util.isMetric(context);

        //Holder para las temperaturas altas y bajas
        //Leemos la temperatura alta desde el cursor
        double altaTemp = cursor.getDouble(MainActivityFragment.COL_WEATHER_MAX_TEMP);

        viewHolder.altaTempView.setText(Util.formatTemperature(altaTemp,esMetric));

        //Leemos la baja temperatura desde el cursor
        double bajaTemp = cursor.getDouble(MainActivityFragment.COL_WEATHER_MIN_TEMP);
        viewHolder.bajaTempView.setText(Util.formatTemperature(bajaTemp,esMetric));


    }

}
