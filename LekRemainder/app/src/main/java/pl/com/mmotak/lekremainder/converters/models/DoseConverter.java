package pl.com.mmotak.lekremainder.converters.models;

import java.util.ArrayList;
import java.util.List;

import pl.com.mmotak.lekremainder.entities.DbTakeDose;
import pl.com.mmotak.lekremainder.entities.IDbDose;
import pl.com.mmotak.lekremainder.entities.IDbDrug;
import pl.com.mmotak.lekremainder.entities.DbDose;
import pl.com.mmotak.lekremainder.entities.DbDrug;
import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;
import pl.com.mmotak.lekremainder.models.Dose;
import pl.com.mmotak.lekremainder.models.Drug;
import pl.com.mmotak.lekremainder.models.TodayDose;

/**
 * Created by mmotak on 16.12.2016.
 */

public class DoseConverter {
    private static final ILogger LOGGER = LekLogger.create(DoseConverter.class.getSimpleName());

    public static List<TodayDose> toTodayDoses(List<DbDose> dbDoses) {
        LOGGER.d("toTodayDoses - start convert");
        List<TodayDose> outputList = new ArrayList<>();

        for (DbDose dbDose : dbDoses) {
            dbDose.getId();
            dbDose.getDbDrug();
            dbDose.getTime();
            DbTakeDose dbTakeDose = (DbTakeDose) dbDose.getDbTakeDose();

            TodayDose todayDose = new TodayDose(
                    dbDose.getId(),
                    DrugConverter.toDrug((DbDrug) dbDose.getDbDrug()),
                    dbDose.getTime(),
                    TakenDoseConverter.toTakeDose(dbTakeDose),
                    dbDose.getShiftInDays());

            outputList.add(todayDose);
        }

        LOGGER.d("toTodayDoses - end convert");
        return outputList;

    }

    public static List<Dose> toDoses(List<IDbDose> dbDoseList, Drug drug) {
        List<Dose> outputList = new ArrayList<>();

        for (IDbDose dbDose : dbDoseList) {
            outputList.add(new Dose(dbDose.getId(), drug, dbDose.getTime(), dbDose.getShiftInDays()));
        }

        return outputList;
    }

    public static List<DbDose> toDbDoses(List<Dose> doses, IDbDrug dbDrug) {
        List<DbDose> outputList = new ArrayList<>();

        for (Dose dose : doses) {
            DbDose dbDose = new DbDose();
            dbDose.setTime(dose.getTime());
            dbDose.setDbDrug(dbDrug);

            outputList.add(dbDose);
        }

        return outputList;
    }

    public static IDbDose toDbDose(Dose dose, IDbDrug dbDrug) {
        DbDose dbDose = new DbDose();
        dbDose.setTime(dose.getTime());
        dbDose.setDbDrug(dbDrug);
        dbDose.setShiftInDays(dbDose.getShiftInDays());

        return dbDose;
    }
}
