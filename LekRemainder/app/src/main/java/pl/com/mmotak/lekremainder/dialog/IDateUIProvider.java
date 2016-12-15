package pl.com.mmotak.lekremainder.dialog;

import android.app.Activity;

import org.joda.time.DateTime;

/**
 * Created by mmotak on 02.12.2016.
 */

public interface IDateUIProvider {

    void showDialog(Activity activity, DateTime endDate, IDialogResult<DateTime> iResult);

}
