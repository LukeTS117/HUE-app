package com.example.hueapp.LightSettings;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hueapp.GlobalVariables;
import com.example.hueapp.Hue.Lamp;
import com.example.hueapp.Hue.LampState;
import com.example.hueapp.R;

import java.io.Serializable;
import java.util.ArrayList;

public class LightAdapter extends RecyclerView.Adapter<LightAdapter.LightViewHolder> implements Serializable {

    private ArrayList<Lamp> lamps;
    private LampSelectionFragment lsf;

    public LightAdapter(ArrayList<Lamp> lamps, LampSelectionFragment lsf) {
        this.lamps = lamps;
        this.lsf = lsf;
    }

    @NonNull
    @Override
    public LightViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.light_item, viewGroup, false);
        return new LightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LightViewHolder lightViewHolder, int i) {
        Lamp lamp = lamps.get(i);
        LampState state = lamp.getLampState();

        lightViewHolder.lampID.setText(lamp.getName());
        lightViewHolder.lamp.setBackground(new ColorDrawable(Color.HSVToColor(new float[]{(float)state.getHue()/((float)GlobalVariables.MAX_HUE/360f), (float)state.getSaturation()/(float)GlobalVariables.MAX_SATURATION, (float)state.getBrightness()/(float)GlobalVariables.MAX_BRIGHTNESS})));
        lightViewHolder.isSelected = false;

        if(lamp.isSelected()){
            lightViewHolder.isSelected = true;
            lightViewHolder.selected.setColorFilter(R.attr.colorSelected);
        }
    }




    @Override
    public int getItemCount() {
        return lamps.size();
    }

    public class LightViewHolder extends RecyclerView.ViewHolder{

        private ImageView selected;
        private ImageView lamp;
        private TextView lampID;
        private boolean isSelected;

        public LightViewHolder(@NonNull View itemView) {
            super(itemView);

            selected = (ImageView) itemView.findViewById(R.id.imageView_selected);
            lamp = (ImageView) itemView.findViewById(R.id.imageView_lamp);
            lampID = (TextView) itemView.findViewById(R.id.textView_LampID);



            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    selectLamp();
                }
            });


        }

        public void selectLamp(){

            isSelected = !isSelected;

            if(isSelected){
                selected.setColorFilter(R.attr.colorSelected);
                lsf.selectLamp(lamps.get(LightViewHolder.super.getAdapterPosition()));

            }
            if(!isSelected){
                selected.clearColorFilter();
                lsf.deselectLamp(lamps.get(LightViewHolder.super.getAdapterPosition()));
            }

        }
    }
}

