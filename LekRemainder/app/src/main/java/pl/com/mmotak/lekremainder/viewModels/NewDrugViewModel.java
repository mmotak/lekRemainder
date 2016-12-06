package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import android.widget.Toast;

import org.joda.time.DateTime;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.dialog.IDateUIProvider;
import pl.com.mmotak.lekremainder.models.DrugOld;

/**
 * Created by mmotak on 28.11.2016.
 */

public class NewDrugViewModel extends AbstractBaseViewModel {

    @Inject IDataProvider dataProvider;
    @Inject IDateUIProvider dateUIProvider;

    public ObservableField<String> name;
    public ObservableField<String> type;

    public ObservableInt doses;
    public ObservableInt dosesEveryH;

    public ObservableField<String> startDate;
    public ObservableField<String> endDate;

    public ObservableBoolean startDateEnable;
    public ObservableBoolean endDateEnable;

    public ObservableField<String> errorMsg;

    public ObservableBoolean enableButton;

    private DrugOld drug;
    private String dateTimeFormat;

    public NewDrugViewModel(Activity baseActivity, DrugOld drug) {
        super(baseActivity);
        getDiComponent().inject(this);
        this.drug = drug;
        clearFields(this.drug);
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
            errorMsg.set("");
        }

        enableSaveButton();
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
                            validateDates();
                        }

                        @Override public void onFail() {
                            startDateEnable.set(false);
                            drug.enableStartDate(false);
                            errorMsg.set("");
                        }
                    });
        }
    }

    public void endDateCheckClick(View view) {
        if (endDateEnable.get()) {
            endDateClick(view);
        } else {
            drug.enableEndDate(false);
            errorMsg.set("");
        }

        enableSaveButton();
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
                            validateDates();
                        }

                        @Override public void onFail() {
                            endDateEnable.set(false);
                            drug.enableEndDate(false);
                            errorMsg.set("");
                        }
                    });
        }
    }

    private String getFormattedDate(DateTime dateTime) {
        return dateTime == null ? "" : dateTime.toString(dateTimeFormat);
    }

    private void validateDates() {
        if (startDateEnable.get() && endDateEnable.get()) {
            DateTime start = drug.getStartDate();
            DateTime end = drug.getEndDate();
            if (start.isAfter(end)) {
                errorMsg.set("Start Date is after end date!");
            } else {
                errorMsg.set("");
            }
        } else {
            errorMsg.set("");
        }

        enableSaveButton();
    }

    private void clearFields(DrugOld drug) {

        enableButton = new ObservableBoolean(false);
        name = new ObservableField<>(drug.name);
        type = new ObservableField<>(drug.type);

        startDateEnable = new ObservableBoolean(drug.isStartDateEnable());
        endDateEnable = new ObservableBoolean(drug.isEndDateEnable());

        startDate = new ObservableField<>(getFormattedDate(drug.getStartDate()));
        endDate = new ObservableField<>(getFormattedDate(drug.getEndDate()));

        doses = new ObservableInt(drug.doses);
        dosesEveryH = new ObservableInt(drug.dosesEveryH);

        errorMsg = new ObservableField<>("");
    }

    private void setUpProperty() {
        dateTimeFormat = getBaseActivity().getString(R.string.date_format);

        doses.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override public void onPropertyChanged(Observable observable, int i) {
                drug.doses = doses.get();
            }
        });

        dosesEveryH.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override public void onPropertyChanged(Observable observable, int i) {
                drug.dosesEveryH = dosesEveryH.get();
            }
        });

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

        enableSaveButton();
    }

    private void enableSaveButton() {
        boolean isNoErrorMsg = !(errorMsg.get().length() > 0);
        enableButton.set(name.get().length() > 0 && type.get().length() > 0 && isNoErrorMsg);
    }
}
