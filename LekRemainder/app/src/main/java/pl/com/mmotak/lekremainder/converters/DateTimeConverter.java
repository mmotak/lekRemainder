package pl.com.mmotak.lekremainder.converters;

import org.joda.time.DateTime;

import io.requery.Converter;
import io.requery.Nullable;

/**
 * Created by mmotak on 07.12.2016.
 */

public class DateTimeConverter implements Converter<DateTime, Long> {

    @Override
    public Class<DateTime> getMappedType() {
        return DateTime.class;
    }

    @Override
    public Class<Long> getPersistedType() {
        return Long.class;
    }

    @Nullable
    @Override
    public Integer getPersistedSize() {
        return null;
    }

    @Override
    public Long convertToPersisted(DateTime value) {
        return value != null ? value.getMillis() : null;
    }

    @Override
    public DateTime convertToMapped(Class<? extends DateTime> type, Long value) {
        return value != null ? new DateTime(value) : null;
    }
}
