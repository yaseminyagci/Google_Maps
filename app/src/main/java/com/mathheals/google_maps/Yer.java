package com.mathheals.google_maps;

import java.io.Serializable;

/**
 * Created by user on 25.02.2018.
 */

class Yer implements Serializable{

    public String isim;
    public String lat,lng;
    public Yer(){}


    public Yer(String isim, String lat, String lng) {
        this.isim = isim;
        this.lat=lat;
        this.lng=lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getIsim() {

        return isim;

    }

    public void setIsim(String isim) {
        this.isim = isim;
    }
}
