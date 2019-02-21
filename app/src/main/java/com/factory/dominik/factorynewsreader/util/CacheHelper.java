package com.factory.dominik.factorynewsreader.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.factory.dominik.factorynewsreader.data.model.ArticlesModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CacheHelper {
    public static final String ARTICLES = "articles";
    public static final String LAST_TIME = "lasttime";
    public static final long CACHE_INVALID_TIME = 5 * 60 * 1000;

    public static void saveArticles(Context context, List<ArticlesModel> articles) {
        final Gson gson = new Gson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(articles);
        editor.putString(ARTICLES, json);
        editor.apply();
    }

    public static ArrayList<ArticlesModel> loadArticles(Context context){
        final Gson gson = new Gson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = sharedPreferences.getString(ARTICLES, null);
        Type type = new TypeToken<ArrayList<ArticlesModel>>() {}.getType();
        return gson.fromJson(json, type);

    }

    public static void saveLastTime(Context context, long lasttime){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(LAST_TIME, lasttime);
        editor.apply();
    }

    public static long loadLastTime(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getLong(LAST_TIME, 0);
    }

    public static boolean shouldFetchNewData(Context context, long currentTime){
        long lastCacheTime = loadLastTime(context);
        return currentTime - lastCacheTime > CACHE_INVALID_TIME;
    }
}
