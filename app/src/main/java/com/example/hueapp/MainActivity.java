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

import com.example.hueapp.LightSettings.LightSettingsFragment;

public class MainActivity extends AppCompatActivity implements OptionsFrament.OnFragmentInteractionListener, LightSettingsFragment.OnFragmentInteractionListener{

    private FrameLayout fragementContainer;
    private FrameLayout mainFragmentContainer;
    private Button button;
    private SharedPref sharedPref;
    private FragmentManager fragmentManager;
    private LightConfiguration lightConfiguration;

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
        mainFragmentContainer = (FrameLayout) findViewById(R.id.fragment_container2);
        button = (Button) findViewById(R.id.button);

        setLightSettingsFragment();

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
        transaction.add(R.id.fragment_container2, lsf, "LIGHT_SETTINGS_FRAGMENT").commit();
    }

    @Override
    public void onFragmentInteraction(boolean themeChanged) {


        if(themeChanged){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            //lightConfiguration = new LightConfiguration(this.sharedPref.UserKey,)
            startActivity(i);
            finish();
        }else{
            onBackPressed();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
