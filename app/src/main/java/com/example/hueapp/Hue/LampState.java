package com.example.hueapp.Hue;


import android.os.Parcel;
import android.os.Parcelable;

enum AlertState{
    NONE, SELECT, LSELECT
}

enum Effect{
    NONE, COLORLOOP
}

enum ColorMode{
    HS, XY, CT
}

public class LampState implements Parcelable {

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

    protected LampState(Parcel in) {
        on = in.readByte() != 0;
        brightness = in.readInt();
        hue = in.readInt();
        saturation = in.readInt();
        x = in.readFloat();
        y = in.readFloat();
        ct = in.readInt();
        TAG = in.readString();

        alertState = AlertState.values()[in.readInt()];
        effect = Effect.values()[in.readInt()];
        colorMode = ColorMode.values()[in.readInt()];
    }

    public static final Creator<LampState> CREATOR = new Creator<LampState>() {
        @Override
        public LampState createFromParcel(Parcel in) {
            return new LampState(in);
        }

        @Override
        public LampState[] newArray(int size) {
            return new LampState[size];
        }
    };

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getCt() {
        return ct;
    }

    public void setCt(int ct) {
        this.ct = ct;
    }

    public AlertState getAlertState() {
        return alertState;
    }

    public void setAlertState(AlertState alertState) {
        this.alertState = alertState;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public ColorMode getColorMode() {
        return colorMode;
    }

    public void setColorMode(ColorMode colorMode) {
        this.colorMode = colorMode;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (on ? 1 : 0));
        parcel.writeInt(brightness);
        parcel.writeInt(hue);
        parcel.writeInt(saturation);
        parcel.writeFloat(x);
        parcel.writeFloat(y);
        parcel.writeInt(ct);
        parcel.writeString(TAG);

        parcel.writeInt(alertState.ordinal());
        parcel.writeInt(effect.ordinal());
        parcel.writeInt(colorMode.ordinal());
    }
}
