package pl.com.mmotak.lekremainder.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import pl.com.mmotak.lekremainder.R;

/**
 * Created by Maciej on 2016-12-11.
 */

public class MyFragmentsLoader {

    public MyFragmentsLoader() {
    }

    public void addFragment(FragmentActivity fragmentActivity, IFragment fragment) {
        if (!isFragmentOnScreen(fragmentActivity, fragment)) {
            FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_layout, fragment.asFragment(), fragment.getTagName()).commit();
        }
    }

    public void replaceFragment(FragmentActivity fragmentActivity, IFragment fragment) {

        if (!isFragmentOnScreen(fragmentActivity, fragment)) {
            FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.fragment_layout, fragment.asFragment(), fragment.getTagName());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private boolean isFragmentOnScreen(FragmentActivity fragmentActivity, IFragment fragment) {
        Fragment currentFragment = fragmentActivity.getSupportFragmentManager().findFragmentByTag(fragment.getTagName());
        return (currentFragment != null && currentFragment.isVisible());
    }
}
