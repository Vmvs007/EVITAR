package com.example.evitar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MenuActivity extends AppCompatActivity {

    SpaceNavigationView navigationView;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        fragment=new HomeFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        navigationView=(SpaceNavigationView) findViewById(R.id.space);

        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.addSpaceItem(new SpaceItem("HOME", R.drawable.ic_home_white_24dp));
        navigationView.addSpaceItem(new SpaceItem("DASHBOARD", R.drawable.ic_dashboard_white_24dp));
        navigationView.addSpaceItem(new SpaceItem("ARCHIVE", R.drawable.ic_archive_white_24dp));
        navigationView.addSpaceItem(new SpaceItem("SETTINGS", R.drawable.ic_settings_white_24dp));

        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Toast.makeText(MenuActivity.this,"onCentreButtonClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Toast.makeText(MenuActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();

                switch (itemIndex){
                    case 0:
                        fragment=new HomeFragment();
                        break;
                    case 1:
                        fragment=new DashboardFragment();
                        break;
                    case 2:
                        fragment=new ArchiveFragment();
                        break;
                    case 3:
                        fragment=new SettingsFragment();
                        break;
                }
                if (fragment!=null){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Toast.makeText(MenuActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });




    }
}
