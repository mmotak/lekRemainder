package pl.com.mmotak.lekremainder.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.annotation.NonNull;

import pl.com.mmotak.lekremainder.R;

/**
 * Created by Maciej on 2016-12-27.
 */

public class ConfirmDialog {

    public static void show(Context context, String title, String message, @NonNull IDialogResult<Boolean> dialogResultListener) {
        createDialog(context, title, message, dialogResultListener).show();
    }

    private static Dialog createDialog(Context context, String title, String message, @NonNull IDialogResult<Boolean> dialogResultListener) {
        return new AlertDialog
                .Builder(context)
                .setTitle(title != null && title.length() > 0 ? title : "Title")
                .setMessage(message)
                .setPositiveButton(R.string.dialog_yes,
                        (dialogInterface, i) -> dialogResultListener.onSuccess(true))
                .setNegativeButton(R.string.dialog_no,
                        (dialogInterface, i) -> dialogResultListener.onFail())
                .create();
    }
}
