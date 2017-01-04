package pl.com.mmotak.lekremainder.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.broadcasts.LekRemainderBootReceiver;
import pl.com.mmotak.lekremainder.broadcasts.LekRemainderMainReceiver;
import pl.com.mmotak.lekremainder.data.ShaderDataProvider;

/**
 * Created by Maciej on 2016-12-28.
 */

public class TodayDoseResetAlarmManager {

    public static void enableAlarms(Context context) {
        // enable
        enableBoot(context);

        ShaderDataProvider shaderDataProvider = new ShaderDataProvider(context);
        DateTime dateTime = shaderDataProvider.getTomorrowRestartDateTime();

        shaderDataProvider.saveNextResetDateTime(dateTime.getMillis());
        setNextAlarmTodayDoseResetService(context, dateTime);
        setNextAlarmNextDoseAlarmService(context, DateTime.now().plusMinutes(1));
    }

    public static void disableAlarms(Context context) {
        // disable
        disableBoot(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createPendingIntent(context, ServicesFactory.RESET, createServiceIntent(context, ServicesFactory.RESET)));
        alarmManager.cancel(createPendingIntent(context, ServicesFactory.NEXT_DOSE, createServiceIntent(context, ServicesFactory.NEXT_DOSE)));
    }

    private static void enableBoot(Context context) {
        ComponentName receiver = new ComponentName(context, LekRemainderBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    //TODO: move to settings
    public static void enableBootIfShouldBe(Context context, boolean shouldBeEnable) {
        ComponentName receiver = new ComponentName(context, LekRemainderBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        final int result = pm.getComponentEnabledSetting(receiver);
        if (shouldBeEnable && result != PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            enableBoot(context);
        }
    }

    private static void disableBoot(Context context) {
        ComponentName receiver = new ComponentName(context, LekRemainderBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    public static void setNextAlarmTodayDoseResetService(Context context, DateTime time) {
        setNextAlarm(context, time, ServicesFactory.RESET, ServicesFactory.RESET);
    }

    public static void setNextAlarmNextDoseAlarmService(Context context, DateTime time) {
        setNextAlarm(context, time, ServicesFactory.NEXT_DOSE, ServicesFactory.NEXT_DOSE);
    }

    private static void setNextAlarm(Context context, DateTime time, int requestCode, int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, time.getMillis(),
                createPendingIntent(context, requestCode, createServiceIntent(context, id)));
    }

    private static Intent createServiceIntent(Context context, int id) {
        Intent serviceIntent = new Intent(context, LekRemainderMainReceiver.class);
        serviceIntent.putExtra(LekRemainderMainReceiver.KEY, id);
        return serviceIntent;
    }

    private static PendingIntent createPendingIntent(Context context, int requestCode, Intent inputIntent) {
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, inputIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }
}
