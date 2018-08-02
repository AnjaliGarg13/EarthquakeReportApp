package com.example.quakereport;

public class Event {
    private String mag;
    private String date;
    private String location;
    private String Country;
    private int tsunami;
    private String felt;

    public String getMag(){
        return mag;
    }
    public String getdate(){
        return date;
    }
    public String getLocation(){
        return location;
    }
    public String getCountry(){
        return Country;
    }
    public int getTsunami(){
        return tsunami;
    }
    public String getFelt(){
        return felt;
    }

    public Event(String m, String d, String l,String c,int ts, String f){
        mag = m;
        date = d;
        location = l;
        Country = c;
        tsunami = ts;
        felt = f;
    }

}
