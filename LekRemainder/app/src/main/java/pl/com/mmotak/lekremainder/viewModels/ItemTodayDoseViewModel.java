package pl.com.mmotak.lekremainder.viewModels;

import org.joda.time.LocalTime;

import pl.com.mmotak.lekremainder.models.TodayDose;

/**
 * Created by mmotak on 16.12.2016.
 */

public class ItemTodayDoseViewModel {

    private final TodayDose todayDose;

    public ItemTodayDoseViewModel(TodayDose todayDose) {
        this.todayDose = todayDose;
    }

    public String getName() {
    return todayDose.getDrugName();
    }

    public LocalTime getTime() {
        return todayDose.getTime();
    }

    public String getEstimatedTime() {
        return "??";
    }
}
