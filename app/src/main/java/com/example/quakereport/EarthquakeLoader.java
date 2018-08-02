package com.example.quakereport;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Event>> {
    public static final String USGS = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=4&limit=50";
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)

    public EarthquakeLoader(Context context) {
        super(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onStartLoading() {
        Log.i("message","I am in OnStartLoading");
        forceLoad();
    }

    @Override
    public ArrayList<Event> loadInBackground() {
        Log.i("message","I am in LoadInBackgroung");
        MainActivity.earthquake = Utils.fetchEarthquake(USGS);

        return MainActivity.earthquake;
    }
}
