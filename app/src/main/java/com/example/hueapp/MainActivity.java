package com.example.hueapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.hueapp.Hue.Lamp;
import com.example.hueapp.Hue.LampState;
import com.example.hueapp.LightSettings.LampSelectionFragment;
import com.example.hueapp.LightSettings.LightSettingsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements OptionsFrament.OnFragmentInteractionListener,
        LightSettingsFragment.OnFragmentInteractionListener,
        LampSelectionFragment.OnFragmentInteractionListener,
        LampListner{

    private FrameLayout fragementContainer;
    private FrameLayout mainFragmentContainer;
    private Button button;
    private SharedPref sharedPref;
    private FragmentManager fragmentManager;
    private ArrayList<Lamp> lamps;
    private ArrayList<Lamp> selectedLamps;
    private LightConfiguration lightConfiguration;
    private LampSelectionFragment lampSelectionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = new SharedPref(this);
        if(sharedPref.loadDarkmodeState()){
            setTheme(R.style.DarkTheme);
        }else{
            setTheme(R.style.LightTheme);
        }

        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        fragementContainer = (FrameLayout) findViewById(R.id.fragment_container);
        mainFragmentContainer = (FrameLayout) findViewById(R.id.fragment_container_bottom);
        button = (Button) findViewById(R.id.button);
        selectedLamps = new ArrayList<>();

        //ToDo: implement huebridge

        lamps = new ArrayList<>();

        setLightSettingsFragment();
        setLampSelectionFragment();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });
    }

    private void openSettings(){
        OptionsFrament options = OptionsFrament.newInstance(null,null);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_container, options, "OPTIONS_FRAGMENT").commit();
    }

    private void setLightSettingsFragment(){
        LightSettingsFragment lsf = LightSettingsFragment.newInstance(null,null);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_container_bottom, lsf, "LIGHT_SETTINGS_FRAGMENT").commit();
    }

    private void setLampSelectionFragment(){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.Light_Key), lamps);
        lampSelectionFragment = LampSelectionFragment.newInstance(null,null);
        lampSelectionFragment.setArguments(bundle);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_container_top, lampSelectionFragment, "LIGHT_SETTINGS_FRAGMENT").commit();
    }

    private void onDataAvailable(Lamp lamp){
            lamps.add(lamp);

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getString(R.string.Light_Key), lamps);

            lampSelectionFragment.setArguments(bundle);
            lampSelectionFragment.onDataSetChanged();
    }

    @Override
    public void onOptionSaveButtonPressed(boolean themeChanged) {

        if(lightConfiguration == null){
            lightConfiguration = new LightConfiguration(sharedPref.getUserKey(), sharedPref.getIP(), sharedPref.getPort(), getApplicationContext(), this);
            lightConfiguration.getLamps();
        }



        if(themeChanged){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }else{
            onBackPressed();
        }

    }

    @Override
    public void OnLightSettingsFragment_ColorChanged(int hue, int sat, int bri) {
        for(int i = 0; i<this.selectedLamps.size(); i++){
            this.lamps.get(this.lamps.indexOf(this.selectedLamps.get(i))).getLampState().setHue(hue);
            this.lamps.get(this.lamps.indexOf(this.selectedLamps.get(i))).getLampState().setSaturation(sat);
            this.lamps.get(this.lamps.indexOf(this.selectedLamps.get(i))).getLampState().setBrightness(bri);
            lightConfiguration.sendToHueBridge(hue, sat, bri, this.lamps.get(this.lamps.indexOf(this.selectedLamps.get(i))).getLampNumber() + "");
        }

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.Light_Key), lamps);

        lampSelectionFragment.setArguments(bundle);
        lampSelectionFragment.onDataSetChanged();
    }

    @Override
    public void onLampSelectionChanged(Bundle selectedLamps) {
        this.selectedLamps = selectedLamps.getParcelableArrayList(getString(R.string.Selected_Light_Key));
        for(int i = 0; i<this.lamps.size(); i++){
            this.lamps.get(i).setSelected(false);
        }

        for(int i = 0; i<this.selectedLamps.size(); i++){
           this.lamps.get(this.lamps.indexOf(this.selectedLamps.get(i))).setSelected(true);
        }
    }

    @Override
    public void onLampAvailable(Lamp lamp) {
        this.onDataAvailable(lamp);
    }
}
