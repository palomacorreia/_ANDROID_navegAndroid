package syssatelite.navegandroid;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

public class mapa extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;// O Gerente de localização
    private LocationProvider locProvider; // Provedor de localização
    private final int REQUEST_LOCATION = 2;
    private Location userLocation;
    private LatLng user;
    private  String grau, unidade, orientacao, tipo, ligado;




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,  0, this); //Requests that I cast the fourth parameter to android.location.LocationListener, doing so results in a castexception.
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

        // recupera (ou cria) uma instância do arquivo de preferencia do Android,
        // pelo seu nome/chave
        SharedPreferences pref = getSharedPreferences("configuracoes", MODE_PRIVATE);
        // recupera a informação
        grau = pref.getString("grau", null);
        unidade = pref.getString("unidade", null);
        orientacao = pref.getString("orientacao", null);
        tipo = pref.getString("tipo", null);
        ligado = pref.getString("ligado", null);

        if(ligado!= null) {
            if (ligado.equals("Sim")) {
                mMap.setTrafficEnabled(true);
            } else {
                mMap.setTrafficEnabled(false);
            }
        }

        //NORTH
        if (userLocation != null) {
            user = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
        } else {
            user = new LatLng(-12.9531, -38.4589);
        }

        if(tipo != null)
        {
            if(tipo.equals("Imagem"))
            {
                mMap.setMapType(mMap.MAP_TYPE_SATELLITE); // Here is where you set the map type
            }else if(tipo.equals("Vetorial"))
            {
                mMap.setMapType(mMap.MAP_TYPE_NORMAL); // Here is where you set the map type
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
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

    public void ativaGPS() {
        try {
            locProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(locProvider.getName(), 1000, 1, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void desativaGPS() {
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        userLocation = location;

        if (mMap != null)
        {
            mMap.clear();
            user = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());

            TextView longitude = (TextView)findViewById(R.id.longitude);
            TextView latitude = (TextView)findViewById(R.id.latitude);
            longitude.setText("Longitude: " + userLocation.getLongitude());
            latitude.setText("Latitude: " + userLocation.getLatitude());
        }

        if(orientacao.equals("North")) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(user)  //CENTRO DO MAPA
                        .zoom(17)
                        .bearing(90)     //ORIENTAÇÃO DA CÂMERA
                        .build();       //UTILIZA O BUILD PARA CRIAR A CâMERA
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            mMap.addMarker(new MarkerOptions().position(user).title("Sua localização!"));

        }else if (orientacao.equals("Course"))
        {
            float bearing = userLocation.getBearing();
            CameraPosition cp = new CameraPosition.Builder()
                    .target(user)
                    .zoom(17)
                    .bearing(bearing)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
            mMap.addMarker(new MarkerOptions().position(user).title("Sua localização COURSE!"));
        }
        else
        {
            CameraPosition cp = new CameraPosition.Builder()
                    .target(user)
                    .zoom(17)
                    .bearing(35.0f)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
            mMap.addMarker(new MarkerOptions().position(user).title("Sua localização!"));
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

//    private void updateCameraBearing(GoogleMap googleMap, float bearing) {
////        if ( googleMap == null) return;
////        CameraPosition camPos = CameraPosition
////                .builder(googleMap.getCameraPosition() // current Camera
////                )
////                .bearing(bearing)
////                .build();
////        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
////        googleMap.addMarker(new MarkerOptions().position(user).title("Sua localização!"));
//        CameraPosition cp = new CameraPosition.Builder()
//                .target(user)
//                .zoom(17)
//                .bearing(bearing)
//                .build();
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
//        mMap.addMarker(new MarkerOptions().position(user).title("Sua localização!"));
//    }


}
