package pl.com.mmotak.lekremainder.bindings.tools;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.SingleDrugLeftTabBinding;
import pl.com.mmotak.lekremainder.viewModels.SingleDrugViewModel;

/**
 * Created by mmotak on 14.12.2016.
 */
public class InflaterStrategy {

    private static IViewDataBinding create(int position) {
        switch (position) {
            case 0:
                return new MainDrugView();
            case 1:
                return new DosesDrugView();
            default:
                throw new IllegalArgumentException("there is no position for " + position);
        }
    }

    public static ViewDataBinding inflate(LayoutInflater inflater, ViewGroup container, SingleDrugViewModel viewModel, int position) {
        IViewDataBinding dataBinding = InflaterStrategy.create(position);
        dataBinding.inflate(inflater, container);
        dataBinding.setViewModel(viewModel);
        return dataBinding.getViewDataBinding();
    }

}
