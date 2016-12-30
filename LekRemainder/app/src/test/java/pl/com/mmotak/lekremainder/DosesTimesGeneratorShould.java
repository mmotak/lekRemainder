package pl.com.mmotak.lekremainder;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.joda.time.LocalTime;
import org.junit.Test;

import java.util.List;

import pl.com.mmotak.lekremainder.models.DosesTimesGenerator;
import pl.com.mmotak.lekremainder.models.ShiftedLocalTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by Maciej on 2016-12-30.
 */

public class DosesTimesGeneratorShould {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Test
    public void generateItems() {
        List<ShiftedLocalTime> list = DosesTimesGenerator.generate(new LocalTime(8, 0), 8, 4);
        assertThat(list).isNotEmpty();

        assertThat(list.stream()
                .filter(shiftedLocalTime -> shiftedLocalTime.getShiftInDays() == 1)
                .count()).isEqualTo(4);
    }
}
