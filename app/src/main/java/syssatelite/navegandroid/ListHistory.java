
package syssatelite.navegandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListHistory extends Activity {

    private ArrayList<LatLng> listaPontos;
    public DatabaseReference mDatabase;
    TextView txtData,txtLat,txtLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//banco de dados
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Recupera pontos do banco de dados
        listaPontos = new ArrayList<LatLng>();
        setContentView(R.layout.fragment_list);


        carregarTable();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }


    public void carregarTable(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("location");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot lc : dataSnapshot.getChildren()){
                    //LocationData loc = dataSnapshot.getValue(LocationData.class);

                    Date key = new Date(Long.valueOf(lc.getKey()));
                    LatLng latlng = new LatLng((Double) lc.child("latitude").getValue(), (Double)lc.child("longitude").getValue());



                    listaPontos.add(latlng);
                    //inserir dados na tabela
                    txtData = (TextView)findViewById(R.id.data);
                    SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
                    txtData.setText(data.format(key));

                    txtLat =findViewById(R.id.latitude);
                    txtLat.setText(lc.child("latitude").toString());

                    txtLon = findViewById(R.id.longitude);
                  txtLon.setText(lc.child("longitude").toString());
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }




}



