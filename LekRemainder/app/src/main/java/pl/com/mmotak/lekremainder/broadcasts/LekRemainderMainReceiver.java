package pl.com.mmotak.lekremainder.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pl.com.mmotak.lekremainder.services.TodayDoseResetService;

public class LekRemainderMainReceiver extends BroadcastReceiver {

    public LekRemainderMainReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, TodayDoseResetService.class));
    }
}
