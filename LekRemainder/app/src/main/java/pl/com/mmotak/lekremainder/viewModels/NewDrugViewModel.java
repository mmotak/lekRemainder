package pl.com.mmotak.lekremainder.viewModels;

import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import pl.com.mmotak.lekremainder.models.Drug;

/**
 * Created by mmotak on 28.11.2016.
 */

public class NewDrugViewModel {

    public ObservableField<Drug> drugObservable;

    public ObservableField<String> name;
    public ObservableField<String> type;

    public ObservableBoolean enableButton;

    private Context context;

    public NewDrugViewModel(Context context) {
        this.context = context;
        drugObservable = new ObservableField<>(new Drug());

        enableButton = new ObservableBoolean(false);

        name = new ObservableField<>("");
        name.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override public void onPropertyChanged(Observable observable, int i) {
                enableSaveButton();
            }
        });

        type = new ObservableField<>("");
        type.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
        @Override public void onPropertyChanged(Observable observable, int i) {
            enableSaveButton();
        }
    });
    }

    public void onSaveClick(View v) {
        String x = name.get();
        String y = type.get();
        Toast.makeText(context, name.get() + " " + type.get(), Toast.LENGTH_LONG).show();
    }

    private void enableSaveButton() {
        enableButton.set(name.get().length() > 0 && type.get().length() > 0);
    }
}
