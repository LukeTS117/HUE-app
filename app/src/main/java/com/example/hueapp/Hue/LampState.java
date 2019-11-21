package com.example.hueapp.Hue;


enum AlertState{
    NONE, SELECT, LSELECT
}

enum Effect{
    NONE, COLORLOOP
}

enum ColorMode{
    HS, XY, CT
}

public class LampState {

    private boolean on;
    private int brightness;
    private int hue;
    private int saturation;
    private float x;
    private float y;
    private int ct;
    private AlertState alertState;
    private Effect effect;
    private ColorMode colorMode;

    private String TAG = "LAMP_STATE";



    public LampState(boolean on, int brightness, int hue, int saturation) {
        this(on, brightness, hue, saturation, 0f, 0f, 0, AlertState.NONE, Effect.NONE, ColorMode.HS);
    }

    public LampState(boolean on, int brightness, int hue, int saturation, float x, float y, int ct, AlertState alertState, Effect effect, ColorMode colorMode) {
        this.on = on;
        this.brightness = brightness;
        this.hue = hue;
        this.saturation = saturation;
        this.x = x;
        this.y = y;
        this.ct = ct;
        this.alertState = alertState;
        this.effect = effect;
        this.colorMode = colorMode;
    }
}
