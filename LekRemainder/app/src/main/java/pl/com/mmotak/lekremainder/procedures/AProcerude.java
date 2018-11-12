package pl.com.mmotak.lekremainder.procedures;

import android.content.Context;

import pl.com.mmotak.lekremainder.di.DiComponent;
import pl.com.mmotak.lekremainder.lekapp.LekRemainderApplication;

public abstract class AProcerude {
    protected DiComponent getDiCompoment(Context context) {
        return ((LekRemainderApplication)(context.getApplicationContext()))
                .getDiComponent();
    }

    public abstract void doJob(Context context);
}
