package pl.com.mmotak.lekremainder.services;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;
import pl.com.mmotak.lekremainder.procedures.IEndProcedure;
import pl.com.mmotak.lekremainder.procedures.NextDoseProcedure;

public class NextDoseAlarmJobIntentService extends JobIntentService {
    public static final int ID = 808;
    private static final ILogger LOGGER = LekLogger.create(NextDoseAlarmJobIntentService.class.getSimpleName());

    public static void enqueueWork(Context context, Intent intent) {
        LOGGER.d("enqueueWork with intent " + intent);
        enqueueWork(context, NextDoseAlarmJobIntentService.class, ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        LOGGER.d("onHandleWork start at " + DateTime.now().toString());
        new NextDoseProcedure(this).doJob(this, new IEndProcedure() {
            @Override
            public void onCompleted() {
                LOGGER.d("onHandleWork end (Completed) at " + DateTime.now().toString());
                stopSelf();
            }

            @Override
            public void onError() {
                LOGGER.d("onHandleWork end (Error) at " + DateTime.now().toString());
                stopSelf();
            }
        });
        LOGGER.d("onHandleWork end at " + DateTime.now().toString());
    }
}
