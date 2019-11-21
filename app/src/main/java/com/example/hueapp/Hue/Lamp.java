package com.example.hueapp.Hue;

public class Lamp {

    LampState lampState;
    String name;
    String uniqueID;

    private String TAG = "LAMP";

    public Lamp(LampState lampState, String name, String uniqueID) {
        this.lampState = lampState;
        this.name = name;
        this.uniqueID = uniqueID;
    }
}
