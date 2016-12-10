package pl.com.mmotak.lekremainder.models;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmotak on 08.12.2016.
 */

public class DosesTimes {

    private List<LocalTime> times;

    private DosesTimes() {
        times = new ArrayList<>(6);
    }

    public static DosesTimes Build(LocalTime startTakingTime, int dosesNo, int dosesEveryH) {

        if (dosesNo == 1) dosesEveryH = 1;
        if (dosesEveryH == 0) dosesEveryH = 12 / (dosesNo - 1);

        DosesTimes dosesTimes = new DosesTimes();
        LocalTime tempTime = new LocalTime(startTakingTime);

        for (int i = 0; i < dosesNo; i++) {
            dosesTimes.add(tempTime.plusHours(i * dosesEveryH));
        }

        return dosesTimes;
    }

    private void add(LocalTime localTime) {
        times.add(localTime);
    }

    public List<LocalTime> getList() {
        return times;
    }
}
