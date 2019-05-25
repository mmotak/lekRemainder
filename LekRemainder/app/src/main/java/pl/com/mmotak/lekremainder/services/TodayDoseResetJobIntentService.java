package pl.com.mmotak.lekremainder.services;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;
import pl.com.mmotak.lekremainder.procedures.DoseResetProcedure;
import pl.com.mmotak.lekremainder.procedures.IEndProcedure;

public class TodayDoseResetJobIntentService extends JobIntentService {
    public static final int ID = 1808;
    private static final ILogger LOGGER = LekLogger.create(TodayDoseResetJobIntentService.class.getSimpleName());

    public static void enqueueWork(Context context, Intent intent) {
        LOGGER.d("enqueueWork with intent " + intent);
        enqueueWork(context, TodayDoseResetJobIntentService.class, ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        LOGGER.d("onHandleWork start at " + DateTime.now().toString());
        new DoseResetProcedure(this).doJob(this, new IEndProcedure() {
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
