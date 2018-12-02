package pl.com.mmotak.lekremainder.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.alarms.ServicesFactory;
import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;
import pl.com.mmotak.lekremainder.services.NextDoseAlarmJobIntentService;
import pl.com.mmotak.lekremainder.services.TodayDoseResetJobIntentService;

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
            case ServicesFactory.NEXT_DOSE:
                NextDoseAlarmJobIntentService.enqueueWork(context, intent);
                break;
            case ServicesFactory.RESET:
                TodayDoseResetJobIntentService.enqueueWork(context, intent);
                break;
        }

        // version 2 with procedures
//        switch (id) {
//            case ServicesFactory.NEXT_DOSE: new NextDoseProcedure(context).doJob(context, null); break;
//            case ServicesFactory.RESET: new DoseResetProcedure(context).doJob(context, null); break;
//        }

        // old version with foreground services
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
