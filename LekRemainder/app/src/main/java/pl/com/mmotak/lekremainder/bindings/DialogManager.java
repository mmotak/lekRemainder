package pl.com.mmotak.lekremainder.bindings;

import android.database.Observable;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.Toast;

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
            @Override public View.OnClickListener create(View view, final IDialogData data) {
                return new View.OnClickListener() {
                    @Override public void onClick(View view) {

                        String msg = data == null ? view.toString() :
                                data.load() == null ? view.toString() : data.load().toString();

                        Toast.makeText(view.getContext(), msg, Toast.LENGTH_LONG)
                                .show();
                    }
                };
            }
        };
    }

//    public static Factory dateTimePicker() {
//        return new Factory() {
//            @Override
//            public View.OnClickListener create(View view, IDialogData data) {
//                return null;
//            }
//        }
//    }
}
