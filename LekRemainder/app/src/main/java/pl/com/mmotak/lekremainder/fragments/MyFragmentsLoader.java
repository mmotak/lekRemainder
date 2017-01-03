package pl.com.mmotak.lekremainder.fragments;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import pl.com.mmotak.lekremainder.R;

/**
 * Created by Maciej on 2016-12-11.
 */

public class MyFragmentsLoader {

    private int layoutResId;

    public MyFragmentsLoader(@LayoutRes int layoutResId) {
        this.layoutResId = layoutResId;
    }

    public void addFragment(FragmentActivity fragmentActivity, IFragment fragment) {
        if (fragment != null && !isNoFragment(fragmentActivity)) {
            FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(layoutResId, fragment.asFragment(), fragment.getTagName()).commit();
        }
    }

    public void replaceFragment(FragmentActivity fragmentActivity, IFragment fragment) {
        if (fragment != null && !isFragmentOnScreen(fragmentActivity, fragment)) {
            FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(layoutResId, fragment.asFragment(), fragment.getTagName());
            fragmentTransaction.commit();
        }
    }

    private boolean isNoFragment(FragmentActivity fragmentActivity) {
        List<Fragment> fragments = fragmentActivity.getSupportFragmentManager().getFragments();
        return fragments != null && !fragments.isEmpty();
    }

    private boolean isFragmentOnScreen(FragmentActivity fragmentActivity, IFragment fragment) {
        Fragment currentFragment = fragmentActivity.getSupportFragmentManager().findFragmentByTag(fragment.getTagName());
        return (currentFragment != null && currentFragment.isVisible());
    }
}
