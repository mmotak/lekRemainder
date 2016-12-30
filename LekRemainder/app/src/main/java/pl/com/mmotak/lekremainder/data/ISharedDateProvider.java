package pl.com.mmotak.lekremainder.data;

/**
 * Created by Maciej on 2016-12-28.
 */

public interface ISharedDateProvider {

    long loadNextResetDateTime();
    void saveNextResetDateTime(long dateTimeInLong);
    void removeNextResetDateTime();
}
