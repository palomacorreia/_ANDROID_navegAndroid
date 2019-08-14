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
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class TelaGNSS extends AppCompatActivity implements LocationListener, GpsStatus.Listener {

    private String lat;
    private String lon;
    private String alt;
    private String gnsstext=">";
    private String SNRtext=">";
    private String ELEVtext=">";
    private String AZIMtext=">";
    private TextView latitudePosition;
    private TextView longitudePosition;
    private TextView altitudePosition;
    private TextView GNSS;
    private TextView SNR;
    private TextView  ELEV;
    private TextView AZIM;
    private LocationManager locationManager;// O Gerente de localização
    private LocationProvider locProvider; // Provedor de localização

    private final int REQUEST_LOCATION = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_gnss);

        latitudePosition = (TextView) findViewById(R.id.latitude);
        longitudePosition = (TextView) findViewById(R.id.longitude);
        altitudePosition = (TextView) findViewById(R.id.altitude);
       // GNSS= (TextView) findViewById(R.id.gnss);
//        SNR=(TextView)findViewById(R.id.snr);
//        ELEV =(TextView)findViewById(R.id.elev);
//        AZIM=(TextView)findViewById(R.id.azimute);
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
        double altitude =location.getAltitude();

        lat = (Location.convert(latitude, Location.FORMAT_SECONDS));
        latitudePosition.setText(lat);
        lon = (Location.convert(longitude, Location.FORMAT_SECONDS));
        longitudePosition.setText(lon);
        alt=(Location.convert(longitude, Location.FORMAT_SECONDS));
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
        // O Location Provider foi desabilitado


    }

    public void onGpsStatusChanged(int event) {
        // Alguma mudança no sistema GPS
        try {
            GpsStatus gpsStatus=locationManager.getGpsStatus(null);
            // Informações do sistema estão encapsuladas no objeto gpsStatus
            if (gpsStatus!=null) {
                Iterable<GpsSatellite> sats = gpsStatus.getSatellites();
                for (GpsSatellite sat : sats) {
                    //processe as informações de cada satélite
                    gnsstext+= ""+sat.getPrn();
                    SNRtext+= ""+sat.getSnr();
                    ELEVtext+= ""+sat.getElevation();
                    AZIMtext+=" "+sat.getAzimuth();
                    //  sat.usedInFix();  //DIFERENCIA OS SATELITES
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
//        GNSS.setText(gnsstext);
//        SNR.setText(SNRtext);
//        ELEV.setText(ELEVtext);
//        AZIM.setText(AZIMtext);
    }

}