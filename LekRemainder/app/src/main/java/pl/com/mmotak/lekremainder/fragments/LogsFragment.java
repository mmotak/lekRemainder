package pl.com.mmotak.lekremainder.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.FragmentLogsBinding;
import pl.com.mmotak.lekremainder.viewModels.LogsFragmentsViewModel;


public class LogsFragment extends Fragment implements IFragment {
    private LogsFragmentsViewModel viewModel;
    private FragmentLogsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_logs, container, false);
        viewModel = new LogsFragmentsViewModel(getActivity()); // TODO REMOVE!
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
        return LogsFragment.class.getSimpleName();
    }

    @Override
    public Fragment asFragment() {
        return this;
    }
}
