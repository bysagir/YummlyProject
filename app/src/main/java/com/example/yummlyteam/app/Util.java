package com.example.yummlyteam.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Util {

    public static boolean isNetworkConnectionAvailable(Context context) {
        boolean isNetworkConnectionAvailable = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            isNetworkConnectionAvailable = (activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED);
        }
        return isNetworkConnectionAvailable;
    }

    public static String timeFormatter(Integer timeInSeconds) {
        //Added this to handle NullPointerException or timeInSeconds which is not positive number and not zero
        if (timeInSeconds == null || timeInSeconds <= 0) {
            return "";
        }
        //Update the time format
        int min = timeInSeconds / 60;
        if (min >= 60) {
            int hour = min / 60;
            if (min % 60 != 0) { //if min is not zero, added min
                min = min % 60;
                return String.valueOf(hour) + "h" + " " + min + "m";
            } else {
                return String.valueOf(hour) + "h";
            }
        }
        return String.valueOf(min) + "m";
    }

    public static String recipeNameFormatter(String recipeName) {
        if (recipeName != null) {
            StringBuilder formattedString = new StringBuilder(recipeName.length());
            String [] words = recipeName.split("\\ ");
            for (int i = 0; i < words.length; i++) {
                formattedString.append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1)).append(" ");
            }
            return formattedString.toString();
        }
        return "";
    }

}
