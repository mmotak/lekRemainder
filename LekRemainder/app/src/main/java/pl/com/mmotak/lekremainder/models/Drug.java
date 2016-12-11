package pl.com.mmotak.lekremainder.models;

import org.joda.time.DateTime;

/**
 * Created by mmotak on 28.11.2016.
 */

public class Drug {

    private String name;
    private String type;

    private int dosesNo;
    private int dosesEveryH;

    private EnableObject<DateTime> startDate;
    private EnableObject<DateTime> endDate;
    private int id;

    public Drug() {
        name = "";
        type = "";
        dosesNo = 3;
        dosesEveryH = 4;

        startDate = new EnableObject<>();
        endDate = new EnableObject<>();
    }

    public Drug(String name, String type, int dosesNo, int dosesEveryH) {
        this.name = name;
        this.type = type;
        this.dosesNo = dosesNo;
        this.dosesEveryH = dosesEveryH;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDosesNo() {
        return dosesNo;
    }

    public void setDosesNo(int dosesNo) {
        this.dosesNo = dosesNo;
    }

    public int getDosesEveryH() {
        return dosesEveryH;
    }

    public void setDosesEveryH(int dosesEveryH) {
        this.dosesEveryH = dosesEveryH;
    }

    public void setStartDateEnable(boolean enable) {
        startDate.setEnable(enable);
    }

    public void setEndDateEnable(boolean enable) {
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}






















