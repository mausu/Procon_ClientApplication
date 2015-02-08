package net.mausukun.procon_clientapplication;

/**
 * Created by mausu on 15/02/08.
 */
class UserData {
    private int id;
    private String name;
    private GeoLocation location;

    public UserData(int id, String name, GeoLocation location){
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public UserData(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public GeoLocation getGeoLocation(){
        return this.location;
    }
}

class GeoLocation{
    private double latitude;
    private double longitude;

    public GeoLocation(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    @Override
    public String toString(){
        return "lat: " + this.latitude + " long: " + this.longitude;
    }
}