package syssatelite.navegandroid;
import syssatelite.navegandroid.Constants;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;



public class Fragment_mapa extends Fragment implements OnMapReadyCallback {


    SQLiteDatabase db;
    MapView mapView;
    GoogleMap map;
    boolean currentLocationInitialized = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get database
        db = getActivity().openOrCreateDatabase(Constants.DB, Context.MODE_PRIVATE, null);


        // Setup location broadcast receiver
        IntentFilter locationFilter = new IntentFilter(Constants.LOCATION_BROADCAST);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(locationReceiver, locationFilter);

        // Setup data receiver
        IntentFilter dataFilter = new IntentFilter(Constants.DATA_BROADCAST);


        // Setup root view
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        // Setup map view
        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(this);

        return rootView;
    }

    public void onMapReady(GoogleMap googleMap) {

        // Provide map to fragment
        map = googleMap;

        // Show a 'my location' button
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }


    }


    private Bitmap getBitmap(int drawableRes) {
        Drawable drawable = getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    // Setup Location Receiver
    private BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Zoom to current location initially
            if (!currentLocationInitialized && map != null) {
                Double latitude = intent.getDoubleExtra("latitude", 0);
                Double longitude = intent.getDoubleExtra("longitude", 0);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 17);
                map.animateCamera(cameraUpdate);
                currentLocationInitialized = true;
            }
        }
    };
}
