package pl.com.mmotak.lekremainder.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.adapters.SingleDrugViewPagerAdapter;
import pl.com.mmotak.lekremainder.databinding.ActivitySingleDrugBinding;
import pl.com.mmotak.lekremainder.viewModels.SingleDrugViewModel;

/**
 * Created by mmotak on 13.12.2016.
 */

public class SingleDrugActivity extends BaseNavDrawerActivity {

    @InjectExtra
    @Nullable
    public Integer drugID = 0;

    private ActivitySingleDrugBinding binding;
    private SingleDrugViewPagerAdapter adapter;
    private SingleDrugViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dart.inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_single_drug);
        viewModel = new SingleDrugViewModel(this, drugID);
        binding.setViewModel(viewModel);

        adapter = new SingleDrugViewPagerAdapter(this, viewModel);
        binding.viewpager.setAdapter(adapter);
        binding.viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
