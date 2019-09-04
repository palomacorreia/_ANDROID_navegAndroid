package syssatelite.navegandroid;

import android.Manifest;
import android.app.Service;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class mapa extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;// O Gerente de localização
    private LocationProvider locProvider; // Provedor de localização
    private final int REQUEST_LOCATION = 2;
    private Location userLocation;
    private LatLng user;
    private  String grau, unidade, orientacao, tipo, ligado;
    private String strlatitude, strlongitude;
    private float velocidade;
    private TextView longitude,latitude, velocimetro;
    //Banco de Dados
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        longitude = (TextView)findViewById(R.id.longitude);
        latitude = (TextView)findViewById(R.id.latitude);
        velocimetro = (TextView)findViewById(R.id.velocidade);
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

            //UNEB
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
            //cria um obj LatLng com a latitude e longitude mudando de acordo a localizacao
            user = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());

            //salvar localização no banco de dados
            mDatabase.child("location").child(String.valueOf(new Date().getTime())).setValue(user);

            //velocidade do mapa
            if(unidade != null)
            {
                if (unidade.equals("km"))
                {
                    velocidade = userLocation.getSpeed();

                }else if(unidade.equals("mph"))
                {
                    velocidade = userLocation.getSpeed();
                }

                System.out.println("velocidade: "+ velocidade);
            }


            //Ver o grau de exibição e coloca na tela
            if(grau.equals( "Decimal")) {
                strlatitude = (userLocation.convert(userLocation.getLatitude(), userLocation.FORMAT_DEGREES));
                strlongitude = (Location.convert(userLocation.getLongitude(), Location.FORMAT_DEGREES));
            }
            else if(grau.equals("Minuto")) {
                strlatitude = (Location.convert(userLocation.getLatitude(), Location.FORMAT_MINUTES));
                strlongitude = (Location.convert(userLocation.getLongitude(), Location.FORMAT_MINUTES));
            }
            else if(grau.equals("Segundo")){
                strlatitude = (Location.convert(userLocation.getLatitude(), Location.FORMAT_SECONDS));
                strlongitude = (Location.convert(userLocation.getLongitude(), Location.FORMAT_SECONDS));
            }

            //Seta no mapa a latitude , longitude e velocidade
            TextView longitude = (TextView)findViewById(R.id.longitude);
            TextView latitude = (TextView)findViewById(R.id.latitude);
            TextView velocimetro = (TextView)findViewById(R.id.velocidade);
            longitude.setText("Latitude: " + strlatitude);
            latitude.setText("Longitude: " + strlongitude);
            velocimetro.setText("Velocidade: " + velocidade);

            //Orientações do mapa
            if(orientacao.equals("North")) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(user)  //CENTRO DO MAPA
                        .zoom(15)
                        .bearing(90)     //ORIENTAÇÃO DA CÂMERA
                        .build();       //UTILIZA O BUILD PARA CRIAR A CâMERA
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mMap.addMarker(new MarkerOptions().position(user).title("Sua localização!"));

            }else if (orientacao.equals("Course"))
            {
                float bearing = userLocation.getBearing();
                CameraPosition cp = new CameraPosition.Builder()
                        .target(user)
                        .zoom(15)
                        .bearing(bearing)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
                mMap.addMarker(new MarkerOptions().position(user).title("Sua localização!"));
            }
            else
            {
                CameraPosition cp = new CameraPosition.Builder()
                        .target(user)
                        .zoom(15)
                        .bearing(35.0f)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
                mMap.addMarker(new MarkerOptions().position(user).title("Sua localização!"));
            }
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
        //mMap.clear();
        BitmapDrawable bitmapdraw =(BitmapDrawable)getResources().getDrawable(R.drawable.question);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 300, 300, false);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(0.0, 0.0);
        mMap.addMarker(new MarkerOptions().position(sydney).title("GPS desligado!!").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //Seta no mapa a latitude , longitude e velocidade
        longitude.setText("GPS desligado");
        latitude.setText("GPS desligado");
        velocimetro.setText("GPS desligado");
    }


}