package pl.com.mmotak.lekremainder.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.alarms.TodayDoseResetAlarmManager;
import pl.com.mmotak.lekremainder.data.ShaderDataProvider;
import pl.com.mmotak.lekremainder.settings.SavedSettings;

/**
 * Created by Maciej on 2016-12-28.
 */

public class LekRemainderBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            ShaderDataProvider shaderDataProvider = new ShaderDataProvider(context);
            long nextResetTime = shaderDataProvider.loadNextResetDateTime();
            DateTime time = SavedSettings.getTomorrowRestartDateTime();

            if (nextResetTime > 0 && nextResetTime <= time.getMillis()) {
                if (nextResetTime > DateTime.now().getMillis()) {
                    TodayDoseResetAlarmManager.setNextAlarmTodayDoseResetService(context, new DateTime(nextResetTime));
                } else {
                    TodayDoseResetAlarmManager.setNextAlarmTodayDoseResetService(context, DateTime.now().plusMinutes(2));
                }
            } else {
                TodayDoseResetAlarmManager.setNextAlarmTodayDoseResetService(context, time);
                shaderDataProvider.saveNextResetDateTime(time.getMillis());
            }

            TodayDoseResetAlarmManager.setNextAlarmNextDoseAlarmService(context, DateTime.now().plusMinutes(4));
        }
    }




}