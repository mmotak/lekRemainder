package pl.com.mmotak.lekremainder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import pl.com.mmotak.lekremainder.R;

public class SplashActivity extends AppCompatActivity {

    private static final int DELAY_IN_MS = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(this::lunchNextActivity, DELAY_IN_MS);
    }

    private void lunchNextActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }




}
