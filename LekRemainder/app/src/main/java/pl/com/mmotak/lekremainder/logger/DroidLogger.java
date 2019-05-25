package pl.com.mmotak.lekremainder.logger;

import android.util.Log;

public class DroidLogger extends AbstractLogger implements ILogger {

    public DroidLogger(String tag, String className) {
        super(tag, className);
    }

    @Override
    protected void logd(String tag, String message) {
        Log.d(tag, message);
    }

    @Override
    protected void loge(String tag, String message) {
        Log.d(tag, message);
    }

    @Override
    protected void loge(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);
    }

    @Override
    protected void logw(String tag, String message) {
        Log.w(tag, message);
    }
}
