package pl.com.mmotak.lekremainder.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.data.MemoryDataProvider;

/**
 * Created by mmotak on 25.11.2016.
 */

@Module
public class DiStorageModule {

    @Provides
    @Singleton
    public IDataProvider getDataProvider() {
        return new MemoryDataProvider();
    }
}
