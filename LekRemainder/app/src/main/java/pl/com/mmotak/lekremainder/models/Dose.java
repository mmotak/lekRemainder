package pl.com.mmotak.lekremainder.models;

import org.joda.time.LocalTime;

/**
 * Created by mmotak on 16.12.2016.
 */

public class Dose {
    private int id;
    private Drug drug;
    private LocalTime time;

    public Dose(int id, Drug drug, LocalTime time) {
        this.id = id;
        this.drug = drug;
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
