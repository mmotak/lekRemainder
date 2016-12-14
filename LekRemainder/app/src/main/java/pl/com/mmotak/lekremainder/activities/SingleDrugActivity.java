package pl.com.mmotak.lekremainder.activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.adapters.SingleDrugViewPagerAdapter;
import pl.com.mmotak.lekremainder.databinding.ActivitySingleDrugBinding;
import pl.com.mmotak.lekremainder.databinding.SingleDrugLeftTabBinding;
import pl.com.mmotak.lekremainder.databinding.SingleDrugRightTabBinding;
import pl.com.mmotak.lekremainder.viewModels.NewDrugViewModel;
import pl.com.mmotak.lekremainder.viewModels.SingleDrugViewModel;

/**
 * Created by mmotak on 13.12.2016.
 */

public class SingleDrugActivity extends BaseNavDrawerActivity {

    private ActivitySingleDrugBinding binding;
    private SingleDrugViewPagerAdapter adapter;
    private SingleDrugViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_single_drug);
        viewModel = new SingleDrugViewModel(this);
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        binding.button.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                binding.button.animate().translationY(-binding.button.getHeight());
//                binding.button.setVisibility(View.INVISIBLE);
//            }
//        },1300);
//    }
}
