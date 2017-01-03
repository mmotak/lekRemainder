package pl.com.mmotak.lekremainder.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import org.joda.time.LocalTime;

/**
 * Created by mmotak on 15.12.2016.
 */

public class LocalTimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = LocalTimeDialog.class.getSimpleName();
    private IDialogResult<LocalTime> listener;
    private LocalTime time;

    public static void show(FragmentActivity activity, LocalTime time, @NonNull IDialogResult<LocalTime> dialogResult) {
        LocalTimeDialog dialog = new LocalTimeDialog();
        dialog.setListener(dialogResult);
        dialog.setTime(time);

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        dialog.show(fragmentManager, TAG);
    }

    public LocalTimeDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getContext(), this, time.getHourOfDay(), time.getMinuteOfHour(), DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        listener.onSuccess(new LocalTime(hourOfDay, minute));
    }

    public void setListener(IDialogResult<LocalTime> listener) {
        this.listener = listener;
    }

    public void setTime(LocalTime time) {
        this.time = time == null ? LocalTime.now() : time;
    }
}
