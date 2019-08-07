package syssatelite.navegandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.configurar:
                Intent config = new Intent(MainActivity.this, Configurar.class);
                startActivity(config);
                return true;
            case R.id.localizar:
                Intent mapa = new Intent(MainActivity.this, mapa.class);
                startActivity(mapa);
                return true;
            case R.id.sat:
                Intent gnss = new Intent(MainActivity.this, TelaGNSS.class);
                startActivity(gnss);
                return true;
            case R.id.ceu:
                Intent ceu = new Intent(MainActivity.this, SatelitedeCima.class);
                startActivity(ceu);
                return true;
            case R.id.sobre:
                Intent sobre = new Intent(MainActivity.this, telaSobre.class);
                startActivity(sobre);
                return true;
            default:
                return true;

        }
    }
}
