package com.example.khoslalabsassignment.appUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPref {

    Context context;
    SharedPreferences sharedpreferences;

    public static final String APP_PREF = "VDB_preference";
    public static final String APP_DATA = "app_data";

    public AppPref(Context context){
        this.context = context;
        sharedpreferences = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
    }

    public void saveData(String data){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(APP_DATA, data);
        editor.commit();
    }

    public String getData(){
        return sharedpreferences.getString(APP_DATA,null);
    }

}
