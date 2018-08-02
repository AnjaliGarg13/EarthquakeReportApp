package com.example.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

import java.util.*;

public class EventAdapter extends ArrayAdapter<Event>{
    public EventAdapter(@NonNull Context context, ArrayList<Event> earthquake) {
        super(context, 0,earthquake);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event earthquake = getItem(position);
        View listItemView = convertView;

        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,false);
    }
//Magnitude
    TextView magTV = (TextView) listItemView.findViewById(R.id.version_mag);
        GradientDrawable circle = (GradientDrawable) magTV.getBackground();
        int magColor = getMagnitudeColor(earthquake.getMag());
        Log.i("Magcolor",magColor+"");
        circle.setColor(magColor);
        magTV.setText(earthquake.getMag() + "");


        //place
        TextView placeTV = (TextView) listItemView.findViewById(R.id.version_place);
        placeTV.setText(earthquake.getLocation());

        //country
        TextView countryTV = (TextView) listItemView.findViewById(R.id.version_country);
        countryTV.setText(earthquake.getCountry());

        //Date
        TextView date = (TextView) listItemView.findViewById(R.id.version_date);
        date.setText(earthquake.getdate());

return listItemView;
    }

    private int getMagnitudeColor(String mag) {
        float f = Float.parseFloat(mag);
        int x  = (int)f;
        int magnitude1Color = 0;
        switch(x){
            case 0: magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude1);;
                break;
            case 1: magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case 2: magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case 3: magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4: magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5: magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6: magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7: magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8: magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9: magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            default:magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
        }

        return magnitude1Color;
    }
}
