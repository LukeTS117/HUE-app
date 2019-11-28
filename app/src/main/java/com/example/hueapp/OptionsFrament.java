package com.example.hueapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OptionsFrament.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OptionsFrament#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OptionsFrament extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SharedPref sharedPref;

    private Button saveButton;
    private Switch darkmodeSwitch;

    private EditText UserKey;
    private EditText IP;
    private EditText Port;


    private boolean themeChanged = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OptionsFrament() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OptionsFrament.
     */
    // TODO: Rename and change types and number of parameters
    public static OptionsFrament newInstance(String param1, String param2) {
        OptionsFrament fragment = new OptionsFrament();
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

        View view = inflater.inflate(R.layout.fragment_options, container, false);
        saveButton = view.findViewById(R.id.button_save);
        darkmodeSwitch = view.findViewById(R.id.switch_darkmode);
        UserKey = view.findViewById(R.id.editText_userkey);
        IP = view.findViewById(R.id.editText_ip);
        Port = view.findViewById(R.id.editText_port);

        if(sharedPref.loadDarkmodeState() == true){
            darkmodeSwitch.setChecked(true);
        }

        UserKey.setText(sharedPref.getUserKey());
        IP.setText(sharedPref.getIP());
        Port.setText(sharedPref.getPort());


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSavePressed(null);
                sharedPref.setUserKey(UserKey.getText().toString());
                sharedPref.setIP(IP.getText().toString());
                sharedPref.setPort(Port.getText().toString());
            }
        });



        darkmodeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    sharedPref.setDarkmodeState(true);
                }else{
                    sharedPref.setDarkmodeState(false);
                }
                themeChanged = true;

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onSavePressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(themeChanged);
            themeChanged = false;
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
        void onFragmentInteraction(boolean themeChanged);
    }


}
