package pl.com.mmotak.lekremainder.wakelock;

import android.content.Context;
import android.os.PowerManager;

public class ScreenLekWakeLock extends LekWakeLock {
    public ScreenLekWakeLock(Context context, String tag) {
        super(context, tag);
    }

    @Override
    protected PowerManager.WakeLock createWakeLock(PowerManager powerManager, String tag) {
        return powerManager.newWakeLock(
                PowerManager.SCREEN_DIM_WAKE_LOCK
                //PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                        | PowerManager.ACQUIRE_CAUSES_WAKEUP, tag);
    }
}
