package pl.com.mmotak.lekremainder.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.FragmentDrugsBinding;
import pl.com.mmotak.lekremainder.viewModels.DrugsViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrugsFragment extends Fragment implements IFragment {

    private FragmentDrugsBinding binding;
    private DrugsViewModel viewModel;

    public DrugsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_drugs, container, false);
        viewModel = new DrugsViewModel(getActivity()); // TODO REMOVE!
        binding.setDrugsViewModel(viewModel);

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
        return DrugsFragment.class.getSimpleName();
    }

    @Override
    public Fragment asFragment() {
        return this;
    }
}
