package pl.com.mmotak.lekremainder.fragments;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.FragmentTodayDrugsBinding;
import pl.com.mmotak.lekremainder.viewModels.TodayDoseViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayDrugsFragment extends Fragment implements IFragment {


    private FragmentTodayDrugsBinding binding;
    private TodayDoseViewModel viewModel;

    public TodayDrugsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today_drugs, container, false);
        viewModel = new TodayDoseViewModel(getActivity()); // TODO REMOVE!
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
        return TodayDrugsFragment.class.getSimpleName();
    }

    @Override
    public Fragment asFragment() {
        return this;
    }
}
