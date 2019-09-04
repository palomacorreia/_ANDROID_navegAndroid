package syssatelite.navegandroid;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;


public class DataTable {

    private Date data;
    private LatLng latLng;

    public DataTable(Date data, LatLng latLng) {
        this.data = data;
        this.latLng = latLng;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
