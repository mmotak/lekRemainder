package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.adapters.SingleDrugDosesAdapter;
import pl.com.mmotak.lekremainder.bindings.DialogData;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.models.DosesTimesGenerator;
import pl.com.mmotak.lekremainder.models.Drug;

/**
 * Created by mmotak on 13.12.2016.
 */

public class SingleDrugViewModel extends AbstractBaseViewModel {

    @Inject
    IDataProvider dataProvider;

    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> type = new ObservableField<>("");
    public ObservableInt dosesNo = new ObservableInt(1);
    public ObservableInt dosesEveryH = new ObservableInt(1);
    public ObservableBoolean enableButton = new ObservableBoolean(false);
    public DialogData<LocalTime> startTime = new DialogData<>(new LocalTime(8, 0));

    private SingleDrugDosesAdapter adapter;
    private List<LocalTime> doses = new ArrayList<>();

    private Drug drug;

    public SingleDrugViewModel(Activity baseActivity, Integer id) {
        super(baseActivity);
        getDiComponent().inject(this);

        this.drug = dataProvider.getDrugById(id);

        setUpFields();
        setUpObservables();
    }

    private void setUpFields() {
        name.set(drug.getName());
        type.set(drug.getType());
        dosesNo.set(drug.getDosesNo());
        dosesEveryH.set(drug.getDosesEveryH());
        startTime.setObject(drug.getDoses().get(0)); // the first item
        doses.clear();
        doses.addAll(drug.getDoses());

        adapter = new SingleDrugDosesAdapter();
        adapter.setList(doses);
    }

    private void setUpObservables() {
        Observable.OnPropertyChangedCallback dosesPropertyChangedCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                createList();
            }
        };
        dosesNo.addOnPropertyChangedCallback(dosesPropertyChangedCallback);
        dosesEveryH.addOnPropertyChangedCallback(dosesPropertyChangedCallback);

        Observable.OnPropertyChangedCallback buttonPropertyChangedCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                enableSaveButton();
            }
        };
        name.addOnPropertyChangedCallback(buttonPropertyChangedCallback);
        type.addOnPropertyChangedCallback(buttonPropertyChangedCallback);

        startTime.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                createList();
            }
        });

        enableSaveButton();
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public void onSaveButtonClick(View view) {
        drug.update(name.get(), type.get(), dosesNo.get(), dosesEveryH.get(), doses);

        dataProvider.addNewDrug(drug);
        getBaseActivity().finish();
    }

    @Override
    public void subscribeOnResume() {

    }

    @Override
    public void unSubscribeOnPause() {

    }

    @Override
    public void onDestroy() {

    }

    private void enableSaveButton() {
        enableButton.set(name.get().length() > 0 && type.get().length() > 0);
    }

    private void createList() {
        doses = DosesTimesGenerator.generate(startTime.getObject(), dosesNo.get(), dosesEveryH.get());
        adapter.setList(doses);
    }
}
