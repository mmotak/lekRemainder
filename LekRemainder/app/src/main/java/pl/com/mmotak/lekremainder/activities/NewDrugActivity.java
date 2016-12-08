package pl.com.mmotak.lekremainder.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.ActivityNewDrugBinding;
import pl.com.mmotak.lekremainder.entities.DbDrug;
import pl.com.mmotak.lekremainder.entities.DbDrugEntity;
import pl.com.mmotak.lekremainder.models.Drug;
import pl.com.mmotak.lekremainder.viewModels.NewDrugViewModel;

public class NewDrugActivity extends BaseNavDrawerActivity {

    @InjectExtra
    public Integer drugID = 0;

    private ActivityNewDrugBinding binding;
    private NewDrugViewModel viewModel;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dart.inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_drug);
        viewModel = new NewDrugViewModel(this, drugID);
        binding.setDrugViewModel(viewModel);
    }
}
