package com.advancedtimecontrol.motivateyourself;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class SharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<MotivationStatement> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.apply();
    }

    public void addFavorite(Context context, MotivationStatement motivationStatement) {
        List<MotivationStatement> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<MotivationStatement>();
        favorites.add(motivationStatement);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, MotivationStatement motivationStatement) {
        ArrayList<MotivationStatement> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(motivationStatement);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<MotivationStatement> getFavorites(Context context) {
        SharedPreferences settings;
        List<MotivationStatement> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
          MotivationStatement[] favoriteItems = gson.fromJson(jsonFavorites,
                   MotivationStatement[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<MotivationStatement>(favorites);
        } else
            return null;

        return (ArrayList<MotivationStatement>) favorites;
    }
}
