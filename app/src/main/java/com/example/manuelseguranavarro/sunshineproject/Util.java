package com.example.manuelseguranavarro.sunshineproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.format.Time;

import com.example.manuelseguranavarro.sunshineproject.data.WeatherContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Manuel on 2/12/15.
 */

public class Util {

    public static final String DATE_FORMAT = "yyyyMMdd";

    static String formatoTemperatura(Context context, double temperatura, boolean isMetric){

        double temp;
        if(!isMetric){
            temp = 9*temperatura/5+32;
        }else{
            temp = temperatura;
        }
        return context.getString(R.string.format_temperature, temp);

    }
    static String formatoDia(String dia){
       // Date date = WeatherContract.
    }


    public static String getPreferredLocation(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_localizacion_key),
                context.getString(R.string.pref_valor_defecto));
    }

    public static boolean isMetric(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_unidades_key),
                context.getString(R.string.pref_unidad_temp_valor_metric))
                        .equals(context.getString(R.string.pref_unidad_temp_valor_metric));
    }

    public static String formatTemperature(double temperature, boolean isMetric) {
        double temp;
        if ( !isMetric ) {
            temp = 9*temperature/5+32;
        } else {
            temp = temperature;
        }
        return String.format("%.0f", temp);
    }

    public static String formatDate(long dateInMillis) {
        Date date = new Date(dateInMillis);
        return DateFormat.getDateInstance().format(date);
    }
    public static String getFriendlyDayString(Context context, long dateInMillis) {
        // The day string for forecast uses the following logic:
        // For today: "Today, June 8"
        // For tomorrow:  "Tomorrow"
        // For the next 5 days: "Wednesday" (just the day name)
        // For all days after that: "Mon Jun 8"

        Time time = new Time();
        time.setToNow();
        long currentTime = System.currentTimeMillis();
        int julianDay = Time.getJulianDay(dateInMillis, time.gmtoff);
        int currentJulianDay = Time.getJulianDay(currentTime, time.gmtoff);

        // If the date we're building the String for is today's date, the format
        // is "Today, June 24"
        if (julianDay == currentJulianDay) {
            String today = context.getString(R.string.today);
            int formatId = R.string.format_full_friendly_date;
            return String.format(context.getString(
                    formatId,
                    today,
                    getFormattedMonthDay(context, dateInMillis)));
        } else if ( julianDay < currentJulianDay + 7 ) {
            // If the input date is less than a week in the future, just return the day name.
            return getDayName(context, dateInMillis);
        } else {
            // Otherwise, use the form "Mon Jun 3"
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(dateInMillis);
        }
    }

    public static String getDayName (Context context, long diaEnMillis){
        Time time = new Time();
        time.setToNow();
        int miDia = Time.getJulianDay(diaEnMillis,time.gmtoff);
        int currentMiDia = Time.getJulianDay(System.currentTimeMillis(),time.gmtoff);
        if (miDia == currentMiDia){
            return context.getString(R.string.today);
        }else{
            Time time1 = new Time();
            time1.setToNow();
            //Otra forma de formatear
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
            return simpleDateFormat.format(diaEnMillis);
        }

    }

    public static String getFormattedMonthDay(Context context, long diaEnMillis){
        Time time = new Time();
        time.setToNow();

        SimpleDateFormat formatoDatosDB = new SimpleDateFormat(Util.DATE_FORMAT);
        SimpleDateFormat formatoMesDia = new SimpleDateFormat("MMMM dd");
        String mesDia = formatoMesDia.format(diaEnMillis);
        return  mesDia;


    }
}
