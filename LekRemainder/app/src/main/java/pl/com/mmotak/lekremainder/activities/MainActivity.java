package pl.com.mmotak.lekremainder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.fragments.DrugsFragment;
import pl.com.mmotak.lekremainder.fragments.HistoryFragment;
import pl.com.mmotak.lekremainder.fragments.MyFragmentsLoader;
import pl.com.mmotak.lekremainder.fragments.SettingsFragment;
import pl.com.mmotak.lekremainder.fragments.TodayDrugsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private FrameLayout fragmentLayout;

    private MyFragmentsLoader fragmentsLoader = new MyFragmentsLoader();

    private static final int TIME_DELAY = 2000;
    private static long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        fragmentsLoader.addFragment(this, new TodayDrugsFragment());
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void setUpViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fragmentLayout = (FrameLayout) findViewById(R.id.fragment_layout);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (backPressedTime + TIME_DELAY > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                Toast.makeText(getBaseContext(), R.string.backspace_exit_message, Toast.LENGTH_SHORT).show();
            }
            backPressedTime = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today_drugs) {
            fragmentsLoader.replaceFragment(this, new TodayDrugsFragment());
        } else if (id == R.id.nav_drugs) {
            fragmentsLoader.replaceFragment(this, new DrugsFragment());
        } else if (id == R.id.nav_history) {
            fragmentsLoader.replaceFragment(this, new HistoryFragment());
        } else if (id == R.id.nav_settings) {
            fragmentsLoader.replaceFragment(this, new SettingsFragment());
            //startActivity(new Intent(this, SingleDrugActivity.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
