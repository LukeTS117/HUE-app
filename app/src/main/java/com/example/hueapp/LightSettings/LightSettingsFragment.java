package com.example.hueapp.LightSettings;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.hueapp.LightConfiguration;
import com.example.hueapp.R;
import com.example.hueapp.SharedPref;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LightSettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LightSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LightSettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView previewColor;
    private SeekBar hueSeekbar;
    private SeekBar satSeekbar;
    private SeekBar briSeekbar;
    public LightConfiguration lightConfiguration;
    private SharedPref sharedPref;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LightSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LightSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LightSettingsFragment newInstance(String param1, String param2) {
        LightSettingsFragment fragment = new LightSettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sharedPref = new SharedPref(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_light_settings, container, false);

        previewColor = view.findViewById(R.id.colorPreview);
        hueSeekbar = view.findViewById(R.id.seekBar_hue);
        briSeekbar = view.findViewById(R.id.seekBar_brightness);
        satSeekbar = view.findViewById(R.id.seekBar_saturation);

        hueSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                onSeekBarValueChanged();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        briSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                onSeekBarValueChanged();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        satSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                onSeekBarValueChanged();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        if(lightConfiguration != null){
            lightConfiguration.setHostIP(sharedPref.getIP());
            lightConfiguration.setPortNr(sharedPref.getPort());
            lightConfiguration.setUserKey(sharedPref.getUserKey());
        } else {
            lightConfiguration = new LightConfiguration(sharedPref.getUserKey(), sharedPref.getIP(), sharedPref.getPort(), this.getContext());
        }

        return view;
    }

    public void onSeekBarValueChanged(){

        int hue = hueSeekbar.getProgress();
        int sat = satSeekbar.getProgress();
        int bri = briSeekbar.getProgress();

        int newColor = Color.HSVToColor(new float[]{(float)hue/((float)hueSeekbar.getMax()/360f), (float)sat/(float)satSeekbar.getMax(), (float)bri/(float)briSeekbar.getMax()});
        previewColor.setColorFilter(newColor);
        mListener.OnLightSettingsFragment_ColorChanged(hue, sat, bri);

        lightConfiguration.sendToHueBridge(hueSeekbar.getProgress(), satSeekbar.getProgress(), briSeekbar.getProgress());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
           // mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnLightSettingsFragment_ColorChanged(int hue, int sat, int bri);
    }
}
