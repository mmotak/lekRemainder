package pl.com.mmotak.lekremainder.logger;

import android.content.Context;

import com.hypertrack.hyperlog.HyperLog;

import java.io.File;

import rx.Observable;
import rx.schedulers.Schedulers;

public class LekLogger {
    private static final String TAG = "LekRemainder";

    private static LekLogger lekLogger = new LekLogger();
    private boolean isInitDone = false;

    private LekLogger() {
    }

    public static ILogger create(String className) {
        if (lekLogger.isInitDone) {
            return new HyperLogLogger(TAG, className);
        } else {
            throw new IllegalStateException("Please init me in application class");
        }
    }

    public static void init(Context context) {
        lekLogger.initMe(context);
    }

    public static Observable<File> saveLogsToFile(Context context) {
        return Observable.just(HyperLog.getDeviceLogsInFile(context))
                .subscribeOn(Schedulers.io());
    }

    private void initMe(Context context) {
        HyperLogLogger.init(context);
        isInitDone = true;
    }
}
