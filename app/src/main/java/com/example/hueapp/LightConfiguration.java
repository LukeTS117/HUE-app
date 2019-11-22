package com.example.hueapp;

import android.util.Log;
import android.widget.SeekBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LightConfiguration {

    private String userKey;
    private String lightNr;
    private String hostIP;
    private String portNr;
    RequestQueue requestQueue;

    public LightConfiguration(String userKey, String lightNr, String hostIP, String portNr){
        this.userKey = userKey;
        this.lightNr = lightNr;
        this.hostIP = hostIP;
        this.portNr = portNr;
    }

    private void sendToHueBridge(int hue, int saturation, int brightness){
        CustomJsonArrayRequest request = new CustomJsonArrayRequest(
                Request.Method.PUT,
                buildUrl(),
                buildBody(hue, saturation, brightness),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.i("RESPONSE", "Response=" + response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
        String apiString = "/api/" + this.userKey + "/lights/" + this.lightNr + "/state";
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
