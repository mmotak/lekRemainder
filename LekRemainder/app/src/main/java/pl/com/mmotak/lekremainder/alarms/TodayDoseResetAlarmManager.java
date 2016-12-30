package pl.com.mmotak.lekremainder.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.broadcasts.LekRemainderBootReceiver;
import pl.com.mmotak.lekremainder.broadcasts.LekRemainderMainReceiver;
import pl.com.mmotak.lekremainder.data.ShaderDataProvider;
import pl.com.mmotak.lekremainder.services.NextDoseAlarmService;
import pl.com.mmotak.lekremainder.services.TodayDoseResetService;
import pl.com.mmotak.lekremainder.settings.SavedSettings;

/**
 * Created by Maciej on 2016-12-28.
 */

public class TodayDoseResetAlarmManager {

    public static void enableAlarms(Context context) {
        // enable
        enableBoot(context);
        DateTime dateTime = SavedSettings.getNextRestartDateTime();
        ShaderDataProvider shaderDataProvider = new ShaderDataProvider(context);
        shaderDataProvider.saveNextResetDateTime(dateTime.getMillis());
        setNextAlarmTodayDoseResetService(context, dateTime);
        setNextAlarmNextDoseAlarmService(context, DateTime.now().plusMinutes(1));

    }

    public static void disableAlarms(Context context) {
        // disable
        disableBoot(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createPendingIntent(context, 3, createServiceIntent(context, TodayDoseResetService.class)));
        alarmManager.cancel(createPendingIntent(context, 4, createServiceIntent(context, NextDoseAlarmService.class)));
    }

    private static void enableBoot(Context context) {
        ComponentName receiver = new ComponentName(context, LekRemainderBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private static void disableBoot(Context context) {
        ComponentName receiver = new ComponentName(context, LekRemainderBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    public static void setNextAlarmTodayDoseResetService(Context context, DateTime time) {
        setNextAlarm(context, time, 3, TodayDoseResetService.class);
    }

    public static void setNextAlarmNextDoseAlarmService(Context context, DateTime time) {
        setNextAlarm(context, time, 4, NextDoseAlarmService.class);
    }

    private static void setNextAlarm(Context context, DateTime time, int requestCode, Class<? extends Service> serviceClass) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, time.getMillis(),
                createPendingIntent(context, requestCode, createServiceIntent(context, serviceClass)));
    }

    private static Intent createServiceIntent(Context context, Class<? extends Service> serviceClass) {
        Intent serviceIntent = new Intent(context, LekRemainderMainReceiver.class);
        serviceIntent.putExtra(LekRemainderMainReceiver.KEY, serviceClass);
        return serviceIntent;
    }

    private static PendingIntent createPendingIntent(Context context, int requestCode, Intent inputIntent) {
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, inputIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }
}
