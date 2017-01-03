package pl.com.mmotak.lekremainder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.fragments.DrugsFragment;
import pl.com.mmotak.lekremainder.fragments.HistoryFragment;
import pl.com.mmotak.lekremainder.fragments.IFragment;
import pl.com.mmotak.lekremainder.fragments.MyFragmentsLoader;
import pl.com.mmotak.lekremainder.fragments.SettingsFragment;
import pl.com.mmotak.lekremainder.fragments.TodayDrugsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawer;

    private MyFragmentsLoader fragmentsLoader = new MyFragmentsLoader(R.id.fragment_layout);

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

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        navigationView.getMenu().getItem(0).setChecked(true);
        fragmentsLoader.replaceFragment(this, new TodayDrugsFragment());
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        IFragment fragment = fragmentFactory(id);
        fragmentsLoader.replaceFragment(this, fragment);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private IFragment fragmentFactory(int id) {
        switch (id) {
            case R.id.nav_today_drugs:
                return new TodayDrugsFragment();
            case R.id.nav_drugs:
                return new DrugsFragment();
            case R.id.nav_history:
                return new HistoryFragment();
            case R.id.nav_settings:
                return new SettingsFragment();
            default:
                return null;
        }
    }
}
