
package syssatelite.navegandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class List extends Activity {

    ArrayList<String> myArraayList =new ArrayList<>();
    ScrollView myScroll;
    DatabaseReference myFirebase;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref=FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);


        ScrollView myListView = findViewById(R.id.lista);
      //  ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this,R.id.lista);
        getsLocation();
    }




    private void getsLocation(){

        ref.child("location").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        if (dataSnapshot.getValue() != null)
                            getAllLocations((Map<String,Object>) dataSnapshot.getValue());

                        System.out.println("Chave:"+ dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
    }
    private void getAllLocations(Map<String,Object> locations) {

        for (Map.Entry<String, Object> entry : locations.entrySet()){

            Date newDate = new Date(Long.valueOf(entry.getKey()));
            Map singleLocation = (Map) entry.getValue();
            LatLng latLng = new LatLng((Double) singleLocation.get("latitude"), (Double)singleLocation.get("longitude"));
            //addGreenMarker(newDate, latLng);

        }


    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
