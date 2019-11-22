package com.example.hueapp.Hue;

import android.util.Log;

public class Lamp {

    LampState lampState;
    String name;
    String uniqueID;

    boolean selected;
    private String TAG = "LAMP";

    public Lamp(LampState lampState, String name, String uniqueID) {
        this.lampState = lampState;
        this.name = name;
        this.uniqueID = uniqueID;
        this.selected = false;
        Log.i(TAG, "Lamp " + uniqueID + " created");
    }

    public void Selected(){
        this.selected = !this.selected;
        Log.i(TAG, "Lamp " + uniqueID + " selected");
    }
}
