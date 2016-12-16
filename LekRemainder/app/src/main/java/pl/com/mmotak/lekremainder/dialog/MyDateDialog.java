package pl.com.mmotak.lekremainder.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.DatePicker;

import org.joda.time.DateTime;

/**
 * Created by mmotak on 30.11.2016.
 */

public class MyDateDialog extends DialogFragment {

    private DateTime date;
    private OnMyDateDialogResult listener;

    public MyDateDialog() {
    }

    public void setListener(OnMyDateDialogResult listener) {
        this.listener = listener;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (listener == null) {
            throw new IllegalArgumentException(
                    "OnMyDateDialogResult in NULL! Please set it by MyDateDialog.setListener before call show.");
        }

        DateTime now = DateTime.now();
        int year = date != null ? date.getYear() : now.getYear(),
                month = date != null ? date.getMonthOfYear() - 1 : now.getMonthOfYear() - 1,
                day = date != null ? date.getDayOfMonth() : now.getDayOfMonth();

        final DatePickerDialog dialog = new DatePickerDialog(getActivity(), null, year, month, day);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE,
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        DatePicker datePicker = dialog.getDatePicker();
                        //Date date = new Date(year,month,day);

                        DateTime result = new DateTime(datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth(),
                                0,
                                0);
                        listener.onSuccess(result);
                    }
                });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onFail();
                    }
                });

        return dialog;
    }

    interface OnMyDateDialogResult {

        void onSuccess(DateTime result);

        void onFail();
    }
}
