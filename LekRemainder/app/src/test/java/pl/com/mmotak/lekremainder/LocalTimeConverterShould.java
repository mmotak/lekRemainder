package pl.com.mmotak.lekremainder;

import org.joda.time.LocalTime;
import org.junit.Test;

import pl.com.mmotak.lekremainder.converters.LocalTimeConverter;
import static org.assertj.core.api.Assertions.*;


/**
 * Created by mmotak on 08.12.2016.
 */

public class LocalTimeConverterShould {

    private LocalTimeConverter converter = new LocalTimeConverter();

    @Test
    public void parseCorrectToSting() {
        LocalTime localTime = new LocalTime(12,16,32);
        String string = converter.convertToPersisted(localTime);
        assertThat(string).isEqualTo("121632");
    }

    @Test
    public void parseCorrectToLocalTime() {
        String string = "121632";
        LocalTime localTime = converter.convertToMapped(converter.getMappedType(),string);
        assertThat(localTime.getHourOfDay()).isEqualTo(12);
        assertThat(localTime.getMinuteOfHour()).isEqualTo(16);
        assertThat(localTime.getSecondOfMinute()).isEqualTo(32);
    }
}
