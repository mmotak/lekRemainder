package pl.com.mmotak.lekremainder.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by Maciej on 2016-12-11.
 */

public interface IFragment {

    String getTagName();
    Fragment asFragment();
}
