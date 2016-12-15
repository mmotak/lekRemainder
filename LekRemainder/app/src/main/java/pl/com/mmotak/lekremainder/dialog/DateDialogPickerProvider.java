package pl.com.mmotak.lekremainder.dialog;

import android.app.Activity;
import android.app.FragmentManager;

import org.joda.time.DateTime;

/**
 * Created by mmotak on 02.12.2016.
 */

public class DateDialogPickerProvider implements IDateUIProvider {

    private static final String TAG = "MyDatePickerFragment";

    @Override public void showDialog(Activity activity, DateTime dateTime, IDialogResult<DateTime> iResult) {
        MyDateDialog dialog = new MyDateDialog();
        dialog.setDate(dateTime);
        dialog.setListener(new MyDateDialog.OnMyDateDialogResult() {
            @Override public void onSuccess(DateTime result) {
                iResult.onSuccess(result);
            }

            @Override public void onFail() {
                iResult.onFail();
            }
        });

        FragmentManager fm = activity.getFragmentManager();
        dialog.show(fm, DateDialogPickerProvider.TAG);
    }
}
