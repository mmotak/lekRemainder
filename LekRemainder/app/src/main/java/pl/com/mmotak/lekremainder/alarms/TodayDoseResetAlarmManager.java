package pl.com.mmotak.lekremainder.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.broadcasts.LekRemainderMainReceiver;
import pl.com.mmotak.lekremainder.services.TodayDoseResetService;

/**
 * Created by Maciej on 2016-12-28.
 */

public class TodayDoseResetAlarmManager {
    public static void setNextAlarm(Context context, DateTime time) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, time.getMillis(),
                createPendingIntent(context, createServiceIntent(context, TodayDoseResetService.class)));
    }

    private static Intent createServiceIntent(Context context, Class<?> serviceClass) {
        Intent serviceIntent = new Intent(context, LekRemainderMainReceiver.class);
        serviceIntent.putExtra(LekRemainderMainReceiver.KEY, serviceClass);
        return serviceIntent;
    }

    private static PendingIntent createPendingIntent(Context context, Intent inputIntent) {
        PendingIntent pi = PendingIntent.getBroadcast(context, 2, inputIntent , PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }
}
