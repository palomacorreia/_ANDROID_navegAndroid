package syssatelite.navegandroid;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MapsLog extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    public GoogleMap mMap;
    public DatabaseReference mDatabase;
    private LocationManager locationManager;



    //Dados da lista de pontos e da polilinha
    private ArrayList<LatLng> listaPontos;
    Polyline linha;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Carregando Bottom Navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_map:
                        Intent a = new Intent(MapsLog.this, MapsLog.class);
                        startActivity(a);
                        break;

                    case R.id.navigation_list:
                        Intent b = new Intent(MapsLog.this,HistoricoLog.class);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });

        //checando permissões
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplication(), "Sem permissão!", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MapsLog.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        //iniciando serviço de localização
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

        //iniciando mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //banco de dados
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Recupera pontos do banco de dados
        listaPontos = new ArrayList<LatLng>();
        recuperaListaPontosDB();


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //checando permissões
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplication(), "Sem permissão!", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MapsLog.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        String provider = LocationManager.NETWORK_PROVIDER;
        Location location = locationManager.getLastKnownLocation(provider);
    }

    @Override
    public void onLocationChanged(Location location) {


    }
    private void recuperaListaPontosDB(){
        mDatabase.child("location").addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            LatLng latlng = new LatLng((Double) ds.child("latitude").getValue(), (Double)ds.child("longitude").getValue());
                            listaPontos.add(latlng);
                        }
                        desenharLinha(listaPontos);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }
    private void desenharLinha(ArrayList<LatLng> lista){
        //apaga todos os marcadores e polilinhas
                mMap.clear();
        //inicializa a variável de ponto
        LatLng ponto = new LatLng(0,0);

        //define configurações da polilinha
        PolylineOptions options = new PolylineOptions().width(6).color(Color.RED).geodesic(true);

        //Até o final da lista, vá adicionando os pontos a polilinha
        for (int i = 0; i < lista.size(); i++) {
            ponto = lista.get(i);
            options.add(ponto);
        }
        linha = mMap.addPolyline(options); //add Polyline

        //Marca última posição do log
        MarkerOptions marcarMeuLocal = new MarkerOptions().position(ponto).title("Minha última posição");
        mMap.addMarker(marcarMeuLocal);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(ponto)      // Sets the center of the map to location user
                .zoom(21)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //Toast.makeText(getApplication(), "Desenhou!", Toast.LENGTH_LONG).show();
    }



    @Override
    public void onResume() {
        super.onResume();
        if(mMap != null){ //prevent crashing if the map doesn't exist yet (eg. on starting activity)
            mMap.clear();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getApplication(), "GPS desligado!", Toast.LENGTH_LONG).show();
      //finish();
    }


}
