package pl.com.mmotak.lekremainder.data;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

/**
 * Created by Maciej on 2016-12-28.
 */

public interface ISharedDateProvider {

    long loadNextResetDateTime();
    void saveNextResetDateTime(long dateTimeInLong);
    void removeNextResetDateTime();

    LocalTime loadResetTimeasLocalTime();
    //DateTime loadRasetTimeasDateTime();

    void saveResetTime(LocalTime localTime);

    DateTime getTomorrowRestartDateTime();
    DateTime getTodayRestartDateTime();
}
