package pl.com.mmotak.lekremainder.data;

import android.content.Context;
import android.content.SharedPreferences;

import pl.com.mmotak.lekremainder.lekapp.LekRemainderApplication;

/**
 * Created by Maciej on 2016-12-28.
 */

public class ShaderDataProvider implements ISharedDateProvider {
    private static final String APP_KEY = "SP" + LekRemainderApplication.class.getSimpleName();
    private static final String RESET_KEY = "RESET_KEY";
    private Context context;

    public ShaderDataProvider(Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }

    @Override
    public long loadReset() {
        return getSharedPreferences().getLong(RESET_KEY, 0);
    }

    @Override
    public void saveReset(long dateTimeInLong) {
        SharedPreferences.Editor editor = getEditor();
        editor.putLong(RESET_KEY,dateTimeInLong);
        editor.commit();
    }

    @Override
    public void removeReset() {
        SharedPreferences.Editor editor = getEditor();
        editor.remove(RESET_KEY);
        editor.commit();
    }
}
