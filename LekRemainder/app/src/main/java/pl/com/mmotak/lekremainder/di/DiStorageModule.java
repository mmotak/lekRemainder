package pl.com.mmotak.lekremainder.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.com.mmotak.lekremainder.data.DataBaseProvider;
import pl.com.mmotak.lekremainder.data.IDataProvider;

/**
 * Created by mmotak on 25.11.2016.
 */

@Module
public class DiStorageModule {

    @Provides
    @Singleton
    public IDataProvider getDataProvider(Context context) {
        return new DataBaseProvider(context);
    }
}
