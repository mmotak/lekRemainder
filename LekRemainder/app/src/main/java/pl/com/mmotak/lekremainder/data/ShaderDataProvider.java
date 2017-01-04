package pl.com.mmotak.lekremainder.data;

import android.content.Context;
import android.content.SharedPreferences;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import pl.com.mmotak.lekremainder.lekapp.LekRemainderApplication;

/**
 * Created by Maciej on 2016-12-28.
 */

public class ShaderDataProvider implements ISharedDateProvider {
    private static final String APP_KEY = "SP" + LekRemainderApplication.class.getSimpleName();
    private static final String NEXT_RESET_KEY = "NEXT_RESET_KEY";
    private static final String H_RESET_KEY = "H_RESET_KEY";
    private static final String M_RESET_KEY = "M_RESET_KEY";
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
        editor.putLong(NEXT_RESET_KEY, dateTimeInLong);
        editor.commit();
    }

    @Override
    public void removeNextResetDateTime() {
        SharedPreferences.Editor editor = getEditor();
        editor.remove(NEXT_RESET_KEY);
        editor.commit();
    }

    @Override
    public LocalTime loadResetTimeasLocalTime() {
        int h = getSharedPreferences().getInt(H_RESET_KEY, 6);
        int m = getSharedPreferences().getInt(M_RESET_KEY, 0);
        return new LocalTime(h, m);
    }

    @Override
    public void saveResetTime(LocalTime localTime) {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(H_RESET_KEY, localTime.getHourOfDay());
        editor.putInt(M_RESET_KEY, localTime.getMinuteOfHour());
        editor.commit();
    }

    @Override
    public DateTime getTomorrowRestartDateTime() {
        LocalTime localTime = loadResetTimeasLocalTime();
        return localTime.toDateTimeToday().plusDays(1);
    }

    @Override
    public DateTime getTodayRestartDateTime() {
        LocalTime localTime = loadResetTimeasLocalTime();
        return localTime.toDateTimeToday();
    }
}
