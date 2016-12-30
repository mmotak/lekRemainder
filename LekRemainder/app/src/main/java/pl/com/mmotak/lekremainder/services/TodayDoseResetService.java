package pl.com.mmotak.lekremainder.services;

import android.app.IntentService;
import android.content.Intent;

import org.joda.time.DateTime;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.alarms.TodayDoseResetAlarmManager;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.data.ISharedDateProvider;
import pl.com.mmotak.lekremainder.lekapp.LekRemainderApplication;
import pl.com.mmotak.lekremainder.settings.SavedSettings;


public class TodayDoseResetService extends IntentService {

    @Inject
    IDataProvider dataProvider;
    @Inject
    ISharedDateProvider sharedDateProvider;

    public TodayDoseResetService() {
        super(TodayDoseResetService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        init();

        dataProvider.removeAllTodayDoses();

        DateTime dateTime = SavedSettings.getNextRestartDateTime();
        sharedDateProvider.saveNextResetDateTime(dateTime.getMillis());

        TodayDoseResetAlarmManager.setNextAlarmTodayDoseResetService(getApplicationContext(),dateTime);
    }

    private void init() {
        if (dataProvider == null || sharedDateProvider == null) {
            ((LekRemainderApplication) getApplication())
                    .getDiComponent()
                    .inject(this);
        }
    }
}
