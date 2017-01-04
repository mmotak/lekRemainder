package pl.com.mmotak.lekremainder.services;

import android.content.Intent;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.alarms.TodayDoseResetAlarmManager;
import pl.com.mmotak.lekremainder.broadcasts.LekRemainderMainReceiver;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.data.ISharedDateProvider;
import pl.com.mmotak.lekremainder.lekapp.LekRemainderApplication;
import pl.com.mmotak.lekremainder.models.TodayDose;
import pl.com.mmotak.lekremainder.notification.INotificationProvider;
import rx.Subscriber;
import rx.Subscription;


public class NextDoseAlarmService extends BaseService {

    @Inject
    IDataProvider dataProvider;
    @Inject
    INotificationProvider notificationProvider;
    @Inject
    ISharedDateProvider sharedDateProvider;

    private Subscription subscribe;

    public NextDoseAlarmService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    protected void onHandleIntent(Intent intent, int startId) {
        Log.d("NextDoseAlarmService", "onHandleIntent "+DateTime.now());

        DateTime now = DateTime.now();

        subscribe = dataProvider.getObservableForNotTakenTodayDoseAfterDateTime(now)
                .subscribe(new Subscriber<List<TodayDose>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("NextDoseAlarmService", "onCompleted ");
                        unSubscribe();
                        stopSelf(startId);
                        LekRemainderMainReceiver.completeWakefulIntent(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("NextDoseAlarmService", "onError ");
                        e.printStackTrace();
                        unSubscribe();
                        stopSelf(startId);
                        LekRemainderMainReceiver.completeWakefulIntent(intent);
                    }

                    @Override
                    public void onNext(List<TodayDose> todayDoses) {
                        Log.d("NextDoseAlarmService", "onNext "+ todayDoses.size());
                        List<TodayDose> notifications = new ArrayList<TodayDose>();

                        DateTime minimum = null;
                        boolean playSound = false;

                        for (TodayDose td : todayDoses) {
                            if (td.getEstimatedDateTime().isBefore(now.plusMinutes(1))) {
                                notifications.add(td);
                                playSound = td.getEstimatedDateTime().isAfter(now.minusMinutes(1)) || playSound;
                            } else if (minimum == null) {
                                minimum = td.getEstimatedDateTime();
                            } else if (minimum.isAfter(td.getEstimatedDateTime())) {
                                minimum = td.getEstimatedDateTime();
                            }
                        }

                        Log.d("NextDoseAlarmService", "onNext "+ notifications.size());
                        notificationProvider.show(notifications, playSound);
                        if (minimum == null) {
                            DateTime resetTime = null;

                            DateTime now = DateTime.now();
                            DateTime todayRestartDateTime = sharedDateProvider.getTodayRestartDateTime();

                            if (now.isBefore(todayRestartDateTime)) {
                                if (notifications.isEmpty()) {
                                    resetTime = DateTime.now().plusMinutes(1);
                                } else {
                                    resetTime = todayRestartDateTime;
                                }
                            } else {
                                resetTime = sharedDateProvider.getTomorrowRestartDateTime();
                            }

                            TodayDoseResetAlarmManager.setNextAlarmTodayDoseResetService(getApplicationContext(), resetTime);
                            sharedDateProvider.saveNextResetDateTime(resetTime.getMillis());
                        } else {
                            TodayDoseResetAlarmManager.setNextAlarmNextDoseAlarmService(getApplicationContext(), minimum);
                        }
                        Log.d("NextDoseAlarmService", "onNext - end");
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    private void unSubscribe() {
        if (subscribe != null && subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
    }

    private void init() {
        ((LekRemainderApplication) getApplication())
                .getDiComponent()
                .inject(this);
    }
}
