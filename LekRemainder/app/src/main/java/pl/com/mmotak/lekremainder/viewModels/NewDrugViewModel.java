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
import pl.com.mmotak.lekremainder.dialog.IDialogResult;
import pl.com.mmotak.lekremainder.entities.DbDrug;
import pl.com.mmotak.lekremainder.entities.DbDrugEntity;
import pl.com.mmotak.lekremainder.models.Drug;

/**
 * Created by mmotak on 28.11.2016.
 */

public class NewDrugViewModel extends AbstractBaseViewModel {

    @Inject
    IDataProvider dataProvider;
    @Inject
    IDateUIProvider dateUIProvider;

    public ObservableField<String> name;
    public ObservableField<String> type;

    public ObservableInt dosesNo;
    public ObservableInt dosesEveryH;
    public ObservableField<String> dosesHours;

    public ObservableField<String> startDate;
    public ObservableField<String> endDate;

    public ObservableBoolean startDateEnable;
    public ObservableBoolean endDateEnable;

    public ObservableField<String> errorMsg;
    public ObservableField<String> dosesNoErrorMsg;

    public ObservableBoolean enableButton;

    private Drug drug;
    private String dateTimeFormat;
    private String localTimeFormat;

    public NewDrugViewModel(Activity baseActivity, Integer id) {
        super(baseActivity);
        getDiComponent().inject(this);

        dateTimeFormat = getBaseActivity().getString(R.string.date_format);
        localTimeFormat = getBaseActivity().getString(R.string.time_format);
        this.drug = dataProvider.getDrugById(id);
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
            drug.setStartDateEnable(false);
            errorMsg.set("");
        }

        enableSaveButton();
    }

    public void startDateClick(View view) {
        if (startDateEnable.get()) {
            dateUIProvider.showDialog(getBaseActivity(),
                    drug.getStartDate(),
                    new IDialogResult<DateTime>() {

                        @Override
                        public void onSuccess(DateTime dateTime) {
                            drug.setStartDate(dateTime);
                            drug.setStartDateEnable(true);
                            startDate.set(getFormattedDate(dateTime));
                            startDateEnable.set(true);
                            validateDates();
                        }

                        @Override
                        public void onFail() {
                            startDateEnable.set(false);
                            drug.setStartDateEnable(false);
                            errorMsg.set("");
                        }
                    });
        }
    }

    public void endDateCheckClick(View view) {
        if (endDateEnable.get()) {
            endDateClick(view);
        } else {
            drug.setEndDateEnable(false);
            errorMsg.set("");
        }

        enableSaveButton();
    }

    public void endDateClick(View view) {
        if (endDateEnable.get()) {
            dateUIProvider.showDialog(getBaseActivity(),
                    drug.getEndDate(),
                    new IDialogResult<DateTime>() {

                        @Override
                        public void onSuccess(DateTime dateTime) {
                            drug.setEndDate(dateTime);
                            drug.setEndDateEnable(true);
                            endDate.set(getFormattedDate(dateTime));
                            endDateEnable.set(true);
                            validateDates();
                        }

                        @Override
                        public void onFail() {
                            endDateEnable.set(false);
                            drug.setEndDateEnable(false);
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

    private void clearFields(Drug drug) {

        enableButton = new ObservableBoolean(false);
        name = new ObservableField<>(drug.getName());
        type = new ObservableField<>(drug.getType());

        startDateEnable = new ObservableBoolean(drug.isStartDateEnable());
        endDateEnable = new ObservableBoolean(drug.isEndDateEnable());

        startDate = new ObservableField<>(getFormattedDate(drug.getStartDate()));
        endDate = new ObservableField<>(getFormattedDate(drug.getEndDate()));

        dosesNo = new ObservableInt(drug.getDosesNo());
        dosesEveryH = new ObservableInt(drug.getDosesEveryH());

        dosesHours = new ObservableField<>("8:00");

        errorMsg = new ObservableField<>("");
        dosesNoErrorMsg = new ObservableField<>("");
    }

    private void setUpProperty() {

        dosesNo.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                drug.setDosesNo(dosesNo.get());
                if (dosesNo.get() == 0) {
                    dosesNoErrorMsg.set("Cannot be 0!");
                } else {
                    dosesNoErrorMsg.set("");
                }
            }
        });

        dosesEveryH.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                drug.setDosesEveryH(dosesEveryH.get());
            }
        });

        name.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                drug.setName(name.get());
                enableSaveButton();
            }
        });

        type.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                drug.setType(type.get());
                enableSaveButton();
            }
        });

        enableSaveButton();
    }

    private void enableSaveButton() {
        boolean isNoErrorMsg = !(errorMsg.get().length() > 0)
                && !(dosesNoErrorMsg.get().length() > 0);
        enableButton.set(name.get().length() > 0 && type.get().length() > 0 && isNoErrorMsg);
    }

    @Override
    public void subscribeOnResume() {

    }

    @Override
    public void unSubscribeOnPause() {

    }
}
