package syssatelite.navegandroid;

import android.Manifest;
import android.app.Service;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Iterator;


public class TelaGNSS extends AppCompatActivity implements LocationListener, GpsStatus.Listener {

    private String lat;
    private String lon;
    private String alt;
    private TextView latitudePosition;
    private TextView longitudePosition;
    private TextView altitudePosition;
    private LocationManager locationManager;// O Gerente de localização
    private LocationProvider locProvider; // Provedor de localização
    private Satelite objSatelite = new Satelite();
    private CircleView myview;
    private final int REQUEST_LOCATION = 2;
    public  int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_gnss);

        latitudePosition = (TextView) findViewById(R.id.latitude);
        longitudePosition = (TextView) findViewById(R.id.longitude);
        altitudePosition = (TextView) findViewById(R.id.altitude);
        myview=(CircleView)findViewById(R.id.ciurculoviewid);
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Permissão do Usuário
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // A permissão foi dada
            ativaGPS();
        } else {
            // Solicite a permissão
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        desativaGPS();
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                // O usuário acabou de dar a permissão
                ativaGPS();
            } else {
                // O usuário não deu a permissão solicitada
                Toast.makeText(this, "Sua localização não será mostrada", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void ativaGPS() {
        try {
            locProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(locProvider.getName(), 30000, 1, this);
            locationManager.addGpsStatusListener(this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void desativaGPS() {
        try {
            locationManager.removeUpdates(this);
            locationManager.removeGpsStatusListener(this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        // Aqui a nova localização
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double altitude = location.getAltitude();

        lat = (Location.convert(latitude, Location.FORMAT_SECONDS));
        latitudePosition.setText(lat);
        lon = (Location.convert(longitude, Location.FORMAT_SECONDS));
        longitudePosition.setText(lon);
        alt = (Location.convert(longitude, Location.FORMAT_SECONDS));
        altitudePosition.setText(alt);


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // O Status do LocationProvider foi modificado

    }

    @Override
    public void onProviderEnabled(String provider) {
        // O Location Provider foi habilitado

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getApplication(), "GPS desligado!", Toast.LENGTH_LONG).show();
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onGpsStatusChanged(int event) {

        String strGpsStats = "";
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        GpsStatus gpsStatus = locationManager.getGpsStatus(null);
        if(gpsStatus != null) {
            Iterable<GpsSatellite>satellites = gpsStatus.getSatellites();
            Iterator<GpsSatellite>sat = satellites.iterator();
            while (sat.hasNext()) {
                GpsSatellite satellite = sat.next();
                strGpsStats+= (i++) + ": " + "Pseudo-random number for the satellite:  "+satellite.getPrn() + "," + "Satellite was used by the GPS calculation: " + satellite.usedInFix() + "," + "Signal to noise ratio for the satellite: "+satellite.getSnr() + "," + "Azimuth of the satellite in degrees: "+satellite.getAzimuth() + "," +"Elevation of the satellite in degrees: "+satellite.getElevation()+ "\n\n";
                objSatelite.setAZIM(satellite.getAzimuth());
                objSatelite.setTIPOSAT(satellite.usedInFix());
                objSatelite.setELEV(satellite.getElevation());
                objSatelite.setGNSS(satellite.getPrn());
                objSatelite.setSNR(satellite.getSnr());
                //arraySatelite.add(objSatelite);
//                if (objSatelite != null && arraySatelite.size()!= 0 && strGpsStats != "") {
//                    System.out.println("SATELITE1:" + sttemos aterGpsStats);


                System.out.println("SATELITE:" + strGpsStats);
                System.out.println("PRN gnss"+ satellite.getPrn());
            }

            myview.setSats(satellites);
            myview.postInvalidate();
        }


    }

}