package pl.com.mmotak.lekremainder.alarms;

import android.content.Context;
import android.content.Intent;

import pl.com.mmotak.lekremainder.services.NextDoseAlarmService;
import pl.com.mmotak.lekremainder.services.TodayDoseResetService;

/**
 * Created by mmotak on 02.01.2017.
 */

public class ServicesFactory {

    public static final int RESET = 3;
    public static final int NEXT_DOSE = RESET + 1;
    public static final int ACTIVITY_DOSE = NEXT_DOSE + 1;

    public static Intent getServiceIntent(Context context, int id) {
        switch (id) {
            case RESET:
                return new Intent(context, TodayDoseResetService.class);
            case NEXT_DOSE:
                return new Intent(context, NextDoseAlarmService.class);
            default:
                return new Intent(context, NextDoseAlarmService.class);
        }
    }

    public static String getName(int id) {
        switch (id) {
            case ACTIVITY_DOSE:
                return "ACTIVITY_DOSE(" + ACTIVITY_DOSE + ")";
            case NEXT_DOSE:
                return "NEXT_DOSE(" + NEXT_DOSE + ")";
            case RESET:
                return "RESET(" + RESET + ")";
        }
        return "" + id;
    }
}
