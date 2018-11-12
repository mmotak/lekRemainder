package pl.com.mmotak.lekremainder.procedures;

import android.content.Context;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.alarms.TodayDoseResetAlarmManager;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.data.ISharedDateProvider;
import pl.com.mmotak.lekremainder.lekapp.LekRemainderApplication;
import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;
import pl.com.mmotak.lekremainder.models.TodayDose;
import pl.com.mmotak.lekremainder.notification.INotificationProvider;
import rx.Subscriber;

public class NextDoseProcedure extends AProcerude {
    private static final ILogger LOGGER = LekLogger.create(NextDoseProcedure.class.getSimpleName());

    @Inject IDataProvider dataProvider;
    @Inject INotificationProvider notificationProvider;
    @Inject ISharedDateProvider sharedDateProvider;

    public NextDoseProcedure(IDataProvider dataProvider, INotificationProvider notificationProvider, ISharedDateProvider sharedDateProvider) {
        this.dataProvider = dataProvider;
        this.notificationProvider = notificationProvider;
        this.sharedDateProvider = sharedDateProvider;
    }

    public NextDoseProcedure(Context context) {
        getDiCompoment(context).inject(this);
    }

    @Override
    public void doJob(Context context) {
        LOGGER.d("doJob " + DateTime.now());

        DateTime now = DateTime.now();

        /*subscribe = */
        dataProvider.getObservableForNotTakenTodayDoseAfterDateTime()
                .subscribe(new Subscriber<List<TodayDose>>() {
                    @Override
                    public void onCompleted() {
                        LOGGER.d("onCompleted ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LOGGER.e(e.getMessage(), e);
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

                            TodayDoseResetAlarmManager.setNextAlarmTodayDoseResetService(context, resetTime);
                            sharedDateProvider.saveNextResetDateTime(resetTime.getMillis());
                        } else {
                            TodayDoseResetAlarmManager.setNextAlarmNextDoseAlarmService(context, minimum);
                        }
                        LOGGER.d("onNext - end");
                    }
                });
    }
}
