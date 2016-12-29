package pl.com.mmotak.lekremainder.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import pl.com.mmotak.lekremainder.services.TodayDoseResetService;

public class LekRemainderMainReceiver extends BroadcastReceiver {

    public static final String KEY = "ID_SERVICE_CLASS";

    public LekRemainderMainReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extra = intent.getExtras();
        Class<?> serviceClass = (Class<?>) extra.getSerializable(KEY);

        context.startService(new Intent(context, serviceClass));
    }
}
