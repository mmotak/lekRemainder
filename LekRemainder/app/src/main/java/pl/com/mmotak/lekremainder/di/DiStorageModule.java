package pl.com.mmotak.lekremainder.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.com.mmotak.lekremainder.BuildConfig;
import pl.com.mmotak.lekremainder.data.DataBaseProvider;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.data.ISharedDateProvider;
import pl.com.mmotak.lekremainder.data.ShaderDataProvider;
import pl.com.mmotak.lekremainder.data.backup.FileJsonBackuper;
import pl.com.mmotak.lekremainder.data.backup.IFileBackup;
import pl.com.mmotak.lekremainder.data.backup.PublicFileStorage;
import pl.com.mmotak.lekremainder.data.backup.SimpleFileStorage;

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

    @Provides
    @Singleton
    public ISharedDateProvider getSharedDateProvider(Context context) {
        return new ShaderDataProvider(context);
    }

    @Provides
    @Singleton
    public PublicFileStorage getPublicFileStorageProvider(Context context) {
        return new SimpleFileStorage(context, "/DrugRemainderBackups");
    }

    @Provides
    @Singleton
    public IFileBackup getFileBackupProvicer(PublicFileStorage publicFileStorage, IDataProvider iDataProvider) {
        return new FileJsonBackuper(iDataProvider, publicFileStorage);
    }
}
