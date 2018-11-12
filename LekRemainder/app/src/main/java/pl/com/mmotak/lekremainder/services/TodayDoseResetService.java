package pl.com.mmotak.lekremainder.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;

import org.joda.time.DateTime;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.alarms.TodayDoseResetAlarmManager;
import pl.com.mmotak.lekremainder.broadcasts.LekRemainderMainReceiver;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.data.ISharedDateProvider;
import pl.com.mmotak.lekremainder.lekapp.LekRemainderApplication;
import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;
import pl.com.mmotak.lekremainder.notification.INotificationProvider;


public class TodayDoseResetService extends IntentService {
    private static final ILogger LOGGER = LekLogger.create(TodayDoseResetService.class.getSimpleName());

    @Inject
    IDataProvider dataProvider;
    @Inject
    ISharedDateProvider sharedDateProvider;
    @Inject
    INotificationProvider notificationProvider;

    public TodayDoseResetService() {
        super(TodayDoseResetService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForegroundMe();

        return super.onStartCommand(intent, flags, startId);
    }

    private void startForegroundMe() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // it is foreground service
            startForeground(notificationProvider.getResetId(), notificationProvider.getResetNotification());
        }
    }

    private void stopForegroundMe() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // it is foreground service
            stopForeground(true);
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LOGGER.d("onHandleIntent " + DateTime.now());

        dataProvider.removeAllTodayDoses();

        DateTime dateTime = sharedDateProvider.getTomorrowRestartDateTime();
        sharedDateProvider.saveNextResetDateTime(dateTime.getMillis());

        TodayDoseResetAlarmManager.setNextAlarmTodayDoseResetService(getApplicationContext(), dateTime);
        TodayDoseResetAlarmManager.setNextAlarmNextDoseAlarmService(getApplicationContext(), DateTime.now().plusMinutes(2));

        stopForegroundMe();
        //LekRemainderMainReceiver.completeWakefulIntent(intent);
    }

    private void init() {
        ((LekRemainderApplication) getApplication())
                .getDiComponent()
                .inject(this);
    }
}
