package syssatelite.navegandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class telaConfigurar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void onRadioButtonClikedOrientacao(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.nenhuma:
                if (checked)
                    Log.d("myTag", "Nothing");
                    break;
            case R.id.norht:
                if (checked)
                    Log.d("myTag", "North");
                    break;
            case R.id.course:
                if (checked)
                    Log.d("myTag", "Course");
                    break;
        }
    }
}
