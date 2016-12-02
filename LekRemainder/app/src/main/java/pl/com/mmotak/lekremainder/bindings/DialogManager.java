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

    protected DialogManager() {
    }

    public interface Factory {
        View.OnClickListener create(View view, BaseObservable result);
    }

    public static Factory toast() {
        return new Factory() {
            @Override public View.OnClickListener create(View view, BaseObservable result) {
                return new View.OnClickListener() {
                    @Override public void onClick(View view) {

                        if (result != null &&  result instanceof ObservableField) {
                            ObservableField<String> x = (ObservableField<String>) result;
                            x.set(view.toString());
                        }

                        Toast.makeText(view.getContext(), view.toString(), Toast.LENGTH_LONG)
                                .show();
                    }
                };
            }
        };
    }
}
