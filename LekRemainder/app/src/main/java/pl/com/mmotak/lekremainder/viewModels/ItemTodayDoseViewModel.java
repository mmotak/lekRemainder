package pl.com.mmotak.lekremainder.viewModels;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.dialog.ConfirmDialog;
import pl.com.mmotak.lekremainder.dialog.IDialogResult;
import pl.com.mmotak.lekremainder.models.TodayDose;

/**
 * Created by mmotak on 16.12.2016.
 */

public class ItemTodayDoseViewModel implements IDialogResult<Boolean> {

    private Context context;
    private IDataProvider dataProvider;
    private final TodayDose todayDose;

    public ItemTodayDoseViewModel(Context context, TodayDose todayDose, IDataProvider dataProvider) {
        this.context = context;
        this.dataProvider = dataProvider;
        this.todayDose = todayDose;
    }

    public void onItemClick(View view) {
        if (!todayDose.wasTaken()) {
            Context context = view.getContext();

            ConfirmDialog.show(context,
                    context.getString(R.string.ask_take_drug_title),
                    context.getString(R.string.ask_take_drug, getName()),
                    this);
        }
    }

    public String getName() {
        return todayDose.getDrugName();
    }

    public LocalTime getTime() {
        return todayDose.getTime();
    }

    public int getImageColor() {
        int color = R.color.colorPrimary;
        return color;
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

    @Override
    public void onSuccess(Boolean data) {
        if (!todayDose.wasTaken()) {
            DateTime now = DateTime.now(); // use LocalDateTime
            todayDose.setTaken(now);

            dataProvider.saveHistory(todayDose.getDrugName(), todayDose.getId(), now);
            dataProvider.updateTodayDose(todayDose);
        }
    }

    @Override
    public void onFail() {

    }
}
