package com.example.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {

    public static ArrayList<Event> fetchEarthquake(String requestUrl){
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    String jsonResponse = "";
    try {
        URL url = new URL(requestUrl);
         jsonResponse = makeHttpRequest(url);
    } catch (MalformedURLException e) {
        Log.i("error","creating url");
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

    ArrayList<Event> earthquake = extractFeatureFromJson(jsonResponse);
    return earthquake;
}

    private static ArrayList<Event> extractFeatureFromJson(String jsonResponse) {
        ArrayList<Event> list = new ArrayList<>();
        if(jsonResponse==null||jsonResponse.isEmpty())
            return null;
        try {

            JSONObject root = new JSONObject(jsonResponse);
            JSONArray features = root.getJSONArray("features");
            Log.i("featuresLength",features.length()+"");
            for(int i = 0;i<features.length(); ++i){
                JSONObject properties = features.getJSONObject(i).getJSONObject("properties");

                //Magnitude
                double m = properties.getDouble("mag");
                DecimalFormat decimalFormat = new DecimalFormat("0.0");
                String mag = decimalFormat.format(m);


                //Location
                String location = properties.getString("place");
                int index = 0;
                for(int j = 0;j<location.length();++j){
                    if(location.charAt(j)==',')
                    {
                        index = j;
                        break;
                    }
                }
                String str1 = "",str2 = "";
                if(index == 0){
                    str1 = "Near the ";
                    str2 = location;
                }else{
                    str1 = location.substring(0,index);
                    str2 = location.substring(index+1,location.length());
                }


                //Date and time
                long time = properties.getLong("time");
                Date dateObject = new Date(time);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String date = dateFormat.format(dateObject);
                index = 0;
                String str = "";
                for(int j = 0; j<date.length();++j){
                    if(date.charAt(j) == ' '){
                        index = j; break;
                    }
                }
                str = date.substring(index+1,date.length());
                date = date.substring(0,index);
                String date1 = date +"\n"+str;
                //Tsunami
                int tsunami = properties.getInt("tsunami");

                //Felt
                String felt = properties.getString("felt");

                //creating new object
                list.add(new Event(mag,date1,str1,str2,tsunami,felt));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
return list;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if(url==null)
            return "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            Log.i("error","problem in request method");
            e.printStackTrace();
        }finally{
        if(urlConnection != null)
            urlConnection.disconnect();
        if(inputStream != null)
            inputStream.close();
        }
return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {
    StringBuilder output = new StringBuilder();
    if(inputStream == null)
        return output.toString();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        try {
            String line = reader.readLine();
            while(line!=null){
                output.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("error","BufferedReader reader.getLine()");
        }
    return output.toString();
    }


}
