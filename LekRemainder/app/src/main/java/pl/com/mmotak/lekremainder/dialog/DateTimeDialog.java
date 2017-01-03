package pl.com.mmotak.lekremainder.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;

import org.joda.time.DateTime;


/**
 * Created by mmotak on 30.11.2016.
 */

public class DateTimeDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = DateTimeDialog.class.getSimpleName();

    private DateTime date;
    private IDialogResult<DateTime> listener;

    public static void show(FragmentActivity activity, DateTime dateTime, @NonNull IDialogResult<DateTime> dialogResult) {
        DateTimeDialog dialog = new DateTimeDialog();
        dialog.setListener(dialogResult);
        dialog.setDateTime(dateTime);

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        dialog.show(fragmentManager, TAG);
    }

    public DateTimeDialog() {
    }

    public void setListener(IDialogResult<DateTime> listener) {
        this.listener = listener;
    }

    public void setDateTime(DateTime date) {
        this.date = date;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DateTime now = DateTime.now();
        int year = date != null ? date.getYear() : now.getYear(),
                month = date != null ? date.getMonthOfYear() - 1 : now.getMonthOfYear() - 1,
                day = date != null ? date.getDayOfMonth() : now.getDayOfMonth();

        return new DatePickerDialog(getContext(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        DateTime result = new DateTime(datePicker.getYear(),
                datePicker.getMonth() + 1,
                datePicker.getDayOfMonth(),
                0,
                0);
        listener.onSuccess(result);
    }
}
