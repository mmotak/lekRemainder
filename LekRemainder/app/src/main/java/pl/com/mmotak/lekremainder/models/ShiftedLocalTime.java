package pl.com.mmotak.lekremainder.models;

import org.joda.time.LocalTime;

/**
 * Created by Maciej on 2016-12-30.
 */

public class ShiftedLocalTime {
    private LocalTime localTime;
    private int shiftInDays;

    public ShiftedLocalTime(LocalTime localTime, int shiftInDays) {
        this.localTime = localTime;
        this.shiftInDays = shiftInDays;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public int getShiftInDays() {
        return shiftInDays;
    }
}
