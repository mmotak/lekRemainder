package pl.com.mmotak.lekremainder.di;

import javax.inject.Singleton;

import dagger.Component;
import pl.com.mmotak.lekremainder.activities.AddDrugActivity;
import pl.com.mmotak.lekremainder.activities.MainActivity;
import pl.com.mmotak.lekremainder.viewModels.NewDrugViewModel;

/**
 * Created by mmotak on 25.11.2016.
 */

@Singleton
@Component(modules = {DiStorageModule.class,DiApplicationModule.class})
public interface DiComponent {
    void inject(MainActivity mainActivity);

    void inject(AddDrugActivity addDrugActivity);

    void inject(NewDrugViewModel newDrugViewModel);
}
