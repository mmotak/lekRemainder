package pl.com.mmotak.lekremainder.bindings.tools;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import pl.com.mmotak.lekremainder.viewModels.SingleDrugViewModel;

/**
 * Created by mmotak on 14.12.2016.
 */
public interface IViewDataBinding {
    int getLayoutId();

    void inflate(LayoutInflater inflater, ViewGroup container);

    void setViewModel(SingleDrugViewModel viewModel);

    ViewDataBinding getViewDataBinding();
}
