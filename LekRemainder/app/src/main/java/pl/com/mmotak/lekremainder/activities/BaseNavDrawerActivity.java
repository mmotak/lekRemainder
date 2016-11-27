package pl.com.mmotak.lekremainder.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import pl.com.mmotak.lekremainder.R;

/**
 * Created by mmotak on 27.11.2016.
 */

public class BaseNavDrawerActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initToolbar();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setUpToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    protected void setUpToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
