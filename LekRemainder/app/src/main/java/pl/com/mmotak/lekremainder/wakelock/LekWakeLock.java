package pl.com.mmotak.lekremainder.wakelock;

import android.content.Context;
import android.os.PowerManager;

import java.util.concurrent.TimeUnit;

import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;

public abstract class LekWakeLock {
    private static final ILogger LOGGER = LekLogger.create(LekWakeLock.class.getSimpleName());
    private static final long MAX_TIME = TimeUnit.SECONDS.toMillis(121);
    private final PowerManager.WakeLock wakeLock;

    public LekWakeLock(Context context, String tag) {
        LOGGER.d("Try to create new wakeLock by tag " + tag);
        this.wakeLock = createWakeLock(getPowerManager(context), tag);
    }

    protected abstract PowerManager.WakeLock createWakeLock(PowerManager powerManager, String tag);

    public final void start() {
        LOGGER.d("start wake lock");
        wakeLock.acquire(MAX_TIME);
        LOGGER.d("acquire");
    }

    public final void stop() {
        LOGGER.d("stop wake lock");
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
            LOGGER.d("release");
        }
    }

    private PowerManager getPowerManager(Context context) {
        return (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    }
}
