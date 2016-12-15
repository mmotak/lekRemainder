package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import pl.com.mmotak.lekremainder.adapters.SingleDrugDosesAdapter;
import pl.com.mmotak.lekremainder.bindings.DialogData;
import pl.com.mmotak.lekremainder.dialog.IDialogResult;
import pl.com.mmotak.lekremainder.dialog.TimeDialog;

/**
 * Created by mmotak on 13.12.2016.
 */

public class SingleDrugViewModel extends AbstractBaseViewModel {

    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> type = new ObservableField<>("");

    public ObservableInt dosesNo = new ObservableInt(1);
    public ObservableInt dosesEveryH = new ObservableInt(1);

    public DialogData<LocalTime> startTime = new DialogData<>(new LocalTime(8,0));

    private Observable.OnPropertyChangedCallback dosesPropertyChangedCallback;

    private SingleDrugDosesAdapter adapter;
    private List<LocalTime> list = new ArrayList<>();

    public SingleDrugViewModel(Activity baseActivity) {
        super(baseActivity);

        list.add(new LocalTime(8,0));
        adapter = new SingleDrugDosesAdapter();
        adapter.setList(list);

        dosesPropertyChangedCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                createList();
            }
        };

        dosesNo.addOnPropertyChangedCallback(dosesPropertyChangedCallback);
        dosesEveryH.addOnPropertyChangedCallback(dosesPropertyChangedCallback);


        startTime.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                createList();
            }
        });
    }

    public RecyclerView.Adapter  getAdapter() {
        return adapter;
    }

    public void onSaveButtonClick(View view) {
        Toast.makeText(getBaseActivity(), "Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void subscribeOnResume() {

    }

    @Override
    public void unSubscribeOnPause() {

    }

    private void createList() {
        list = new ArrayList<>();

        LocalTime time = startTime.getObject();
        for (int i = 0 ; i < dosesNo.get(); i++) {
            list.add(time.plusHours(i * dosesEveryH.get()));
        }

        adapter.setList(list);
    }
}
