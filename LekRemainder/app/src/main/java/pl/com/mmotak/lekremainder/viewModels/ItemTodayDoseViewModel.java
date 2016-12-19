package pl.com.mmotak.lekremainder.viewModels;

import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.models.TodayDose;

/**
 * Created by mmotak on 16.12.2016.
 */

public class ItemTodayDoseViewModel {

    private IDataProvider dataProvider;
    private final TodayDose todayDose;

    public ItemTodayDoseViewModel(TodayDose todayDose, IDataProvider dataProvider) {
        this.dataProvider = dataProvider;
        this.todayDose = todayDose;
    }

    public void onItemClick(View view) {
        if (!todayDose.wasTaken()) {
            DateTime now = DateTime.now(); // use LocalDateTime
            todayDose.setTaken(now);

            dataProvider.saveHistory(todayDose.getDrugName(), todayDose.getId(), now);
            dataProvider.updateTodayDose(todayDose);
        }
    }

    public String getName() {
        return todayDose.getDrugName();
    }

    public LocalTime getTime() {
        return todayDose.getTime();
    }

    public String getEstimatedTime() {

        if (todayDose.wasTaken()) {
            // show taken time
            return todayDose.getTakenTime().toLocalTime().toString();
        } else {
            // show estimated time
            int shift = todayDose.getShift();
            LocalTime time = todayDose.getTime();
            return time.plusSeconds(shift).toString();
        }
    }
}
