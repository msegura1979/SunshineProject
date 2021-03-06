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
    private boolean mUseTodayLayout;

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

    public void setUseTodayLayout(boolean useTodayLayout){
        mUseTodayLayout = useTodayLayout;
    }

    @Override
    public int getItemViewType (int posicion){
        //Si la posicion en la lista es 0 entonces nos dira que el View_type_today de lo contrario VIEW_TYPE_FUTURE_DAY
        return (posicion == 0 && mUseTodayLayout) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    @Override
    public int getViewTypeCount(){
        return VIEW_TYPE_COUNT;
    }




    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int tipoVista = getItemViewType(cursor.getPosition());
        int layoutId = -1;

        //Elegimos el tipo de vista segun la id
        if (tipoVista == VIEW_TYPE_TODAY){
            layoutId = R.layout.list_item_forecast_today;
            //Segun la Hora cambiamos el fondo de color
          /*  Date horaActual = new Date();
            long hora = (horaActual.getTime());
            if (hora >= 64800.000){
                parent.setBackgroundColor(Color.parseColor("#63A9EB"));
            }else{
                parent.setBackgroundColor(Color.WHITE);
            }*/
            //Fin color de fondo
        }else if (tipoVista == VIEW_TYPE_FUTURE_DAY){
            layoutId = R.layout.list_item_forecast;
        }

        View view = LayoutInflater.from(context).inflate(layoutId,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;

    }

    //Este metodo va a a hacer la distincion entre los dos tipos de vista la de Hoy y las futuras
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int viewType = getItemViewType(cursor.getPosition());

        //Aqui le indicamos que obtenga de los recurso la imagen art
        switch (viewType){
            case VIEW_TYPE_TODAY:{
                viewHolder.iconoView.setImageResource(Util.getArtResourceForWeatherCondition(cursor.getInt(MainActivityFragment.COL_WEATHER_CONDITION_ID)));
                break;
            }
            //En este caso le indicamos que obtenga el icono
            case VIEW_TYPE_FUTURE_DAY:{
                viewHolder.iconoView.setImageResource(Util.getIconResourceForWeatherCondition(cursor.getInt(MainActivityFragment.COL_WEATHER_CONDITION_ID)));
                break;
            }
        }


        //Lectura del tiempo con el ID del icono
        int tiempoId = cursor.getInt(MainActivityFragment.COL_WEATHER_ID);

        //Holder para el icono

        //viewHolder.iconoView.setImageResource(R.drawable.ic_temperature_hdpi);

        //Leemos los datos del cursor
        //Holder para el dia
        long datosEnMilesimas = cursor.getLong(MainActivityFragment.COL_WEATHER_DATE);
        viewHolder.diaView.setText(Util.getFriendlyDayString(context, datosEnMilesimas));


        //leemos el tiempo desde el cursos
        //Holder para descripcion
        String descripcion = cursor.getString(MainActivityFragment.COL_WEATHER_DESC);

        viewHolder.descripcionView.setText(descripcion);
        viewHolder.iconoView.setContentDescription(descripcion);

        //Leemos las preferencias de usuario si es metric o imperial
        boolean esMetric = Util.isMetric(context);

        //Holder para las temperaturas altas y bajas
        //Leemos la temperatura alta desde el cursor
        double altaTemp = cursor.getDouble(MainActivityFragment.COL_WEATHER_MAX_TEMP);

        viewHolder.altaTempView.setText(Util.formatTemperature(context, altaTemp,esMetric));

        //Leemos la baja temperatura desde el cursor
        double bajaTemp = cursor.getDouble(MainActivityFragment.COL_WEATHER_MIN_TEMP);
        viewHolder.bajaTempView.setText(Util.formatTemperature(context,bajaTemp,esMetric));


    }

}
