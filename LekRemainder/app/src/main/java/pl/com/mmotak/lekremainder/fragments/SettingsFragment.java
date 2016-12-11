package pl.com.mmotak.lekremainder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.com.mmotak.lekremainder.R;

/**
 * Created by Maciej on 2016-12-11.
 */

public class SettingsFragment extends Fragment implements IFragment {

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
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
