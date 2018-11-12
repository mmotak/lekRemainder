package pl.com.mmotak.lekremainder.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.alarms.ServicesFactory;
import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;
import pl.com.mmotak.lekremainder.procedures.DoseResetProcedure;
import pl.com.mmotak.lekremainder.procedures.NextDoseProcedure;

public class LekRemainderMainReceiver extends BroadcastReceiver {
    private static final ILogger LOGGER = LekLogger.create(LekRemainderMainReceiver.class.getSimpleName());
    public static final String KEY = "ID_SERVICE_CLASS";

    public LekRemainderMainReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // new code with procedures
        LOGGER.d("onReceive " + DateTime.now());
        int id = intent.getExtras().getInt(KEY);
        LOGGER.d("onReceive id " + ServicesFactory.getName(id));

        switch (id) {
            case ServicesFactory.NEXT_DOSE: new NextDoseProcedure(context).doJob(context); break;
            case ServicesFactory.RESET: new DoseResetProcedure(context).doJob(context); break;
        }

//        try {
//            LOGGER.d("onReceive " + DateTime.now());
//            Bundle extra = intent.getExtras();
//            int id = extra.getInt(KEY);
//            LOGGER.d("onReceive id " + ServicesFactory.getName(id));
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                context.startForegroundService(ServicesFactory.getServiceIntent(context, id));
//            } else {
//                context.startService(ServicesFactory.getServiceIntent(context, id));
//            }
//        } catch (Exception e) {
//            LOGGER.e(e.getMessage(), e);
//        }
    }
}
