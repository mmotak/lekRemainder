package pl.com.mmotak.lekremainder.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.FragmentSettingsBinding;
import pl.com.mmotak.lekremainder.viewModels.SettingsFragmentViewModel;
import pl.com.mmotak.lekremainder.viewModels.TodayDoseViewModel;

/**
 * Created by Maciej on 2016-12-11.
 */

public class SettingsFragment extends Fragment implements IFragment {

    private FragmentSettingsBinding binding;
    private SettingsFragmentViewModel viewModel;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        viewModel = new SettingsFragmentViewModel(getActivity()); // TODO REMOVE!
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        viewModel.subscribeOnResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.unSubscribeOnPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }

    @Override
    public String getTagName() {
        return SettingsFragment.class.getSimpleName();
    }

    @Override
    public Fragment asFragment() {
        return new SettingsFragment();
    }
}
