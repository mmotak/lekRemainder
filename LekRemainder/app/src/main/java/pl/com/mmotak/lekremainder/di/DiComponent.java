package pl.com.mmotak.lekremainder.di;

import javax.inject.Singleton;

import dagger.Component;
import pl.com.mmotak.lekremainder.procedures.DoseResetProcedure;
import pl.com.mmotak.lekremainder.procedures.NextDoseProcedure;
import pl.com.mmotak.lekremainder.services.NextDoseAlarmService;
import pl.com.mmotak.lekremainder.services.TodayDoseResetService;
import pl.com.mmotak.lekremainder.viewModels.DrugsViewModel;
import pl.com.mmotak.lekremainder.viewModels.HistoryFragmentViewModel;
import pl.com.mmotak.lekremainder.viewModels.SettingsFragmentViewModel;
import pl.com.mmotak.lekremainder.viewModels.SingleDrugViewModel;
import pl.com.mmotak.lekremainder.viewModels.TodayDoseViewModel;

/**
 * Created by mmotak on 25.11.2016.
 */

@Singleton
@Component(modules = {DiStorageModule.class, DiApplicationModule.class, DiUiModules.class})
public interface DiComponent {
    void inject(DrugsViewModel drugsViewModel);

    void inject(SingleDrugViewModel singleDrugViewModel);

    void inject(TodayDoseViewModel todayDoseViewModel);

    void inject(HistoryFragmentViewModel historyFragmentViewModel);

    void inject(TodayDoseResetService todayDoseResetService);

    void inject(NextDoseAlarmService nextDoseAlarmService);

    void inject(SettingsFragmentViewModel settingsViewModel);

    void inject(NextDoseProcedure nextDoseProcedure);

    void inject(DoseResetProcedure doseResetProcedure);
}
