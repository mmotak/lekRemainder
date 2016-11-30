package pl.com.mmotak.lekremainder.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import butterknife.BindView;
import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.ActivityDrugsBinding;
import pl.com.mmotak.lekremainder.viewModels.DrugsViewModel;

public class DrugsActivity extends BaseNavDrawerActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    private ActivityDrugsBinding binding;
    private DrugsViewModel viewModel;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_drugs);
        viewModel = new DrugsViewModel(this);
        binding.setDrugsViewModel(viewModel);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}
