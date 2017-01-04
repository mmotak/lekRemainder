package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.content.Context;
import android.databinding.Observable;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.alarms.TodayDoseResetAlarmManager;
import pl.com.mmotak.lekremainder.bindings.DialogData;
import pl.com.mmotak.lekremainder.data.ISharedDateProvider;
import pl.com.mmotak.lekremainder.dialog.ConfirmDialog;
import pl.com.mmotak.lekremainder.dialog.IDialogResult;

/**
 * Created by Maciej on 2017-01-04.
 */

public class SettingsFragmentViewModel extends AbstractBaseViewModel implements IDialogResult<Boolean> {

    @Inject
    ISharedDateProvider sharedDateProvider;

    public DialogData<LocalTime> time;

    public SettingsFragmentViewModel(Activity baseActivity) {
        super(baseActivity);
        getDiComponent().inject(this);
        setUpFields();
    }

    private void setUpFields() {
        time = new DialogData<>(sharedDateProvider.loadResetTimeasLocalTime());

        time.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                sharedDateProvider.saveResetTime(time.getObject());
            }
        });
    }

    public void onResetNowButtonClick(View view) {
        Context context = view.getContext();
        ConfirmDialog.show(context,
                context.getString(R.string.reset_now_ask_title),
                context.getString(R.string.reset_now_ask),
                this);
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

    @Override
    public void onSuccess(Boolean data) {
        TodayDoseResetAlarmManager.setNextAlarmTodayDoseResetService(getBaseActivity(), DateTime.now().plusSeconds(7));
    }

    @Override
    public void onFail() {

    }
}
