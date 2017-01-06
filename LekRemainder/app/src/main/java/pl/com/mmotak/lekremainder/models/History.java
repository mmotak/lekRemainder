package pl.com.mmotak.lekremainder.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.chrono.StrictChronology;

/**
 * Created by Maciej on 2017-01-06.
 */

public class History extends BaseObservable {

    private final int id;
    private final int doseId;
    private final String name;
    private final DateTime time;
    private boolean isHeaderVisible;

    public History(int id, int doseId, String name, DateTime time, boolean isHeaderVisible) {
        this.id = id;
        this.doseId = doseId;
        this.name = name;
        this.time = time;
        this.isHeaderVisible = isHeaderVisible;
    }

    @Bindable
    public String getDay() {
        return String.format("%02d",time.getDayOfMonth());
    }

    @Bindable
    public String getMonth() {
        return String.format("%02d",time.getMonthOfYear());
    }

    @Bindable
    public int getHeaderVisibility() {
        return isHeaderVisible ? View.VISIBLE : View.INVISIBLE;
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public LocalTime getTime() {
        return time.toLocalTime();
    }
}
