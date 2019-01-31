package pl.com.mmotak.lekremainder.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.alarms.ServicesFactory;
import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;
import pl.com.mmotak.lekremainder.procedures.DoseResetProcedure;
import pl.com.mmotak.lekremainder.procedures.IEndProcedure;
import pl.com.mmotak.lekremainder.procedures.NextDoseProcedure;
import pl.com.mmotak.lekremainder.wakelock.LekWakeLock;
import pl.com.mmotak.lekremainder.wakelock.ScreenLekWakeLock;

public class LekRemainderMainReceiver extends BroadcastReceiver {
    private static final String LOCK_TAG = "lekremainder:LekRemainderMainReceiver";
    private static final ILogger LOGGER = LekLogger.create(LekRemainderMainReceiver.class.getSimpleName());
    public static final String KEY = "ID_SERVICE_CLASS";

    public LekRemainderMainReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LekWakeLock lekWakeLock = new ScreenLekWakeLock(context, LOCK_TAG);

        // new code with procedures
        LOGGER.d("onReceive " + DateTime.now());
        int id = intent.getExtras().getInt(KEY);
        LOGGER.d("onReceive id " + ServicesFactory.getName(id));

//        switch (id) {
//            case ServicesFactory.NEXT_DOSE:
//                NextDoseAlarmJobIntentService.enqueueWork(context, intent);
//                break;
//            case ServicesFactory.RESET:
//                TodayDoseResetJobIntentService.enqueueWork(context, intent);
//                break;
//        }

        // version 2 with procedures
        switch (id) {
            case ServicesFactory.NEXT_DOSE:
                lekWakeLock.start();
                new NextDoseProcedure(context).doJob(context, new IEndProcedure() {
                    @Override
                    public void onCompleted() {
                        lekWakeLock.stop();
                    }

                    @Override
                    public void onError() {
                        lekWakeLock.stop();
                    }
                });
                break;
            case ServicesFactory.RESET:
                lekWakeLock.start();
                new DoseResetProcedure(context).doJob(context, new IEndProcedure() {
                    @Override
                    public void onCompleted() {
                        lekWakeLock.stop();
                    }

                    @Override
                    public void onError() {
                        lekWakeLock.stop();
                    }
                });
                break;
        }

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

//    @SuppressLint("InvalidWakeLockTag")
//    public void turnOnScreen(Context context){
//        // turn on screen
//        PowerManager powerManager = (PowerManager) context.getSystemService(POWER_SERVICE);
//        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
//        wakeLock.acquire(60*1000L /*1 minute*/);
//    }


//    private void unlockScreen(Context context) {
//        Window window = context.getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
//    }
}
