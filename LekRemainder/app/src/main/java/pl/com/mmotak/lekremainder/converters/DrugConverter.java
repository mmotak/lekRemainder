package pl.com.mmotak.lekremainder.converters;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import pl.com.mmotak.lekremainder.entities.DbDrug;
import pl.com.mmotak.lekremainder.models.Drug;

/**
 * Created by mmotak on 07.12.2016.
 */

public class DrugConverter {

    private static String DbDateTimeFormat = "MMddyyyy";
    private static DateTimeFormatter DateTimeFormatter = DateTimeFormat.forPattern(DbDateTimeFormat);

    public static List<Drug> toDrugs(List<DbDrug> dbDrugs){
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
        drug.setDoses(DoseConverter.toDoses(dbDrug.getDbDoses()));

        return drug;
    }

    public static DbDrug toDbDrug(Drug drug) {
        if (drug == null) {
            throw new IllegalArgumentException("Drug cannot be NULL!");
        }

        DbDrug dbDrug = new DbDrug();

        dbDrug.setName(drug.getName());
        dbDrug.setType(drug.getType());
        dbDrug.setDosesNo(drug.getDosesNo());
        dbDrug.setDosesEveryH(drug.getDosesEveryH());
        dbDrug.getDbDoses().addAll(DoseConverter.toDbDoses(drug.getDoses(), dbDrug));

        return dbDrug;
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
