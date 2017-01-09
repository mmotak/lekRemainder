package pl.com.mmotak.lekremainder.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.com.mmotak.lekremainder.notification.INotificationProvider;
import pl.com.mmotak.lekremainder.notification.LekRemainderNotificationManager;
import pl.com.mmotak.lekremainder.ui.IToastProvider;
import pl.com.mmotak.lekremainder.ui.LekRemainderToastProvider;

/**
 * Created by mmotak on 02.12.2016.
 */

@Module
public class DiUiModules {

    @Provides
    @Singleton
    public INotificationProvider getNotificationProvider(Context context) {
        return new LekRemainderNotificationManager(context);
    }

    @Provides
    @Singleton
    public IToastProvider getToastProvider() {
        return new LekRemainderToastProvider();
    }
}
