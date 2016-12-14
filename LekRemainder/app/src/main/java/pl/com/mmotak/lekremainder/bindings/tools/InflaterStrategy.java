package pl.com.mmotak.lekremainder.bindings.tools;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import pl.com.mmotak.lekremainder.viewModels.BaseViewModel;

/**
 * Created by mmotak on 14.12.2016.
 */
public class InflaterStrategy {

    private static AbstractViewDataBinder create(int position) {
        switch (position) {
            case 0:
                return new MainDrugView();
            case 1:
                return new DosesDrugView();
            default:
                throw new IllegalArgumentException("there is no position for " + position);
        }
    }

    public static int getCount() {
        return 2;
    }

    public static ViewDataBinding inflate(LayoutInflater inflater, ViewGroup container, BaseViewModel viewModel, int position) {
        AbstractViewDataBinder<?,?> dataBinding = InflaterStrategy.create(position);
        dataBinding.inflate(inflater, container);
        dataBinding.setViewModel(viewModel);
        return dataBinding.getViewDataBinding();
    }

}
