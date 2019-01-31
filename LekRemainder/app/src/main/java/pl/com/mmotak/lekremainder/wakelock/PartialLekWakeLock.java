package pl.com.mmotak.lekremainder.wakelock;

import android.content.Context;
import android.os.PowerManager;

public class PartialLekWakeLock extends LekWakeLock {

    public PartialLekWakeLock(Context context, String tag) {
        super(context, tag);
    }

    @Override
    protected PowerManager.WakeLock createWakeLock(PowerManager powerManager, String tag) {
        return powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, tag);
    }
}
