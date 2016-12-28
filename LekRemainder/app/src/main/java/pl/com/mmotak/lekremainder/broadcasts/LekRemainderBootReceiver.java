package pl.com.mmotak.lekremainder.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.alarms.TodayDoseResetAlarmManager;

/**
 * Created by Maciej on 2016-12-28.
 */

public class LekRemainderBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            //ShaderDataProvider shaderDataProvider = new ShaderDataProvider(context);
            //long reset = shaderDataProvider.loadReset();

            DateTime time = DateTime.now().plusDays(1).withTime(6, 0, 0, 0); // set tomorrow at 6 am
            TodayDoseResetAlarmManager.setNextAlarm(context, time);
        }
    }
}