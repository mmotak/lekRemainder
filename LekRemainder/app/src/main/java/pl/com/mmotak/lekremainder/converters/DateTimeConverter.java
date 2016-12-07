package pl.com.mmotak.lekremainder.converters;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.annotation.Annotation;

import io.requery.Convert;
import io.requery.Converter;
import io.requery.Nullable;

/**
 * Created by mmotak on 07.12.2016.
 */

public class DateTimeConverter implements Converter<DateTime,String> {

    private static String DbDateTimeFormat = "MMddyyyy";
    private static org.joda.time.format.DateTimeFormatter DateTimeFormatter = DateTimeFormat.forPattern(DbDateTimeFormat);

    @Override
    public Class<DateTime> getMappedType() {
        return DateTime.class;
    }

    @Override
    public Class<String> getPersistedType() {
        return String.class;
    }

    @Nullable
    @Override
    public Integer getPersistedSize() {
        return null;
    }

    @Override
    public String convertToPersisted(DateTime value) {
        return parse(value);
    }

    @Override
    public DateTime convertToMapped(Class<? extends DateTime> type, String value) {
        return parse(value);
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
