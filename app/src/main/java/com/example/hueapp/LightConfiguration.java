package com.example.hueapp;

import android.content.Context;
import android.util.Log;
import android.widget.SeekBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LightConfiguration {

    private String userKey;
    private String hostIP;
    private String portNr;
    private int numberOfLights;

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

    public LightConfiguration(String userKey, String hostIP, String portNr, Context context){
        this.userKey = userKey;
        this.hostIP = hostIP;
        this.portNr = portNr;
        requestQueue = Volley.newRequestQueue(context);
    }



    public void sendToHueBridge(int hue, int saturation, int brightness){
        CustomJsonArrayRequest request = new CustomJsonArrayRequest(
                Request.Method.PUT,
                buildUrl(),
                buildBody(hue, saturation, brightness),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.i("RESPONSE", "Response=" + response.toString());
                            numberOfLights = response.length();
                            Log.i("DEBUG", "Lights Available: " + response.length());
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

    private String buildUrl(){
        String url = "http://" + this.hostIP + ":" + this.portNr + buildApiString();
        return url;
    }

    private String buildApiString(){
        String apiString = "/api/" + this.userKey + "/lights";
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
