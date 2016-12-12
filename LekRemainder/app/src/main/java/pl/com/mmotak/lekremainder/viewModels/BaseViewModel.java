package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.content.Context;

import pl.com.mmotak.lekremainder.di.DiComponent;

/**
 * Created by mmotak on 29.11.2016.
 */

public interface BaseViewModel {

    DiComponent getDiComponent();

    Activity getBaseActivity();

    void subscribeOnResume();

    void unSubscribeOnPause();
}
