package pl.com.mmotak.lekremainder;

import org.joda.time.DateTime;
import org.junit.Test;

import pl.com.mmotak.lekremainder.converters.DateTimeConverter;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by mmotak on 08.12.2016.
 */

public class DateTimeConverterShould {

    private DateTimeConverter converter = new DateTimeConverter();

    @Test
    public void convertToMillis() {
        DateTime dateTime = new DateTime(2017,11,28, 0, 0);
        Long millis = dateTime.getMillis();
        Long parsed = converter.convertToPersisted(dateTime);
        assertThat(parsed).isEqualTo(millis);
    }

    @Test
    public void parseCorrectToDateTime() {
        Long millis = 1511827200000L;
        DateTime dateTime = converter.convertToMapped(converter.getMappedType(),millis);
        assertThat(dateTime.getYear()).isEqualTo(2017);
        assertThat(dateTime.getMonthOfYear()).isEqualTo(11);
        assertThat(dateTime.getDayOfMonth()).isEqualTo(28);
    }
}
