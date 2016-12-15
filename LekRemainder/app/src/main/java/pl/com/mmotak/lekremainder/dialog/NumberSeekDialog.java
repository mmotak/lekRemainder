package pl.com.mmotak.lekremainder.dialog;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.DialogNumberSeekBinding;
import pl.com.mmotak.lekremainder.viewModels.dialogs.NumberDialogViewModel;

/**
 * Created by mmotak on 15.12.2016.
 */

public class NumberSeekDialog extends DialogFragment {

    public static final String TAG = NumberSeekDialog.class.getSimpleName();

    private NumberDialogViewModel viewModel;
    private IDialogResult<Integer> dialogResultListener;
    private String title;
    private int maxSeekBarValue;

    public NumberSeekDialog() {
        viewModel = new NumberDialogViewModel();
    }

    public static void show(FragmentActivity activity, Integer number, int max, String title, @NonNull IDialogResult<Integer> iDialogResult) {
        NumberSeekDialog dialog = new NumberSeekDialog();

        dialog.setDialogResultListener(iDialogResult);
        dialog.setStartValue(number);
        dialog.setTitle(title);
        dialog.setMaxSeekBarValue(max);

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        dialog.show(fragmentManager, TAG);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DialogNumberSeekBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.dialog_number_seek, null, false);

        binding.dialogSeekBarNumber.setMax(maxSeekBarValue);
        binding.setViewModel(viewModel);

        return new AlertDialog
                .Builder(getActivity())
                .setTitle(title != null && title.length() > 0 ? title : "Title")
                .setPositiveButton(R.string.dialog_positive,
                        (dialogInterface, i) -> dialogResultListener.onSuccess(viewModel.number.get()))
                .setNegativeButton(R.string.dialog_negative,
                        (dialogInterface, i) -> dialogResultListener.onFail())
                .setView(binding.getRoot())
                .create();
    }

    private void setDialogResultListener(IDialogResult<Integer> dialogResultListener) {
        this.dialogResultListener = dialogResultListener;
    }

    private void setStartValue(Integer startValue) {
        viewModel.setStartValue(startValue);
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setMaxSeekBarValue(int maxSeekBarValue) {
        if (maxSeekBarValue <= 1) {
            throw new IllegalArgumentException("Max value for seek bar have to be more then 2!");
        }
        this.maxSeekBarValue = maxSeekBarValue - 1; // minus one because it start from zero
    }
}
