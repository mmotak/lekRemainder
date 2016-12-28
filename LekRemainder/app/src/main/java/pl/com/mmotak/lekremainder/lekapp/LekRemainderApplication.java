package pl.com.mmotak.lekremainder.lekapp;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.alarms.TodayDoseResetAlarmManager;
import pl.com.mmotak.lekremainder.di.DaggerDiComponent;
import pl.com.mmotak.lekremainder.di.DiApplicationModule;
import pl.com.mmotak.lekremainder.di.DiComponent;
import pl.com.mmotak.lekremainder.di.DiStorageModule;

/**
 * Created by mmotak on 25.11.2016.
 */

public class LekRemainderApplication extends Application {

    private DiComponent diComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        init();

        DateTime time = DateTime.now().plusMinutes(1);
        TodayDoseResetAlarmManager.setNextAlarm(this, time);
    }

    private void init() {
        JodaTimeAndroid.init(this);

        diComponent = DaggerDiComponent.builder()
                .diApplicationModule(new DiApplicationModule(this))
                .diStorageModule(new DiStorageModule())
                .build();
    }

    public DiComponent getDiComponent() {
        return diComponent;
    }
}
