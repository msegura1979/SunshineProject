package com.example.manuelseguranavarro.sunshineproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.format.Time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Manuel on 2/12/15.
 */

public class Util {

    public static final String DATE_FORMAT = "yyyyMMdd";






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

    public static String formatTemperature(Context context,double temperature, boolean isMetric) {
        double temp;
        if ( !isMetric ) {
            temp = 9*temperature/5+32;
        } else {
            temp = temperature;
        }
        return context.getString(R.string.format_temperature, temp);
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
    //Metodo para formatear la velocidad del viento
    public static String getFormattedWind(Context context, float windSpeed, float degrees){
        int windFormat = 0;
        if (Util.isMetric(context)){
            windFormat = R.string.format_wind_kmh;
            windSpeed = .621371192237334f * windSpeed;
        }

        String direction = "Desconocido";
        if (degrees >= 337.5 || degrees <= 22.5){
            direction="N";
        }else if(degrees >= 22.5 && degrees < 67.5){
            direction="NE";
        }else if(degrees >= 67.5 && degrees < 112.5){
            direction="E";
        }else if(degrees >= 112.5 && degrees < 157.5){
            direction="SE";
        }else if(degrees >= 157.5 && degrees < 202.5){
            direction="S";
        }else if(degrees >= 202.5 && degrees < 247.5){
            direction="SW";
        }else if(degrees >= 247.5 && degrees < 292.5){
            direction="W";
        }else if(degrees >= 292.5 && degrees < 22.5){
            direction="NW";
        }
        return String.format(context.getString(windFormat), windSpeed, direction);
    }

    //Metodo para obtener el icono segun las condiciones del tiempo
    public static int getIconResourceForWeatherCondition(int weatherId) {
        // Based on weather code data found at:
        // http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.ic_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.ic_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.ic_rain;
        } else if (weatherId == 511) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.ic_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.ic_fog;
        } else if (weatherId == 761 || weatherId == 781) {
            return R.drawable.ic_storm;
        } else if (weatherId == 800) {
            return R.drawable.ic_clear;
        } else if (weatherId == 801) {
            return R.drawable.ic_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.ic_cloudy;
        }
        return -1;
    }

    /**
     * Helper method to provide the art resource id according to the weather condition id returned
     * by the OpenWeatherMap call.
     * @param weatherId from OpenWeatherMap API response
     * @return resource id for the corresponding image. -1 if no relation is found.
     */
    public static int getArtResourceForWeatherCondition(int weatherId) {
        // Based on weather code data found at:
        // http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.art_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.art_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.art_rain;
        } else if (weatherId == 511) {
            return R.drawable.art_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.art_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.art_rain;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.art_fog;
        } else if (weatherId == 761 || weatherId == 781) {
            return R.drawable.art_storm;
        } else if (weatherId == 800) {
            return R.drawable.art_clear;
        } else if (weatherId == 801) {
            return R.drawable.art_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.art_clouds;
        }
        return -1;
    }

}
