package pl.com.mmotak.lekremainder.bindings.tools;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.SingleDrugLeftTabBinding;
import pl.com.mmotak.lekremainder.databinding.SingleDrugRightTabBinding;
import pl.com.mmotak.lekremainder.viewModels.SingleDrugViewModel;

/**
 * Created by mmotak on 14.12.2016.
 */
public class DosesDrugView implements IViewDataBinding {

    private SingleDrugRightTabBinding bindings;

    @Override
    public int getLayoutId() {
        return R.layout.single_drug_right_tab;
    }

    @Override
    public void inflate(LayoutInflater inflater, ViewGroup container) {
        bindings = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
    }

    @Override
    public void setViewModel(SingleDrugViewModel viewModel) {
        bindings.setViewModel(viewModel);
    }

    @Override
    public ViewDataBinding getViewDataBinding() {
        return bindings;
    }
}
