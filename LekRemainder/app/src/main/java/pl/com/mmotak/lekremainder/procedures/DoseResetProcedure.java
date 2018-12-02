package pl.com.mmotak.lekremainder.procedures;

import android.content.Context;

import org.joda.time.DateTime;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.alarms.TodayDoseResetAlarmManager;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.data.ISharedDateProvider;

public class DoseResetProcedure extends AProcerude {
    @Inject IDataProvider dataProvider;
    @Inject ISharedDateProvider sharedDateProvider;

    public DoseResetProcedure(IDataProvider dataProvider, ISharedDateProvider sharedDateProvider) {
        this.dataProvider = dataProvider;
        this.sharedDateProvider = sharedDateProvider;
    }

    public DoseResetProcedure(Context context) {
        getDiCompoment(context).inject(this);
    }

    @Override
    public void doJob(Context context, IEndProcedure endProcedure) {
        try {
            dataProvider.removeAllTodayDoses();

            DateTime dateTime = sharedDateProvider.getTomorrowRestartDateTime();
            sharedDateProvider.saveNextResetDateTime(dateTime.getMillis());

            TodayDoseResetAlarmManager.setNextAlarmTodayDoseResetService(context, dateTime);
            TodayDoseResetAlarmManager.setNextAlarmNextDoseAlarmService(context, DateTime.now().plusMinutes(2));
        } finally {
            if (endProcedure != null) {
                endProcedure.onCompleted();
            }
        }
    }
}
