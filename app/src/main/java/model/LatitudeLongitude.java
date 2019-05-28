package model;

public class LatitudeLongitude {
    private double lat;
    private double lon;
    private String Marker;

    public LatitudeLongitude(double lat, double lon, String marker) {
        this.lat = lat;
        this.lon = lon;
        Marker = marker;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getMarker() {
        return Marker;
    }

    public void setMarker(String marker) {
        Marker = marker;
    }
}
