package pl.com.mmotak.lekremainder.services;

import android.content.Intent;
import android.os.Build;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.alarms.TodayDoseResetAlarmManager;
import pl.com.mmotak.lekremainder.broadcasts.LekRemainderMainReceiver;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.data.ISharedDateProvider;
import pl.com.mmotak.lekremainder.lekapp.LekRemainderApplication;
import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;
import pl.com.mmotak.lekremainder.models.TodayDose;
import pl.com.mmotak.lekremainder.notification.INotificationProvider;
import rx.Subscriber;
import rx.Subscription;


public class NextDoseAlarmService extends BaseService {
    private static final ILogger LOGGER = LekLogger.create(NextDoseAlarmService.class.getSimpleName());

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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForegroundMe();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startForegroundMe() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // it is foreground service
            startForeground(notificationProvider.getNextDoseId(), notificationProvider.getNextDoseNotification());
        }
    }

    private void stopForegroundMe() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // it is foreground service
            stopForeground(true);
        }
    }

    protected void onHandleIntent(Intent intent, int startId) {
        LOGGER.d("onHandleIntent " + DateTime.now());

        DateTime now = DateTime.now();

        subscribe = dataProvider.getObservableForNotTakenTodayDoseAfterDateTime()
                .doOnSubscribe(() -> LOGGER.d("doOnSubscribe"))
                .subscribe(new Subscriber<List<TodayDose>>() {
                    @Override
                    public void onCompleted() {
                        LOGGER.d("onCompleted ");
                        endMe(startId, intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LOGGER.e("onError " + e.getMessage(), e);
                        endMe(startId, intent);
                    }

                    @Override
                    public void onNext(List<TodayDose> todayDoses) {
                        LOGGER.d("onNext " + todayDoses.size());
                        LOGGER.d("now " + now);
                        LOGGER.d("now_NEW " + DateTime.now());
                        List<TodayDose> notifications = new ArrayList<TodayDose>();

                        DateTime minimum = null;
                        boolean playSound = false;

                        int shiftDay = 0;
                        if (now.isBefore(sharedDateProvider.getTodayRestartDateTime())) {
                            // a little date time hack
                            // if after 00:00 and before reset time
                            // add -1 day
                            shiftDay = -1;
                        }

                        for (TodayDose td : todayDoses) {
                            if (td.getEstimatedDateTime().plusDays(shiftDay).isBefore(now.plusMinutes(1))) {
                                notifications.add(td);
                                playSound = td.getEstimatedDateTime().plusDays(shiftDay).isAfter(now.minusMinutes(1)) || playSound;
                            } else if (minimum == null) {
                                minimum = td.getEstimatedDateTime().plusDays(shiftDay);
                            } else if (minimum.isAfter(td.getEstimatedDateTime())) {
                                minimum = td.getEstimatedDateTime().plusDays(shiftDay);
                            }
                        }

                        LOGGER.d("onNext " + notifications.size());
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
                        LOGGER.d("onNext - end");
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

    private void endMe(int startId, Intent intent) {
        unSubscribe();
        stopForegroundMe();
        stopSelf(startId);
        //LekRemainderMainReceiver.completeWakefulIntent(intent);
    }

    private void init() {
        ((LekRemainderApplication) getApplication())
                .getDiComponent()
                .inject(this);
    }
}
