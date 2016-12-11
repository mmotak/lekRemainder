package pl.com.mmotak.lekremainder.converters;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import io.requery.Converter;
import io.requery.Nullable;

/**
 * Created by mmotak on 08.12.2016.
 */

public class LocalTimeConverter implements Converter<LocalTime, String> {

    private static String DbLocalTimeFormat = "HHmmss";
    private static org.joda.time.format.DateTimeFormatter LocalTimeFormatter = DateTimeFormat.forPattern(DbLocalTimeFormat);


    @Override
    public Class<LocalTime> getMappedType() {
        return LocalTime.class;
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
    public String convertToPersisted(LocalTime value) {
        return parse(value);
    }

    @Override
    public LocalTime convertToMapped(Class<? extends LocalTime> type, String value) {
        return parse(value);
    }

    private static String parse(LocalTime localTime) {
        return localTime == null ? null : localTime.toString(DbLocalTimeFormat);
    }

    private static LocalTime parse(String localTime) {
        if (localTime == null || localTime.length() == 0) {
            return null;
        }
        return LocalTimeFormatter.parseLocalTime(localTime);
    }
}
