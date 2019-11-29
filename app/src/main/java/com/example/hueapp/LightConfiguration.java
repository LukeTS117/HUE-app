package com.example.hueapp;

import android.content.Context;
import android.util.Log;
import android.widget.SeekBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hueapp.Hue.Lamp;
import com.example.hueapp.Hue.LampState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LightConfiguration {

    private String userKey;
    private String hostIP;
    private String portNr;
    private int numberOfLights;
    private LampListner listner;

    public int getNumberOfLights() {
        return numberOfLights;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public void setPortNr(String portNr) {
        this.portNr = portNr;
    }

    RequestQueue requestQueue;

    public LightConfiguration(String userKey, String hostIP, String portNr, Context context, LampListner lampListner){
        this.userKey = userKey;
        this.hostIP = hostIP;
        this.portNr = portNr;
        requestQueue = Volley.newRequestQueue(context);
        this.listner = lampListner;
    }



    public void sendToHueBridge(int hue, int saturation, int brightness, String lightNR){
        CustomJsonArrayRequest request = new CustomJsonArrayRequest(
                Request.Method.PUT,
                buildUrl(lightNR),
                buildBody(hue, saturation, brightness),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.i("RESPONSE", "Response=" + response.toString());
                            numberOfLights = response.length();
                            Log.d("DEBUG", "Lights Available: " + response.length());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", error.toString());
                    }
                }
        );
        requestQueue.add(request);
    }

    public void getLamps(){
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                getterBuildURL(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for(int i = 0; response.length() > i; i++){
                                JSONObject payload = response.getJSONObject(i+1+ "");
                                Boolean isOn = payload.getJSONObject("state").getBoolean("on");
                                int hue = payload.getJSONObject("state").getInt("hue");
                                int bri = payload.getJSONObject("state").getInt("bri");
                                int sat = payload.getJSONObject("state").getInt("sat");
                                String uniqueID = payload.getString("uniqueid");

                                LampState lampState = new LampState(isOn, bri, hue, sat);
                                Lamp lamp = new Lamp(lampState, "Lamp " + i+1, uniqueID, i+1);

                                listner.onLampAvailable(lamp);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", error.toString());
                    }
                }
        );
        requestQueue.add(request);
    }

    private String buildUrl(String lightNR){
        String url = "http://" + this.hostIP + ":" + this.portNr + buildApiString(lightNR);
        return url;
    }

    private String buildApiString(String lightNR){
        String apiString = "/api/" + this.userKey +"/lights/" + lightNR + "/state";
        return apiString;
    }

    private String getterBuildURL(){
        String url = "http://" + this.hostIP + ":" + this.portNr + getterBuildAPI();
        return url;
    }
    private String getterBuildAPI(){
        String apiString = "/api/" + this.userKey +"/lights/";
        return apiString;
    }

    private JSONObject buildBody(int hue, int saturation, int brightness){
        JSONObject body = new JSONObject();

        try{
            body.put("on", true);
            body.put("hue", hue);
            body.put("sat", saturation);
            body.put("bri", brightness);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return body;
    }



}
