package pl.com.mmotak.lekremainder.models;

import org.joda.time.LocalTime;

/**
 * Created by mmotak on 16.12.2016.
 */

public class Dose {
    private int id;
    private transient Drug drug;
    private LocalTime time;
    private int shiftInDays;

    public Dose(int id, Drug drug, LocalTime time, int shiftInDays) {
        this.id = id;
        this.drug = drug;
        this.time = time;
        this.shiftInDays = shiftInDays;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getShiftInDays() {
        return shiftInDays;
    }

    public void setShiftInDays(int shiftInDays) {
        this.shiftInDays = shiftInDays;
    }
}
