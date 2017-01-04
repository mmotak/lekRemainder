package pl.com.mmotak.lekremainder.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;

import pl.com.mmotak.lekremainder.alarms.ServicesFactory;
import pl.com.mmotak.lekremainder.services.TodayDoseResetService;

public class LekRemainderMainReceiver extends WakefulBroadcastReceiver {

    public static final String KEY = "ID_SERVICE_CLASS";

    public LekRemainderMainReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extra = intent.getExtras();
        int id = extra.getInt(KEY);

        startWakefulService(context, ServicesFactory.getServiceIntent(context, id));
        //context.startService(ServicesFactory.getServiceIntent(context, id));
    }
}
