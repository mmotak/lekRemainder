package pl.com.mmotak.lekremainder.models;

import org.joda.time.DateTime;

/**
 * Created by mmotak on 28.11.2016.
 */

public class Drug {

    public String name;
    public String type;

    private EnableObject<DateTime> startDate;
    private EnableObject<DateTime> endDate;

    public Drug(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Drug() {
        name = "";
        type = "";

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
        startDate.setValue(dateTime);
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






















