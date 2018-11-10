package pl.com.mmotak.lekremainder.broadcasts;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.alarms.ServicesFactory;
import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;

public class LekRemainderMainReceiver extends WakefulBroadcastReceiver {
    private static final ILogger LOGGER = LekLogger.create(LekRemainderMainReceiver.class.getSimpleName());
    public static final String KEY = "ID_SERVICE_CLASS";

    public LekRemainderMainReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LOGGER.d("onReceive " + DateTime.now());
        Bundle extra = intent.getExtras();
        int id = extra.getInt(KEY);
        LOGGER.d("onReceive id " + id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(ServicesFactory.getServiceIntent(context, id));
        } else {
            startWakefulService(context, ServicesFactory.getServiceIntent(context, id));
        }
    }
}
