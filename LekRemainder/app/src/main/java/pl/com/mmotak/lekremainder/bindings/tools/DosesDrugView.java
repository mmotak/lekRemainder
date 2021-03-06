package pl.com.mmotak.lekremainder.bindings.tools;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.SingleDrugRightTabBinding;
import pl.com.mmotak.lekremainder.viewModels.BaseViewModel;
import pl.com.mmotak.lekremainder.viewModels.SingleDrugViewModel;

/**
 * Created by mmotak on 14.12.2016.
 */
public class DosesDrugView extends AbstractViewDataBinder<SingleDrugRightTabBinding, SingleDrugViewModel> {
    @Override
    public void setViewModel(BaseViewModel viewModel) {
        getBindings().setViewModel((SingleDrugViewModel) viewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.single_drug_right_tab;
    }
}
