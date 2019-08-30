package syssatelite.navegandroid;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;


public class historico extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigationView;
    private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        mTextMessage = findViewById(R.id.message);

        navigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(this);

    }

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_map:
//                    mTextMessage.setText(R.string.title_home);
//
//                    return true;
//                case R.id.navigation_list :
//                  // mTextMessage.setText(R.string.title_dashboard);
//                    getSupportActionBar().setTitle("Albuns");
//                    Fragment listFragment = Fragment_List.newInstance()
//                    openFragment(listFragment);
//                    return true;
//            }
//            return false;
//        }
//    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_map:
                mTextMessage.setText(R.string.title_home);

                return true;
            case R.id.navigation_list :
                 //mTextMessage.setText(R.string.title_dashboard);
                Fragment listFragment = Fragment_List.newInstance();
                openFragment(listFragment);
                return true;
        }
        return false;
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container1, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
