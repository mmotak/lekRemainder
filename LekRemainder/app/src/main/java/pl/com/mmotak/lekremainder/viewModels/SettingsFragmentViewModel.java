package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.content.Context;
import android.databinding.Observable;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.io.File;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.alarms.TodayDoseResetAlarmManager;
import pl.com.mmotak.lekremainder.bindings.DialogData;
import pl.com.mmotak.lekremainder.data.ISharedDateProvider;
import pl.com.mmotak.lekremainder.data.backup.IFileBackup;
import pl.com.mmotak.lekremainder.dialog.ADialogSuccessResult;
import pl.com.mmotak.lekremainder.dialog.ConfirmDialog;
import pl.com.mmotak.lekremainder.dialog.IDialogResult;
import pl.com.mmotak.lekremainder.email.EmailSender;
import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;
import pl.com.mmotak.lekremainder.ui.IToastProvider;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Maciej on 2017-01-04.
 */

public class SettingsFragmentViewModel extends AbstractBaseViewModel {
    private static final ILogger LOGGER = LekLogger.create(SettingsFragmentViewModel.class.getSimpleName());

    @Inject
    ISharedDateProvider sharedDateProvider;
    @Inject
    IToastProvider toastProvider;
    @Inject
    IFileBackup fileBackup;

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

    private void showSendFileByEmailDialog(Context context, String title, File file) {
        ConfirmDialog.show(context, title, "Send file via email?",
                new ADialogSuccessResult<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        EmailSender.sendFileToEmail(context, context.getString(R.string.app_name) + " " + title, file);
                    }
                });
    }

    public void onLogsSaveToFile(View view) {
        Context context = view.getContext();
        ConfirmDialog.show(context, "Logs", "Save logs to file?",
                new ADialogSuccessResult<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        LekLogger.saveLogsToFile(context)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(file -> {
                                            if (file != null && file.exists()) {
                                                showSendFileByEmailDialog(context, "Logs " + DateTime.now(), file);
                                            } else {
                                                toastProvider.show(context, "Logs not saved");
                                            }
                                        },
                                        e -> LOGGER.e(e.getMessage(), e));
                    }
                });
    }

    public void onResetNowButtonClick(View view) {
        Context context = view.getContext();
        ConfirmDialog.show(context,
                context.getString(R.string.reset_now_ask_title),
                context.getString(R.string.reset_now_ask),
                new ADialogSuccessResult<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        TodayDoseResetAlarmManager.setNextAlarmTodayDoseResetService(getBaseActivity(), DateTime.now().plusSeconds(7));
                        toastProvider.show(getBaseActivity(), "Next Reset All Alarm will be call in 7 seconds");
                    }
                });
    }

    public void onSaveHistoryButtonClick(View view) {
        Context context = view.getContext();
        ConfirmDialog.show(context,
                "Save History?",
                "Save All Drugs History?",
                new ADialogSuccessResult<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        fileBackup.saveHistory()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(file -> {
                                            if (file != null && file.exists()) {
                                                showSendFileByEmailDialog(context, "History", file);
                                            } else {
                                                toastProvider.show(view.getContext(), "History not saved");
                                            }
                                        },
                                        e -> LOGGER.e(e.getMessage(), e));
                    }
                });
    }

    public void onSaveConfigButtonClick(View view) {
        Context context = view.getContext();
        ConfirmDialog.show(context,
                "Save Configuration?",
                "Save Configuration?",
                new IDialogResult<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        fileBackup.saveConfig()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(file -> {
                                            if (file != null && file.exists()) {
                                                showSendFileByEmailDialog(context, "Configuration", file);
                                            } else {
                                                toastProvider.show(view.getContext(), "Configuration not saved");
                                            }
                                        },
                                        e -> LOGGER.e(e.getMessage(), e));
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    public void onLoadConfigButtonClick(View view) {
        Context context = view.getContext();
        ConfirmDialog.show(context,
                "Save Configuration?",
                "Save Configuration?",
                new IDialogResult<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        fileBackup.loadConfig()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(isOk -> toastProvider.show(view.getContext(), isOk ? "saved" : "not saved"),
                                        e -> LOGGER.e(e.getMessage(), e));
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }


    public void onTestNextDoseTimeButtonClick(View view) {
        TodayDoseResetAlarmManager.setNextAlarmNextDoseAlarmService(view.getContext(), DateTime.now().plusSeconds(7));
        toastProvider.show(view.getContext(), "Next Doses Alarm will be call in 7 seconds");
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
}
