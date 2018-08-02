package com.example.quakereport;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
//import android.app.LoaderManager;
import android.support.v4.content.Loader;
//import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Event>> {
    public static ArrayList<Event> earthquake;
    public static final String USGS = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=50";
    ListView listView;

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.layout.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //LoaderManager loaderManager = getLoaderManager();
        listView = (ListView) findViewById(R.id.listView);
        listView.setEmptyView((TextView)findViewById(R.id.textView4));
        boolean b = isNetworkAvailable(this);
        if(!b){
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);
            TextView textView4 = (TextView)findViewById(R.id.textView4);
            textView4.setText("NO INTERNET CONNECTION");
            TextView textView3 = (TextView) findViewById(R.id.textView3);
            textView3.setVisibility(View.INVISIBLE);
            Button button = (Button) findViewById(R.id.button);
            button.setVisibility(View.VISIBLE);
        }else{
            Button button = (Button) findViewById(R.id.button);
            button.setVisibility(View.INVISIBLE);
            getSupportLoaderManager().initLoader(0, null, this);//.forceLoad();
        }


    }

    public void Retry(View view){
        listView = (ListView) findViewById(R.id.listView);
        listView.setEmptyView((TextView)findViewById(R.id.textView4));
        boolean b = isNetworkAvailable(this);
        if(!b){
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);
            TextView textView4 = (TextView)findViewById(R.id.textView4);
            textView4.setText("NO INTERNET CONNECTION");
            TextView textView3 = (TextView) findViewById(R.id.textView3);
            textView3.setVisibility(View.INVISIBLE);
            Button button = (Button) findViewById(R.id.button);
            button.setVisibility(View.VISIBLE);
        }else{
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            Button button = (Button) findViewById(R.id.button);
            button.setVisibility(View.INVISIBLE);
            TextView textView3 = (TextView) findViewById(R.id.textView3);
            textView3.setVisibility(View.VISIBLE);
            TextView textView4 = (TextView) findViewById(R.id.textView4);
            textView4.setVisibility(View.INVISIBLE);
            getSupportLoaderManager().initLoader(0, null, this);
        }

    }

    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    @NonNull

   @Override
    public Loader<ArrayList<Event>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.i("message","I am in OnCreateLoader");
        return new EarthquakeLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Event>> loader, ArrayList<Event> events) {
        TextView textView4 = (TextView)findViewById(R.id.textView4);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        textView4.setText("No Earthquake Available");
        Log.i("message","I am in OnLoadfinished");

        if(events==null){
            Log.i("error","onPostExecute()");
            textView4.setVisibility(View.VISIBLE);
            TextView textView3 = (TextView) findViewById(R.id.textView3);
            textView3.setVisibility(View.INVISIBLE);
            return;
        }
        EventAdapter adapter = new EventAdapter(getApplicationContext(), events);
        if(adapter==null){
            Log.i("error","null adaper");
        }

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),information.class);
                intent.putExtra("position",position);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Event>> loader) {
        Log.i("message","I am in OnLoaderReset");
        getSupportLoaderManager().restartLoader(0, null,  this);
    }






//    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
//    public class EarthquakeTask extends AsyncTask<URL,Void,ArrayList<Event>> {
//
//        @Override
//        protected ArrayList<Event> doInBackground(URL... urls) {
//
////            if(urls.length==0){
////                Log.i("error","empty url in mainActivity.java");
////                return null;
////            }
//
//           earthquake = Utils.fetchEarthquake(USGS);
//
//            return earthquake;
//        }
//
//        @Override
//        protected void onPostExecute(final ArrayList<Event> events) {
//            if(events==null){
//                Log.i("error","onPostExecute()");
//            }
//          EventAdapter adapter = new EventAdapter(getApplicationContext(), events);
//            if(adapter==null){
//                Log.i("error","null adaper");
//            }
//            ListView listView = (ListView) findViewById(R.id.listView);
//            listView.setAdapter(adapter);
//
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent = new Intent(getApplicationContext(),information.class);
//                    intent.putExtra("position",position);
//                    startActivity(intent);
//
//                }
//            });
//
//        }
//    }
}
