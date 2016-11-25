package pl.com.mmotak.lekremainder.di;

import javax.inject.Singleton;

import dagger.Component;
import pl.com.mmotak.lekremainder.activities.MainActivity;

/**
 * Created by mmotak on 25.11.2016.
 */

@Singleton
@Component(modules = {DiStorageModule.class,DiApplicationModule.class})
public interface DiComponent {
    void inject(MainActivity mainActivity);
}
