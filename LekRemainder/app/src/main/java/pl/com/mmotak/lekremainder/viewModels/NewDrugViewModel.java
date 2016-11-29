package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.models.Drug;

/**
 * Created by mmotak on 28.11.2016.
 */

public class NewDrugViewModel extends AbstractBaseViewModel {

    @Inject
    IDataProvider dataProvider;

    public ObservableField<String> name;
    public ObservableField<String> type;

    public ObservableBoolean enableButton;

    public NewDrugViewModel(Activity baseActivity) {
        super(baseActivity);
        getDiComponent().inject(this);
        clearFields();
        setUpProprety();
    }

    public void onSaveClick(View v) {
        dataProvider.addNewDrug(new Drug(name.get(), type.get()));
        Toast.makeText(getBaseActivity(), name.get() + " " + type.get(), Toast.LENGTH_LONG).show();
        getBaseActivity().finish();
    }

    private void clearFields() {
        enableButton = new ObservableBoolean(false);
        name = new ObservableField<>("");
        type = new ObservableField<>("");
    }

    private void setUpProprety() {
        name.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override public void onPropertyChanged(Observable observable, int i) {
                enableSaveButton();
            }
        });

        type.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override public void onPropertyChanged(Observable observable, int i) {
                enableSaveButton();
            }
        });
    }

    private void enableSaveButton() {
        enableButton.set(name.get().length() > 0 && type.get().length() > 0);
    }
}
