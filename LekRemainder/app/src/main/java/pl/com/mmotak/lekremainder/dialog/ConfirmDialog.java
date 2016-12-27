package pl.com.mmotak.lekremainder.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;

import org.jetbrains.annotations.NotNull;

import pl.com.mmotak.lekremainder.R;

/**
 * Created by Maciej on 2016-12-27.
 */

public class ConfirmDialog {

    public static void show(Activity activity, String title, String message, @NotNull IDialogResult<Boolean> dialogResultListener) {
        createDialog(activity, title, message, dialogResultListener).show();
    }


    private static Dialog createDialog(Activity activity, String title, String message, @NotNull IDialogResult<Boolean> dialogResultListener) {
        return new AlertDialog
                .Builder(activity)
                .setTitle(title != null && title.length() > 0 ? title : "Title")
                .setMessage(message)
                .setPositiveButton(R.string.dialog_yes,
                        (dialogInterface, i) -> dialogResultListener.onSuccess(true))
                .setNegativeButton(R.string.dialog_no,
                        (dialogInterface, i) -> dialogResultListener.onFail())
                .create();
    }
}
