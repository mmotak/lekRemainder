package pl.com.mmotak.lekremainder.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.adapters.SingleDrugViewPagerAdapter;
import pl.com.mmotak.lekremainder.databinding.ActivitySingleDrugBinding;
import pl.com.mmotak.lekremainder.dialog.ConfirmDialog;
import pl.com.mmotak.lekremainder.dialog.IDialogResult;
import pl.com.mmotak.lekremainder.viewModels.SingleDrugViewModel;

/**
 * Created by mmotak on 13.12.2016.
 */

public class SingleDrugActivity extends BaseNavDrawerActivity implements IDialogResult<Boolean> {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.single_drug_menu, menu);

        MenuItem item = menu.findItem(R.id.menu_delete);
        int drugId = drugID != null ? drugID : 0;
        item.setVisible(drugId != 0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_delete) {
            ConfirmDialog.show(this, getString(R.string.dialog_remove_title), getString(R.string.dialog_remove_message), this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(Boolean data) {
        viewModel.remove();
        finish();
    }

    @Override
    public void onFail() {

    }
}
