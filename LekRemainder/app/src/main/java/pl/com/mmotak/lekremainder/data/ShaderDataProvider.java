package pl.com.mmotak.lekremainder.data;

import android.content.Context;
import android.content.SharedPreferences;

import pl.com.mmotak.lekremainder.lekapp.LekRemainderApplication;

/**
 * Created by Maciej on 2016-12-28.
 */

public class ShaderDataProvider implements ISharedDateProvider {
    private static final String APP_KEY = "SP" + LekRemainderApplication.class.getSimpleName();
    private static final String NEXT_RESET_KEY = "NEXT_RESET_KEY";
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
    public long loadNextResetDateTime() {
        return getSharedPreferences().getLong(NEXT_RESET_KEY, -1);
    }

    @Override
    public void saveNextResetDateTime(long dateTimeInLong) {
        SharedPreferences.Editor editor = getEditor();
        editor.putLong(NEXT_RESET_KEY,dateTimeInLong);
        editor.commit();
    }

    @Override
    public void removeNextResetDateTime() {
        SharedPreferences.Editor editor = getEditor();
        editor.remove(NEXT_RESET_KEY);
        editor.commit();
    }
}
