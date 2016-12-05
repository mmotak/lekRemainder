package pl.com.mmotak.lekremainder.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.ActivityNewDrugBinding;
import pl.com.mmotak.lekremainder.models.Drug;
import pl.com.mmotak.lekremainder.viewModels.NewDrugViewModel;

public class NewDrugActivity extends BaseNavDrawerActivity {

    private ActivityNewDrugBinding binding;
    private NewDrugViewModel viewModel;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_drug);
        viewModel = new NewDrugViewModel(this, new Drug());
        binding.setDrugViewModel(viewModel);
    }

}
