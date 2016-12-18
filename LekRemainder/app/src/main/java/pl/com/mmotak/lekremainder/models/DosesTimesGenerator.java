package pl.com.mmotak.lekremainder.models;

import android.content.Context;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import pl.com.mmotak.lekremainder.R;

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

    public static String generateString(List<Dose> list) {
        //String timeFormat = context.getString(R.string.time_format);
        String timeFormat = "HH:mm";

        StringBuilder sb = new StringBuilder();

        for (Dose dose: list) {
            sb.append(" ").append(dose.getTime().toString(timeFormat));
        }

        return sb.toString();
    }
}
