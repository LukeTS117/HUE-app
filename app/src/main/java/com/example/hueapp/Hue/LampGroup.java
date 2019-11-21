package com.example.hueapp.Hue;

import android.util.Log;

import java.util.List;

public class LampGroup {

    private String name;
    private List<Lamp> lamps;
    private LampState lampState;

    private String TAG = "LAMP_GROUP";


    public LampGroup(String name, List<Lamp> lamps, LampState lampState) {
        this.name = name;
        this.lamps = lamps;
        this.lampState = lampState;
    }

    public void addLamp(Lamp lamp){
        this.lamps.add(lamp);
    }

    public void removeLamp(Lamp lamp){
        if(lamps.contains(lamp)){
            lamps.remove(lamp);
        }else{
            Log.i(TAG, "Lamp not found");
        }
    }
}
