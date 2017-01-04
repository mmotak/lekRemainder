package pl.com.mmotak.lekremainder.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.joda.time.DateTime;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.alarms.TodayDoseResetAlarmManager;
import pl.com.mmotak.lekremainder.broadcasts.LekRemainderMainReceiver;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.data.ISharedDateProvider;
import pl.com.mmotak.lekremainder.lekapp.LekRemainderApplication;


public class TodayDoseResetService extends IntentService {

    @Inject
    IDataProvider dataProvider;
    @Inject
    ISharedDateProvider sharedDateProvider;

    public TodayDoseResetService() {
        super(TodayDoseResetService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("TodayDoseResetService", "onHandleIntent " + DateTime.now());

        dataProvider.removeAllTodayDoses();

        DateTime dateTime = sharedDateProvider.getTomorrowRestartDateTime();
        sharedDateProvider.saveNextResetDateTime(dateTime.getMillis());

        TodayDoseResetAlarmManager.setNextAlarmTodayDoseResetService(getApplicationContext(), dateTime);
        TodayDoseResetAlarmManager.setNextAlarmNextDoseAlarmService(getApplicationContext(), DateTime.now().plusMinutes(1));

        LekRemainderMainReceiver.completeWakefulIntent(intent);
    }

    private void init() {
        ((LekRemainderApplication) getApplication())
                .getDiComponent()
                .inject(this);
    }
}
