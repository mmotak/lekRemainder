package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;

import pl.com.mmotak.lekremainder.di.DiComponent;
import pl.com.mmotak.lekremainder.lekapp.LekRemainderApplication;

/**
 * Created by mmotak on 29.11.2016.
 */

public abstract class AbstractBaseViewModel implements BaseViewModel {

    private final Activity baseActivity;

    public AbstractBaseViewModel(Activity baseActivity)
    {
        this.baseActivity = baseActivity;
    }

    @Override public DiComponent getDiComponent() {
        return ((LekRemainderApplication) baseActivity.getApplication()).getDiComponent();
    }

    @Override public Activity getBaseActivity() {
        return baseActivity;
    }
}
