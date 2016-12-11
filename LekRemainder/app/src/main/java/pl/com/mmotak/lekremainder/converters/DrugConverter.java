package pl.com.mmotak.lekremainder.converters;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import pl.com.mmotak.lekremainder.entities.DbDrug;
import pl.com.mmotak.lekremainder.entities.DbDrugEntity;
import pl.com.mmotak.lekremainder.models.Drug;

/**
 * Created by mmotak on 07.12.2016.
 */

public class DrugConverter {

    private static String DbDateTimeFormat = "MMddyyyy";
    private static DateTimeFormatter DateTimeFormatter = DateTimeFormat.forPattern(DbDateTimeFormat);

    public static Drug toDrug(DbDrug dbDrug) {
        if (dbDrug == null) {
            throw new IllegalArgumentException("DbDrug cannot be NULL!");
        }

        Drug drug = new Drug();

        drug.setId(dbDrug.getId());
        drug.setName(dbDrug.getName());
        drug.setType(dbDrug.getType());
        drug.setDosesEveryH(dbDrug.getDosesEveryH());
        drug.setDosesNo(dbDrug.getDosesNo());

        //drug.setStartDate(parse(dbDrug.getStartDate()));
        //drug.setEndDate(parse(dbDrug.getEndDate()));
        drug.setStartDate(dbDrug.getStartDate());
        drug.setEndDate(dbDrug.getEndDate());

        drug.setStartDateEnable(dbDrug.isStartDateEnable());
        drug.setEndDateEnable(dbDrug.isEndDateEnable());

        return drug;
    }

    public static DbDrugEntity toDbDrug(Drug drug) {
        if (drug == null) {
            throw new IllegalArgumentException("Drug cannot be NULL!");
        }

        DbDrugEntity dbDrugEntity = new DbDrugEntity();

        dbDrugEntity.setName(drug.getName());
        dbDrugEntity.setType(drug.getType());
        dbDrugEntity.setDosesNo(drug.getDosesNo());
        dbDrugEntity.setDosesEveryH(drug.getDosesEveryH());

        dbDrugEntity.setStartDateEnable(drug.isStartDateEnable());
        dbDrugEntity.setEndDateEnable(drug.isEndDateEnable());

//        dbDrugEntity.setStartDate(parse(drug.getStartDate()));
//        dbDrugEntity.setEndDate(parse(drug.getEndDate()));

        dbDrugEntity.setStartDate((drug.getStartDate()));
        dbDrugEntity.setEndDate((drug.getEndDate()));

        return dbDrugEntity;
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
