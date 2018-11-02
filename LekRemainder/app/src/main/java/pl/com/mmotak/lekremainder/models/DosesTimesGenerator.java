package pl.com.mmotak.lekremainder.models;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmotak on 08.12.2016.
 */

public class DosesTimesGenerator {

    public static List<ShiftedLocalTime> generate(LocalTime time, int dosesNo, int dosesEveryH) {
        List<ShiftedLocalTime> list = new ArrayList<>();

        if (time == null) {
            time = new LocalTime(8, 0);
        }

        int hours = time.getHourOfDay() - dosesEveryH;
        for (int i = 0; i < dosesNo; i++) {
            hours += dosesEveryH;
            list.add(new ShiftedLocalTime(time.plusHours(i * dosesEveryH), hours / 24));
        }

        return list;
    }

    public static String generateString(List<Dose> list) {
        String timeFormat = "HH:mm";

        StringBuilder sb = new StringBuilder();

        for (Dose dose : list) {
            sb.append(" ").append(dose.getTime().toString(timeFormat));
        }

        return sb.toString();
    }
}
