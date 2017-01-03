package pl.com.mmotak.lekremainder.bindings;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import pl.com.mmotak.lekremainder.dialog.ContextHelper;
import pl.com.mmotak.lekremainder.dialog.DateTimeDialog;
import pl.com.mmotak.lekremainder.dialog.IDialogResult;
import pl.com.mmotak.lekremainder.dialog.NumberSeekDialog;
import pl.com.mmotak.lekremainder.dialog.LocalTimeDialog;

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

    public static Factory toast() {
        return new Factory() {
            @Override
            public View.OnClickListener create(View view, final IDialogData data) {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String msg = data == null ? view.toString() :
                                data.load() == null ? view.toString() : data.load().toString();

                        Toast.makeText(view.getContext(), msg, Toast.LENGTH_LONG)
                                .show();
                    }
                };
            }
        };
    }

    public static Factory dateTimePicker() {
        return new Factory() {
            @Override
            public View.OnClickListener create(View view, IDialogData data) {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DateTimeDialog.show((FragmentActivity) ContextHelper.getActivity(view.getContext()), data == null ? null : (DateTime) data.load(), new IDialogResult<DateTime>() {
                            @Override
                            public void onSuccess(DateTime dateTime) {
                                if (data != null) {
                                    data.save(dateTime);
                                }
                            }

                            @Override
                            public void onFail() {

                            }
                        });
                    }
                };
            }
        };
    }

    public static Factory timeDialog() {
        return (v, data) -> view -> {

            LocalTimeDialog.show((FragmentActivity) ContextHelper.getActivity(view.getContext()),  data == null ? null : (LocalTime) data.load(), new IDialogResult<LocalTime>() {
                @Override
                public void onSuccess(LocalTime time) {
                    if (data != null) { data.save(time); }
                }

                @Override
                public void onFail() {

                }
            });
        };
    }

    public static Factory numberSeekDialog(int max) {
        return new Factory() {
            @Override
            public View.OnClickListener create(View view, IDialogData data) {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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


                        NumberSeekDialog.show((FragmentActivity) ContextHelper.getActivity(view.getContext()), number, max, title, new IDialogResult<Integer>() {
                            @Override
                            public void onSuccess(Integer number) {
                                if (data != null) {
                                    data.save(number);
                                } else if (view instanceof TextView) {
                                    ((TextView) view).setText(number.toString());
                                }
                            }

                            @Override
                            public void onFail() {

                            }
                        });
                    }
                };
            }
        };
    }
}
