package pl.com.mmotak.lekremainder.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.FragmentHistoryBinding;
import pl.com.mmotak.lekremainder.viewModels.HistoryFragmentViewModel;

/**
 * Created by Maciej on 2016-12-11.
 */

public class HistoryFragment extends Fragment implements IFragment {

    private HistoryFragmentViewModel viewModel;
    private FragmentHistoryBinding binding;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        viewModel = new HistoryFragmentViewModel(getActivity()); // TODO REMOVE!
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
        return HistoryFragment.class.getSimpleName();
    }

    @Override
    public Fragment asFragment() {
        return this;
    }
}
