package pl.com.mmotak.lekremainder.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;

/**
 * Created by mmotak on 02.01.2017.
 */

public abstract class BaseService extends Service {

    private Looper serviceLooper;
    private BaseService.ServiceHandler serviceHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        HandlerThread thread = new HandlerThread(
                NextDoseAlarmService.class.getSimpleName(),
                Process.THREAD_PRIORITY_BACKGROUND);
        // start the new handler thread
        thread.start();

        serviceLooper = thread.getLooper();
        // start the service using the background handler
        serviceHandler = new BaseService.ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Message message = serviceHandler.obtainMessage();
        message.arg1 = startId;
        message.obj = intent;
        serviceHandler.sendMessage(message);

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceLooper.quit();
    }

    protected abstract void onHandleIntent(Intent intent, int startId);

    private final class ServiceHandler extends Handler {

        ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            onHandleIntent((Intent) msg.obj, (int) msg.arg1);
        }
    }
}
