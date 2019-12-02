package com.example.evitar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MenuActivity extends AppCompatActivity implements NotificationsFragment.OnFragmentInteractionListener{

    SpaceNavigationView navigationView;
    Fragment fragment;
    TextView toolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Toolbar myToolbar= (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        toolbarText=(TextView) findViewById(R.id.textView8);



        fragment=new HomeFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        navigationView=(SpaceNavigationView) findViewById(R.id.space);

        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.addSpaceItem(new SpaceItem("Home", R.drawable.ic_home_white_24dp));
        navigationView.addSpaceItem(new SpaceItem("Dashboard", R.drawable.ic_dashboard_white_24dp));
        navigationView.addSpaceItem(new SpaceItem("Archive", R.drawable.ic_archive_white_24dp));
        navigationView.addSpaceItem(new SpaceItem("Settings", R.drawable.ic_settings_white_24dp));



        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                navigationView.setCentreButtonSelectable(true);
                toolbarText.setText("Notifications");
                fragment=new NotificationsFragment();
                if (fragment!=null){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                }
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                toolbarText.setText(itemName);

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
                setFragment();
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Toast.makeText(MenuActivity.this, "You are already here!", Toast.LENGTH_SHORT).show();
            }
        });





    }

    public void setFragment(){
        if (fragment!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
    @Override
    public void onButtonclick(Notification notif) {
        openDialog();
    }
    public void openDialog() {
        NotificationDialog notifDialog = new NotificationDialog();
        notifDialog.show(getSupportFragmentManager(), "notif dialog");
    }

}
