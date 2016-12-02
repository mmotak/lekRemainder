package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.Toast;

import org.joda.time.DateTime;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.dialog.IDateUIProvider;
import pl.com.mmotak.lekremainder.models.Drug;

/**
 * Created by mmotak on 28.11.2016.
 */

public class NewDrugViewModel extends AbstractBaseViewModel {

    @Inject IDataProvider dataProvider;
    @Inject IDateUIProvider dateUIProvider;

    public ObservableField<String> name;
    public ObservableField<String> type;

    public ObservableField<String> startDate;
    public ObservableField<String> endDate;

    public ObservableBoolean startDateEnable;
    public ObservableBoolean endDateEnable;

    public ObservableBoolean enableButton;

    private Drug drug = new Drug();
    private String dateTimeFormat;

    public NewDrugViewModel(Activity baseActivity) {
        super(baseActivity);
        getDiComponent().inject(this);
        clearFields();
        setUpProperty();
    }

    public void onSaveClick(View v) {
        dataProvider.addNewDrug(drug);
        Toast.makeText(getBaseActivity(), name.get() + " " + type.get(), Toast.LENGTH_LONG).show();
        getBaseActivity().finish();
    }

    public void startDateCheckClick(View view) {
        if (startDateEnable.get()) {
            startDateClick(view);
        } else {
            drug.enableStartDate(false);
        }
    }

    public void startDateClick(View view) {
        if (startDateEnable.get()) {
            dateUIProvider.showDialog(getBaseActivity(),
                    drug.getStartDate(),
                    new IDateUIProvider.IResult() {

                        @Override public void onSuccess(DateTime dateTime) {
                            drug.setStartDate(dateTime);
                            drug.enableStartDate(true);
                            startDate.set(getFormattedDate(dateTime));
                            startDateEnable.set(true);
                        }

                        @Override public void onFail() {
                            startDateEnable.set(false);
                            drug.enableStartDate(false);
                        }
                    });
        }
    }

    public void endDateCheckClick(View view) {
        if (endDateEnable.get()) {
            endDateClick(view);
        } else {
            drug.enableEndDate(false);
        }
    }

    public void endDateClick(View view) {
        if (endDateEnable.get()) {
            dateUIProvider.showDialog(getBaseActivity(),
                    drug.getEndDate(),
                    new IDateUIProvider.IResult() {

                        @Override public void onSuccess(DateTime dateTime) {
                            drug.setEndDate(dateTime);
                            drug.enableEndDate(true);
                            endDate.set(getFormattedDate(dateTime));
                            endDateEnable.set(true);
                        }

                        @Override public void onFail() {
                            endDateEnable.set(false);
                            drug.enableEndDate(false);
                        }
                    });
        }
    }

    private String getFormattedDate(DateTime dateTime) {
        return dateTime == null ? "" : dateTime.toString(dateTimeFormat);
    }

    private void clearFields() {

        enableButton = new ObservableBoolean(false);
        name = new ObservableField<>("");
        type = new ObservableField<>("");

        startDateEnable = new ObservableBoolean(false);
        endDateEnable = new ObservableBoolean(false);

        startDate = new ObservableField<>(getFormattedDate(drug.getStartDate()));
        endDate = new ObservableField<>(getFormattedDate(drug.getEndDate()));
    }

    private void setUpProperty() {
        dateTimeFormat = getBaseActivity().getString(R.string.date_format);

        name.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override public void onPropertyChanged(Observable observable, int i) {
                drug.name = name.get();
                enableSaveButton();
            }
        });

        type.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override public void onPropertyChanged(Observable observable, int i) {
                drug.type = type.get();
                enableSaveButton();
            }
        });
    }

    private void enableSaveButton() {
        enableButton.set(name.get().length() > 0 && type.get().length() > 0);
    }
}
