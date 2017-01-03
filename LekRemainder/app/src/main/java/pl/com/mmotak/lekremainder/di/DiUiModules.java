package pl.com.mmotak.lekremainder.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.com.mmotak.lekremainder.notification.INotificationProvider;
import pl.com.mmotak.lekremainder.notification.LekRemainderNotificationManager;

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
}
