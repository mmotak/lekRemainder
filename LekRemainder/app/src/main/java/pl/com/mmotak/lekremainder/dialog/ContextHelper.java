package pl.com.mmotak.lekremainder.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

/**
 * Created by Maciej on 2017-01-03.
 */

public class ContextHelper {

    public static Activity getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
