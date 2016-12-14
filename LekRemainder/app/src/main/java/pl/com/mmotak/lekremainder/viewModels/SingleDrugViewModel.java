package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import android.widget.Toast;

import pl.com.mmotak.lekremainder.bindings.DialogManager;

/**
 * Created by mmotak on 13.12.2016.
 */

public class SingleDrugViewModel extends AbstractBaseViewModel {

    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> type = new ObservableField<>("");

    public ObservableInt dosesNo = new ObservableInt(1);
    public ObservableInt dosesEveryH = new ObservableInt(1);
    ;

    public ObservableField<DialogManager.IDialogData> dialogData = new ObservableField<>(new DialogManager.IDialogData() {
        Object o = "Some text";

        @Override
        public Object load() {
            return o;
        }

        @Override
        public void save(Object o) {
            this.o = o;
        }
    });

    public SingleDrugViewModel(Activity baseActivity) {
        super(baseActivity);
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
