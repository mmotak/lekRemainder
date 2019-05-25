package pl.com.mmotak.lekremainder.bindings;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import pl.com.mmotak.lekremainder.dialog.ContextHelper;
import pl.com.mmotak.lekremainder.dialog.DateTimeDialog;
import pl.com.mmotak.lekremainder.dialog.ADialogSuccessResult;
import pl.com.mmotak.lekremainder.dialog.LocalTimeDialog;
import pl.com.mmotak.lekremainder.dialog.NumberSeekDialog;

/**
 * Created by mmotak on 02.12.2016.
 */

public class DialogManager {

    public interface IDialogData {
        Object load();

        void save(Object o);
    }

    protected DialogManager() {
    }

    public interface Factory {
        View.OnClickListener create(View view, IDialogData data);
    }

    private static FragmentActivity getFragmentActivity(View view) {
        return (FragmentActivity) ContextHelper.getActivity(view.getContext());
    }

    public static Factory toast() {
        return (inputView, inputData) -> (View.OnClickListener) view -> {
            String msg = inputData == null ? view.toString() : inputData.load() == null ? view.toString() : inputData.load().toString();
            Toast.makeText(view.getContext(), msg, Toast.LENGTH_LONG).show();
        };
    }

    public static Factory dateTimePicker() {
        return (inputView, data) -> (View.OnClickListener) view -> {
            DateTimeDialog.show(getFragmentActivity(view), data == null ? null : (DateTime) data.load(),
                    new ADialogSuccessResult<DateTime>() {
                        @Override
                        public void onSuccess(DateTime dateTime) {
                            if (data != null) {
                                data.save(dateTime);
                            }
                        }
                    });
        };
    }


    public static Factory timeDialog() {
        return (v, data) -> view -> {
            LocalTimeDialog.show(getFragmentActivity(view), data == null ? null : (LocalTime) data.load(), new ADialogSuccessResult<LocalTime>() {
                @Override
                public void onSuccess(LocalTime time) {
                    if (data != null) {
                        data.save(time);
                    }
                }
            });
        };
    }

    public static Factory numberSeekDialog(int max) {
        return (inputView, data) -> (View.OnClickListener) view -> {
            Integer number = null;
            String title = null;

            if (data == null) {
                if (view instanceof TextView) {
                    String text = ((TextView) view).getText().toString();
                    title = ((TextView) view).getHint().toString();
                    number = Integer.parseInt(text);
                }
            } else {
                number = (Integer) data.load();
            }

            NumberSeekDialog.show(getFragmentActivity(view), number, max, title, new ADialogSuccessResult<Integer>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSuccess(Integer number) {
                    if (data != null) {
                        data.save(number);
                    } else if (view instanceof TextView) {
                        ((TextView) view).setText(number.toString());
                    }
                }
            });
        };
    }
}
