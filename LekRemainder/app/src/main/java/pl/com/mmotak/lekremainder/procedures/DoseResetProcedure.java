package pl.com.mmotak.lekremainder.procedures;

import android.content.Context;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.alarms.TodayDoseResetAlarmManager;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.data.ISharedDateProvider;
import pl.com.mmotak.lekremainder.notification.INotificationProvider;

public class DoseResetProcedure {
    private IDataProvider dataProvider;
    private ISharedDateProvider sharedDateProvider;
    private INotificationProvider notificationProvider;

    public DoseResetProcedure(IDataProvider dataProvider, ISharedDateProvider sharedDateProvider, INotificationProvider notificationProvider) {
        this.dataProvider = dataProvider;
        this.sharedDateProvider = sharedDateProvider;
        this.notificationProvider = notificationProvider;
    }

    private void dojob(Context context) {
        dataProvider.removeAllTodayDoses();

        DateTime dateTime = sharedDateProvider.getTomorrowRestartDateTime();
        sharedDateProvider.saveNextResetDateTime(dateTime.getMillis());

        TodayDoseResetAlarmManager.setNextAlarmTodayDoseResetService(context, dateTime);
        TodayDoseResetAlarmManager.setNextAlarmNextDoseAlarmService(context, DateTime.now().plusMinutes(2));
    }
}
