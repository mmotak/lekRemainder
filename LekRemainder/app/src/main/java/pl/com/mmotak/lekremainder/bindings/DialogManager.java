package pl.com.mmotak.lekremainder.bindings;

import android.app.Activity;
import android.database.Observable;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.dialog.DateDialogPickerProvider;
import pl.com.mmotak.lekremainder.dialog.IDateUIProvider;
import pl.com.mmotak.lekremainder.dialog.MyNumberDialog;

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
                        DateDialogPickerProvider p = new DateDialogPickerProvider();

                        DateTime dt = null;

                        if (data != null) {
                            dt = (DateTime) data.load();
                        }

                        p.showDialog((Activity) view.getContext(), dt, new IDateUIProvider.IResult<DateTime>() {
                            @SuppressWarnings("unchecked")
                            @Override
                            public void onSuccess(DateTime dateTime) {
                                if (data != null) {
                                    data.save(dateTime);
                                }
                            }

                            @Override
                            public void onFail() {
                                if (data != null) {
                                    data.save(null);
                                }
                            }
                        });
                    }
                };
            }
        };
    }

    public static Factory numberDialog() {
        return new Factory() {
            @Override
            public View.OnClickListener create(View view, IDialogData data) {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer no = null;

                        if (data == null) {
                            if (view instanceof TextView) {
                                String text = ((TextView) view).getText().toString();
                                no = Integer.parseInt(text);
                            }
                        } else {
                            no = (Integer) data.load();
                        }


                        MyNumberDialog.show(view.getContext(), no, new IDateUIProvider.IResult<Integer>() {
                            @Override
                            public void onSuccess(Integer number) {
                                if (view instanceof TextView) {
                                    ((TextView) view).setText(number.toString());
                                }
                                if (data != null) {
                                    data.save(number);
                                }
                            }

                            @Override
                            public void onFail() {
                                if (data != null) {
                                    data.notify();
                                }
                            }
                        });
                    }
                };
            }
        };
    }
}
