package pl.com.mmotak.lekremainder.wakelock;

import android.content.Context;
import android.os.PowerManager;

import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;

public class LekWakeLock {
    private static final ILogger LOGGER = LekLogger.create(LekWakeLock.class.getSimpleName());
    private final PowerManager.WakeLock wakeLock;

    public LekWakeLock(Context context, String tag) {
        LOGGER.d("Try to create new wakeLock by tag " + tag);
        this.wakeLock = getPowerManager(context).newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, tag);
    }


    private PowerManager getPowerManager(Context context) {
        return (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    }

    public void start() {
        wakeLock.acquire();
    }

    public void stop() {
        if (wakeLock.isHeld()) {
            wakeLock.release();
        }
    }
}
