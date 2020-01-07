package com.example.evitar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.evitar.ArchiveFolder.ArchiveDayFragment;
import com.example.evitar.ArchiveFolder.ArchiveFragment;
import com.example.evitar.DashFolder.DashboardFragment;
import com.example.evitar.EpiFolder.Epi;
import com.example.evitar.EpiFolder.EpiDialog;
import com.example.evitar.EpiFolder.EpiFragment;
import com.example.evitar.HomeFolder.HomeFragment;
import com.example.evitar.LoginFolder.LoginActivity;
import com.example.evitar.MovimentosFolder.Movimento;
import com.example.evitar.MovimentosFolder.MovimentoDialog;
import com.example.evitar.MovimentosFolder.MovimentosFragment;
import com.example.evitar.TipoEpiFolder.TipoEpiDialog;
import com.example.evitar.TipoEpiFolder.TipoEpiFragment;
import com.example.evitar.TipoEpiFolder.TipoEpis;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MenuActivity extends AppCompatActivity implements MovimentosFragment.OnFragmentInteractionListener, EpiFragment.OnFragmentInteractionListener, DashboardFragment.OnFragmentInteractionListener,
        ArchiveDayFragment.OnFragmentInteractionListener, TipoEpiFragment.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener{

    private SpaceNavigationView navigationView;
    private Fragment fragment;
    private TextView toolbarText;

    SharedPreferences mUser;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        mUser = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = mUser.edit();

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

        navigationView.showIconOnly();



        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                navigationView.setCentreButtonSelectable(true);
                toolbarText.setText("Movements");
                fragment=new MovimentosFragment();
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
    public void onButtonclick(Movimento notif) {
        openDialog(notif);
    }
    public void openDialog(Movimento notif) {
        MovimentoDialog notifDialog = new MovimentoDialog(notif);
        notifDialog.show(getSupportFragmentManager(), "notif dialog");
    }

    @Override
    public void onButtonclick(Epi epi) {
        openDialog(epi);
    }
    public void openDialog(Epi epi) {
        EpiDialog epiDialog = new EpiDialog(epi);
        epiDialog.show(getSupportFragmentManager(), "epi dialog");
    }

    @Override
    public void onButtonclick(TipoEpis epi) {
        openDialog(epi);
    }
    public void openDialog(TipoEpis epi) {
        TipoEpiDialog epiDialog = new TipoEpiDialog(epi);
        epiDialog.show(getSupportFragmentManager(), "tipoepi dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_logout:
                mEditor.clear();
                mEditor.commit();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    public void onBackPressed() {

    }

}
