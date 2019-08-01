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
                //newGame();
                return true;
            case R.id.localizar:
                Intent intent2 = new Intent(MainActivity.this, mapa.class);
                startActivity(intent2);
                return true;
            case R.id.sobre:
                Intent intent3 = new Intent(MainActivity.this, telaSobre.class);
                startActivity(intent3);
                return true;
            default:
                return true;

        }
    }
}
