package pl.com.mmotak.lekremainder.ui;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Maciej on 2017-01-06.
 */
public class LekRemainderToastProvider implements IToastProvider {
    @Override
    public void show(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
