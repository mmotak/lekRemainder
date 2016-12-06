package pl.com.mmotak.lekremainder.di;

import dagger.Module;
import dagger.Provides;
import pl.com.mmotak.lekremainder.dialog.DateDialogPickerProvider;
import pl.com.mmotak.lekremainder.dialog.IDateUIProvider;

/**
 * Created by mmotak on 02.12.2016.
 */

@Module
public class DiUiModules {

    @Provides
    public IDateUIProvider getDateUIProvider() {
        return new DateDialogPickerProvider();
    }
}
