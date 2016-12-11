package pl.com.mmotak.lekremainder.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import pl.com.mmotak.lekremainder.lekapp.LekRemainderApplication;

/**
 * Created by mmotak on 25.11.2016.
 */

@Module
public class DiApplicationModule {

    private final LekRemainderApplication app;

    public DiApplicationModule(LekRemainderApplication app) {
        this.app = app;
    }

    @Provides
    public LekRemainderApplication provideApplication() {
        return app;
    }

    @Provides
    public Context getContext() {
        return app.getApplicationContext();
    }
}
