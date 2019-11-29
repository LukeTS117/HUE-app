package com.example.hueapp.Hue;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Lamp implements Parcelable {

    private LampState lampState;
    private String name;
    private String uniqueID;

    boolean selected;
    private String TAG = "LAMP";

    public Lamp(LampState lampState, String name, String uniqueID) {
        this.lampState = lampState;
        this.name = name;
        this.uniqueID = uniqueID;
        this.selected = false;
        Log.i(TAG, "Lamp " + uniqueID + " created");
    }

    public Lamp(LampState lampState, String name, String uniqueID, boolean selected) {
        this.lampState = lampState;
        this.name = name;
        this.uniqueID = uniqueID;
        this.selected = false;
        Log.i(TAG, "Lamp " + uniqueID + " created");
        this.selected = selected;
    }

    protected Lamp(Parcel in) {
        lampState = in.readParcelable(LampState.class.getClassLoader());
        name = in.readString();
        uniqueID = in.readString();
        selected = in.readByte() != 0;
        TAG = in.readString();
    }

    public static final Creator<Lamp> CREATOR = new Creator<Lamp>() {
        @Override
        public Lamp createFromParcel(Parcel in) {
            return new Lamp(in);
        }

        @Override
        public Lamp[] newArray(int size) {
            return new Lamp[size];
        }
    };

    public void Selected(){
        this.selected = !this.selected;
        Log.i(TAG, "Lamp " + uniqueID + " selected");
    }

    public LampState getLampState() {
        return lampState;
    }

    public String getName() {
        return name;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getTAG() {
        return TAG;
    }

    public void setLampState(LampState lampState) {
        this.lampState = lampState;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

        parcel.writeParcelable(lampState, i);
        parcel.writeString(name);
        parcel.writeString(uniqueID);
        parcel.writeByte((byte) (selected ? 1 : 0));
        parcel.writeString(TAG);
    }
}
