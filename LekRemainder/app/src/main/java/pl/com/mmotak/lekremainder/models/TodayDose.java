package pl.com.mmotak.lekremainder.models;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;

/**
 * Created by mmotak on 16.12.2016.
 */

public class TodayDose {

    private Drug drug;
    private int id;
    private LocalTime time;
    private TakeDose takeDose;

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

    public int getId() {
        return id;
    }

    public TakeDose getTakeDose() {
        return takeDose;
    }

    public void setTakeDose(TakeDose takeDose) {
        this.takeDose = takeDose;
    }

    public boolean wasTaken() {
        return takeDose != null && takeDose.isTaken();
    }

    public DateTime getTakenTime() {
        return takeDose.getTime();
    }

    public int getShift() {
        return takeDose != null ? takeDose.getShift() : 0;
    }

    public void setTaken(DateTime takenTime) {
        Seconds seconds = Seconds.secondsBetween(time, takenTime.toLocalTime());
        int shift = seconds.getSeconds();

        takeDose = new TakeDose(
                0,
                takenTime,
                shift,
                true);
    }

    public Drug getDrug() {
        return drug;
    }
}
