package pl.com.mmotak.lekremainder.data;

/**
 * Created by Maciej on 2016-12-28.
 */

public interface ISharedDateProvider {

    long loadReset();
    void saveReset(long dateTimeInLong);
    void removeReset();
}
