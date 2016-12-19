package pl.com.mmotak.lekremainder.di;

import javax.inject.Singleton;

import dagger.Component;
import pl.com.mmotak.lekremainder.viewModels.DrugsViewModel;
import pl.com.mmotak.lekremainder.viewModels.SingleDrugViewModel;
import pl.com.mmotak.lekremainder.viewModels.TodayDoseViewModel;

/**
 * Created by mmotak on 25.11.2016.
 */

@Singleton
@Component(modules = {DiStorageModule.class,DiApplicationModule.class,DiUiModules.class})
public interface DiComponent {
    void inject(DrugsViewModel drugsViewModel);

    void inject(SingleDrugViewModel singleDrugViewModel);

    void inject(TodayDoseViewModel todayDoseViewModel);
}
