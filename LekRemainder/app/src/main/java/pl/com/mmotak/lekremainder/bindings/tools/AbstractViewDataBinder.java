package pl.com.mmotak.lekremainder.bindings.tools;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import pl.com.mmotak.lekremainder.viewModels.BaseViewModel;

/**
 * Created by mmotak on 14.12.2016.
 */
abstract class AbstractViewDataBinder<V extends ViewDataBinding, M extends BaseViewModel> {

    private V bindings;

    public void inflate(LayoutInflater inflater, ViewGroup container) {
        bindings = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
    }

    public ViewDataBinding getViewDataBinding() {
        return bindings;
    }

    protected V getBindings() {
        return bindings;
    }

    public abstract void setViewModel(BaseViewModel viewModel);

    protected abstract int getLayoutId();
}
