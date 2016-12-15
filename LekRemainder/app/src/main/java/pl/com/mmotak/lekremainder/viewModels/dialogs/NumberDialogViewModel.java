package pl.com.mmotak.lekremainder.viewModels.dialogs;

import android.databinding.Observable;
import android.databinding.ObservableInt;

/**
 * Created by mmotak on 15.12.2016.
 */

public class NumberDialogViewModel {

    public ObservableInt number = new ObservableInt(1);
    public ObservableInt progress = new ObservableInt(2);

    public NumberDialogViewModel() {

        progress.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                number.set(progress.get() + 1);
            }
        });
    }

    public void setStartValue(Integer startValue) {
        if (startValue != null) {
            progress.set(startValue - 1); // minus one because we add 1 to number
            progress.notifyChange();
        }
    }
}
