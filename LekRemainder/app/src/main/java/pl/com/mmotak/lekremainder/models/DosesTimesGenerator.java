package pl.com.mmotak.lekremainder.models;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmotak on 08.12.2016.
 */

public class DosesTimesGenerator {

    public static List<LocalTime> generate(LocalTime time, int dosesNo, int dosesEveryH) {
        List<LocalTime> list = new ArrayList<>();

        if (time == null) {
            time = new LocalTime(8,0);
        }

        for (int i = 0; i < dosesNo; i++) {
            list.add(time.plusHours(i * dosesEveryH));
        }

        return list;
    }
}
