package pl.com.mmotak.lekremainder.data.backup;

import rx.Observable;

/**
 * Created by Maciej on 2017-09-30.
 */

public interface IFileBackup {
    rx.Observable<Boolean> saveHistory();

    rx.Observable<Boolean> saveConfig();

    Observable<Boolean> loadConfig();
}
