package pl.com.mmotak.lekremainder.services;

import android.app.IntentService;
import android.content.Intent;

import org.joda.time.DateTime;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.alarms.TodayDoseResetAlarmManager;
import pl.com.mmotak.lekremainder.data.DataBaseProvider;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.lekapp.LekRemainderApplication;


public class TodayDoseResetService extends IntentService {

    @Inject
    IDataProvider dataProvider;

    public TodayDoseResetService() {
        super(TodayDoseResetService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        init();

        dataProvider.removeAllTodayDoses();

        DateTime time = DateTime.now().plusDays(1).withTime(6, 0, 0, 0); // set tomorrow at 6 am
        TodayDoseResetAlarmManager.setNextAlarm(getApplicationContext(), time);
    }

    private void init() {
        if (dataProvider == null) {
            ((LekRemainderApplication) getApplication())
                    .getDiComponent()
                    .inject(this);
        }
    }
}
