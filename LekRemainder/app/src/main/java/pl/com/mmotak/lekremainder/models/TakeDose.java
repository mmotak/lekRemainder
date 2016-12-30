package pl.com.mmotak.lekremainder.models;

import org.joda.time.DateTime;

/**
 * Created by mmotak on 19.12.2016.
 */
public class TakeDose {

    private int id;
    private DateTime time;
    private int shift;
    private boolean taken;

    public TakeDose(int id, DateTime time, int shift, boolean taken) {
        this.id = id;
        this.time = time;
        this.shift = shift;
        this.taken = taken;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public DateTime getTime() {
        return time;
    }

    public int getShiftInSeconds() {
        return shift;
    }
}
