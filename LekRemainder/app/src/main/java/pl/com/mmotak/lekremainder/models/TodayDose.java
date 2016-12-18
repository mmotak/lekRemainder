package pl.com.mmotak.lekremainder.models;

import org.joda.time.LocalTime;

/**
 * Created by mmotak on 16.12.2016.
 */

public class TodayDose {

    private Drug drug;
    private int id;
    private LocalTime time;

    public TodayDose(int id, Drug drug, LocalTime time) {
        this.drug = drug;
        this.id = id;
        this.time = time;
    }

    public String getDrugName() {
        return drug != null ? drug.getName() : "";
    }

    public LocalTime getTime() {
        return time;
    }
}
