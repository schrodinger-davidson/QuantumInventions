package com.example.andy.myapplication.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


// SharedPrefercnce to store json strings
public class MyPref {

    SharedPreferences settings;
    SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "quantum_db";


    public MyPref(Context context){

        if(context == null){
            Log.d("devdx", "MyPref: null");
        }
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
    }


    // Dictionary
    public static final String PREF_DICTIONARY = "PREF_DICTIONARY";
    public String prefDictionary="";
    public String getprefDictionary() {this.prefDictionary = settings.getString(PREF_DICTIONARY,"");return this.prefDictionary; }
    public void setprefDictionary(String prefDictionary) { this.prefDictionary = prefDictionary;editor.putString(PREF_DICTIONARY,prefDictionary);editor.commit(); }




}
