package syssatelite.navegandroid;

import android.Manifest;
import android.app.Service;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapa extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;// O Gerente de localização
    private LocationProvider locProvider; // Provedor de localização
    private final int REQUEST_LOCATION = 2;
    private Location userLocation;
    private LatLng user;



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

//        // recupera (ou cria) uma instância do arquivo de preferencia do Android,
//        // pelo seu nome/chave
//        SharedPreferences pref = getSharedPreferences("configuracoes", MODE_PRIVATE);
//        // recupera a informação
//        String grau = pref.getString("grau", null);
//        String unidade = pref.getString("unidade", null);
//        String orientacao = pref.getString("orientacao", null);
//        String tipo = pref.getString("tipo", null);
//        String ligado = pref.getString("ligado", null);
//
//
//
//
//        if(ligado!= null) {
//            if (ligado.equals("Sim")) {
//                mMap.setTrafficEnabled(true);
//            } else {
//                mMap.setTrafficEnabled(false);
//            }
//        }

        //NORTH
        if (userLocation != null) {
            System.out.println("LAtitude" + userLocation.getLatitude() + "\t");
            System.out.println("Longitude" + userLocation.getLongitude() + "\t");
            user = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
        } else {
            System.out.println("SOU null");
            user = new LatLng(-12.9531, -38.4589);
        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(user)  //CENTRO DO MAPA
                .zoom(17)
                .bearing(90)     //ORIENTAÇÃO DA CÂMERA
                .build();       //UTILIZA O BUILD PARA CRIAR A CâMERA
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions().position(user).title("Sua localização!"));


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
            System.out.println("ativaGPS");
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
        System.out.println("onLocationChanged");
        userLocation = location;
        if (mMap != null) {
            System.out.println("onLocationChanged não é null");
            mMap.clear();
            user = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(user)  //CENTRO DO MAPA
                    .zoom(17)
                    .bearing(90)     //ORIENTAÇÃO DA CÂMERA
                    .build();       //UTILIZA O BUILD PARA CRIAR A CâMERA
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            mMap.addMarker(new MarkerOptions().position(user).title("Sua localização!"));
            //mMap.addMarker(new MarkerOptions().position(user).title("Sua localização!"));
            //mMap.addPolyline()
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

}
