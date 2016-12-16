package pl.com.mmotak.lekremainder.converters;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import pl.com.mmotak.lekremainder.entities.AbstractDbDose;
import pl.com.mmotak.lekremainder.entities.AbstractDbDrug;
import pl.com.mmotak.lekremainder.entities.DbDose;
import pl.com.mmotak.lekremainder.entities.DbDrug;
import pl.com.mmotak.lekremainder.models.TodayDose;

/**
 * Created by mmotak on 16.12.2016.
 */

public class DoseConverter {

    public static List<TodayDose> toTodayDoses(List<DbDose> dbDoses) {
        List<TodayDose> outputList = new ArrayList<>();

        for (DbDose dbDose : dbDoses) {
            dbDose.getId();
            dbDose.getDbDrug();
            dbDose.getTime();

            outputList.add(new TodayDose(dbDose.getId(), DrugConverter.toDrug((DbDrug) dbDose.getDbDrug()), dbDose.getTime()));
        }

        return outputList;

    }

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
