package pl.com.mmotak.lekremainder.viewModels;

import android.content.Context;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.alarms.TodayDoseResetAlarmManager;
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
    private final int imageId;
    private final int imageColorId;

    public ItemTodayDoseViewModel(Context context, TodayDose todayDose, IDataProvider dataProvider) {
        this.context = context;
        this.dataProvider = dataProvider;
        this.todayDose = todayDose;

        if (todayDose.wasTaken()) {
            imageId = R.drawable.accept_black;
            imageColorId = R.color.drug_was_taken;
        } else {
            imageId = R.drawable.clock_black;
            DateTime now = DateTime.now();
            DateTime estimated = todayDose.getEstimatedDateTime();
            if (now.getMillis() < estimated.getMillis()) {
                imageColorId = R.color.drug_was_not_taken;
            } else {
                imageColorId = R.color.drug_was_not_taken_on_time;
            }
        }
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

    public int getImage() {
        return imageId;
    }

    public int getImageColor() {
        return imageColorId;
    }

    public String getEstimatedTime() {
        String format = context.getString(R.string.time_format);

        DateTime estimated = todayDose.getEstimatedDateTime();
        return estimated.toString(format);
    }

    @Override
    public void onSuccess(Boolean data) {
        if (!todayDose.wasTaken()) {
            DateTime now = DateTime.now(); // use LocalDateTime
            todayDose.setTaken(now);

            dataProvider.saveHistory(todayDose.getDrugName(), todayDose.getId(), now);
            dataProvider.updateTodayDose(todayDose);

            TodayDoseResetAlarmManager.setNextAlarmNextDoseAlarmService(context, DateTime.now().plusMinutes(1));
        }
    }

    @Override
    public void onFail() {

    }
}
