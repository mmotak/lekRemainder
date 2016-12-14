package pl.com.mmotak.lekremainder.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;

/**
 * Created by mmotak on 14.12.2016.
 */

public class MyNumberDialog {

    public static void show(Context context, Integer number, @NonNull IDateUIProvider.IResult<Integer> iResult) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setCancelable(false);
        builder.setTitle("Go to page number...");
        builder.setMessage("Enter page number:");

        // Set an EditText view to get user input
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        if (number == null) {
            input.setText("" + 1);
        } else {
            input.setText(number.toString());
        }

        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String msg = input.getText().toString();
                Integer integer = 1;
                try {
                    integer = Integer.parseInt(msg);
                } catch (NumberFormatException e) {

                }
                iResult.onSuccess(integer);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                iResult.onFail();
            }
        });

        builder.create().show();
    }
}
