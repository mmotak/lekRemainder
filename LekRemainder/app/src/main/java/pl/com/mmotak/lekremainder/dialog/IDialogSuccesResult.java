package pl.com.mmotak.lekremainder.dialog;

import android.util.Log;

/**
 * Created by mmotak on 02.11.2018.
 */
public abstract class IDialogSuccesResult<T> implements IDialogResult<T> {
    @Override
    public void onFail() {
        // empty line here
        Log.w("IDialogSuccesResult", "onFail");
    }
}
