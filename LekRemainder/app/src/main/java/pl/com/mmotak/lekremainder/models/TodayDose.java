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
    private int shiftInDays;

    public TodayDose(int id, Drug drug, LocalTime time, TakeDose takeDose, int shiftInDays) {
        this.drug = drug;
        this.id = id;
        this.time = time;
        this.takeDose = takeDose;
        this.shiftInDays = shiftInDays;
    }

    public TakeDose getTakeDose() {
        return takeDose;
    }

    public void setTakeDose(TakeDose takeDose) {
        this.takeDose = takeDose;
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

    public boolean wasTaken() {
        return takeDose != null && takeDose.isTaken();
    }

    public DateTime getTakenTime() {
        return takeDose.getTime();
    }

    public int getShiftInSeconds() {
        return takeDose != null ? takeDose.getShiftInSeconds() : 0;
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

    public DateTime getEstimatedDateTime() {
        if (takeDose != null && takeDose.getTime() != null) {
            return takeDose.getTime();
        } else {
            return getTime().toDateTimeToday().plusDays(shiftInDays).plusSeconds(getShiftInSeconds());
        }
    }

    public String getMessage() {
        return getDrugName() + ": " + getEstimatedDateTime().toString("HH:mm:ss");
    }
}
