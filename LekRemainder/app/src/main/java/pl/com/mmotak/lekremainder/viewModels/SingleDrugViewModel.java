package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import android.widget.Toast;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.bindings.DialogData;

/**
 * Created by mmotak on 13.12.2016.
 */

public class SingleDrugViewModel extends AbstractBaseViewModel {

    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> type = new ObservableField<>("");

    public ObservableInt dosesNo = new ObservableInt(1);
    public ObservableInt dosesEveryH = new ObservableInt(1);

    public DialogData<Integer> dialogData = new DialogData<>(new Integer(1));

//    public ObservableField<DialogManager.IDialogData> dialogData = new ObservableField<>(new DialogManager.IDialogData() {
//        Object o = null;
//
//        @Override
//        public Object load() {
//            return o;
//        }
//
//        @Override
//        public void save(Object o) {
//            this.o = o;
//        }
//    });

    public SingleDrugViewModel(Activity baseActivity) {
        super(baseActivity);

        dialogData.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // ?
                int x = propertyId;
            }
        });
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
}
