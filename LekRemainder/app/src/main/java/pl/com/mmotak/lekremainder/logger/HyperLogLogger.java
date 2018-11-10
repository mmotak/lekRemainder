package pl.com.mmotak.lekremainder.logger;

import android.content.Context;
import android.util.Log;

import com.hypertrack.hyperlog.HyperLog;

public class HyperLogLogger extends AbstractLogger implements ILogger {

    public HyperLogLogger(String tag, String className) {
        super(tag, className);
    }

    @Override
    protected void logd(String tag, String message) {
        HyperLog.d(tag, message);
    }

    @Override
    protected void loge(String tag, String message) {
        HyperLog.e(tag, message);
    }

    @Override
    protected void loge(String tag, String message, Throwable throwable) {
        HyperLog.e(tag, message, throwable);
    }

    @Override
    protected void logw(String tag, String message) {
        HyperLog.w(tag, message);
    }

    public static void init(Context context) {
        HyperLog.initialize(context);
        HyperLog.setLogLevel(Log.VERBOSE);
    }
}
