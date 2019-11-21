package com.example.hueapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    SharedPreferences sharedPreferences;

    String Darkmode = "Darkmode";
    String UserKey  = "UserKey";
    String Port = "Port";
    String IP = "IP";


    public SharedPref(Context context) {
        this.sharedPreferences = context.getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);
    }

    public void setDarkmodeState(boolean state){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Darkmode, state);
        editor.apply();
    }

    public boolean loadDarkmodeState(){
        boolean state = sharedPreferences.getBoolean(Darkmode, false);
        return state;
    }

    public void setUserKey(String uk){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UserKey, uk);
        editor.apply();
    }

    public String getUserKey(){
        String uk = sharedPreferences.getString(UserKey, null);
        return uk;
    }

    public String getPort() {
        String p = sharedPreferences.getString(Port, null);
        return p;
    }

    public void setPort(String port) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Port, port);
        editor.apply();
    }

    public String getIP() {
        String ip = sharedPreferences.getString(IP, null);
        return ip;
    }

    public void setIP(String ip) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IP, ip);
        editor.apply();
    }
}
