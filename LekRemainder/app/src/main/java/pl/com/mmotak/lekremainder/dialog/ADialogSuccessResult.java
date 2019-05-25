package pl.com.mmotak.lekremainder.dialog;

import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;

/**
 * Created by mmotak on 02.11.2018.
 */
public abstract class ADialogSuccessResult<T> implements IDialogResult<T> {
    private static final ILogger LOGGER = LekLogger.create(ADialogSuccessResult.class.getSimpleName());

    @Override
    public void onFail() {
        // empty line here
        LOGGER.w("onFail");
    }
}
