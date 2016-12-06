package pl.com.mmotak.lekremainder.models;

import org.joda.time.DateTime;

/**
 * Created by mmotak on 28.11.2016.
 */

public class DrugOld {

    public String name;
    public String type;

    public int doses;
    public int dosesEveryH;

    private EnableObject<DateTime> startDate;
    private EnableObject<DateTime> endDate;

    public DrugOld(String name, String type, int doses, int dosesEveryH) {
        this.name = name;
        this.type = type;
        this.doses = doses;
        this.dosesEveryH = dosesEveryH;
    }

    public DrugOld() {
        name = "";
        type = "";
        doses = 3;
        dosesEveryH = 4;

        startDate = new EnableObject<>();
        endDate = new EnableObject<>();
    }

    public void enableStartDate(boolean enable) {
        startDate.setEnable(enable);
    }

    public void enableEndDate(boolean enable) {
        endDate.setEnable(enable);
    }

    public void setStartDate(DateTime dateTime) {
        startDate.setValue(dateTime);
    }

    public void setEndDate(DateTime dateTime) {
        endDate.setValue(dateTime);
    }

    public boolean isStartDateEnable() {
        return startDate.isEnable();
    }

    public boolean isEndDateEnable() {
        return endDate.isEnable();
    }

    public DateTime getStartDate() {
        return startDate.getValue();
    }

    public DateTime getEndDate() {
        return endDate.getValue();
    }
}






















