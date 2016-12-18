package pl.com.mmotak.lekremainder.converters;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import pl.com.mmotak.lekremainder.entities.AbstractDbDose;
import pl.com.mmotak.lekremainder.entities.DbDose;
import pl.com.mmotak.lekremainder.entities.DbDrug;
import pl.com.mmotak.lekremainder.models.Dose;
import pl.com.mmotak.lekremainder.models.Drug;

import static android.media.CamcorderProfile.get;

/**
 * Created by mmotak on 07.12.2016.
 */

public class DrugConverter {

    private static String DbDateTimeFormat = "MMddyyyy";
    private static DateTimeFormatter DateTimeFormatter = DateTimeFormat.forPattern(DbDateTimeFormat);

    public static List<Drug> toDrugs(List<DbDrug> dbDrugs) {
        List<Drug> drugs = new ArrayList<>();

        for (DbDrug dbDrug : dbDrugs) {
            drugs.add(toDrug(dbDrug));
        }

        return drugs;
    }

    public static Drug toDrug(DbDrug dbDrug) {
        if (dbDrug == null) {
            throw new IllegalArgumentException("AbstractDbDrug cannot be NULL!");
        }

        Drug drug = new Drug();

        drug.setId(dbDrug.getId());
        drug.setName(dbDrug.getName());
        drug.setType(dbDrug.getType());
        drug.setDosesEveryH(dbDrug.getDosesEveryH());
        drug.setDosesNo(dbDrug.getDosesNo());
        drug.setDoses(DoseConverter.toDoses(dbDrug.getDbDoses(), drug));

        return drug;
    }

    public static DbDrug toDbDrug(Drug drug, DbDrug dbDrugInput) {
        if (drug == null) {
            throw new IllegalArgumentException("Drug cannot be NULL!");
        }

        DbDrug dbDrug;

        if (dbDrugInput != null) {
            if (dbDrugInput.getId() == drug.getId()) {
                dbDrug = dbDrugInput;
            } else {
                throw new IllegalArgumentException("Drug and DbDrug Must have the same id!");
            }
        } else {
            dbDrug = new DbDrug();
        }

        dbDrug.setName(drug.getName());
        dbDrug.setType(drug.getType());
        dbDrug.setDosesNo(drug.getDosesNo());
        dbDrug.setDosesEveryH(drug.getDosesEveryH());

        if (dbDrug.getDbDoses() == null || dbDrug.getDbDoses().size() == 0) {
            dbDrug.getDbDoses().addAll(DoseConverter.toDbDoses(drug.getDoses(), dbDrug));
        } else {
            int size = drug.getDoses().size();
            int dbSize = dbDrug.getDbDoses().size();
            List<AbstractDbDose> dbDoses = dbDrug.getDbDoses();
            List<Dose> doses = drug.getDoses();
            for (int i = 0; i < size; i++) {
                if (i < dbSize) {
                    DbDose dbDose = (DbDose) dbDoses.get(i);
                    dbDose.setTime(doses.get(i).getTime());
                } else {
                    dbDoses.add(DoseConverter.toDbDose(drug.getDoses().get(i), dbDrug));
                }
            }
        }

        return dbDrug;
    }

    public static DbDrug toDbDrug(Drug drug) {
        return toDbDrug(drug, null);
    }

    private static String parse(DateTime dateTime) {
        return dateTime == null ? null : dateTime.toString(DbDateTimeFormat);
    }

    private static DateTime parse(String dateTime) {
        if (dateTime == null || dateTime.length() == 0) {
            return null;
        }
        return DateTimeFormatter.parseDateTime(dateTime);
    }
}
