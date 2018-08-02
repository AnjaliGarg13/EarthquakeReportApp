package com.example.quakereport;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class information extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);


        int position = getIntent().getIntExtra("position",0);

        Event event = MainActivity.earthquake.get(position);

        //Title
        TextView title = (TextView) findViewById(R.id.heading);
        String date = event.getdate();
        int index = 0;
        String str = "";
        for(int i = 0; i<date.length();++i){
            if(date.charAt(i) == ' '){
                index = i; break;
            }
        }
        str = date.substring(index,date.length());
        date = date.substring(0,index);
        title.setText( event.getLocation()+" "+event.getCountry()+"\n"+date +"\n" + str);

        //Mag
        TextView mag = (TextView) findViewById(R.id.perceived_magnitude);
        mag.setText(event.getMag());

        //No. of people
        TextView people = (TextView) findViewById(R.id.number_of_people);
        if(!event.getFelt().equals(null))
        people.setText(event.getFelt() + " people felt the earthquake");

        //Tsunami Alert
        TextView tsunami = (TextView) findViewById(R.id.textView2);
        if(event.getTsunami()==0)
            tsunami.setText("NO");
        else
            tsunami.setText("YES");

    }


}
