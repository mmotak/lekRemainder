package pl.com.mmotak.lekremainder.models;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmotak on 28.11.2016.
 */

public class Drug {

    private int id;

    private String name;
    private String type;

    private int dosesNo;
    private int dosesEveryH;

    private List<Dose> doses = new ArrayList<>();

    public Drug() {
        name = "";
        type = "";
        dosesNo = 3;
        dosesEveryH = 4;

        List<ShiftedLocalTime> times = DosesTimesGenerator.generate(null, dosesNo, dosesEveryH);
        for (ShiftedLocalTime time: times) {
            doses.add(new Dose(0, this, time.getLocalTime(), time.getShiftInDays()));
        }
    }

    public Drug(String name, String type, int dosesNo, int dosesEveryH, List<Dose> doses) {
        this.name = name;
        this.type = type;
        this.dosesNo = dosesNo;
        this.dosesEveryH = dosesEveryH;
        this.doses = doses;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<Dose> getDoses() {
        return doses;
    }

    public void setDoses(List<Dose> doses) {
        this.doses = doses;
    }

    public void update(String name, String type, int dosesNo, int dosesEveryH, List<Dose> doses) {
        this.name = name;
        this.type = type;
        this.dosesEveryH = dosesEveryH;
        this.dosesNo = dosesNo;
        this.doses = doses;
    }
}






















