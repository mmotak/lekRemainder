package pl.com.mmotak.lekremainder.converters;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import pl.com.mmotak.lekremainder.entities.AbstractDbDose;
import pl.com.mmotak.lekremainder.entities.AbstractDbDrug;
import pl.com.mmotak.lekremainder.entities.DbDose;

/**
 * Created by mmotak on 16.12.2016.
 */

public class DoseConverter {

    public static List<LocalTime> toDoses(List<AbstractDbDose> dbDoseList) {
        List<LocalTime> outputList = new ArrayList<>();

        for (AbstractDbDose dbDose : dbDoseList) {
            outputList.add(((DbDose)dbDose).getTime());
        }

        return outputList;
    }

    public static List<DbDose> toDbDoses(List<LocalTime> doses, AbstractDbDrug dbDrug) {
        List<DbDose> outputList = new ArrayList<>();

        for (LocalTime time : doses) {
            DbDose dbDose = new DbDose();
            dbDose.setTime(time);
            dbDose.setDbDrug(dbDrug);

            outputList.add(dbDose);
        }

        return outputList;
    }
}
