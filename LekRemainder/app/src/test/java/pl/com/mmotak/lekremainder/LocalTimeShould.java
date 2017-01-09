package pl.com.mmotak.lekremainder;

import org.joda.time.LocalTime;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by Maciej on 2017-01-07.
 */

public class LocalTimeShould {

    @Test
    public void moveLocalTime() {
        LocalTime l = new LocalTime(22,00);
        l = l.plusSeconds((2 * 60 * 60) + (7 * 60)); // 2h * 60 m * 60 s + 7m * 60s
        assertThat(l.getHourOfDay() == 0);
        assertThat(l.getMinuteOfHour() == 7);
    }
}
