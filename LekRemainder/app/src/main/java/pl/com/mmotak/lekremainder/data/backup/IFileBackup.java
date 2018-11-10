package pl.com.mmotak.lekremainder.data.backup;

import java.io.File;

import rx.Observable;

/**
 * Created by Maciej on 2017-09-30.
 */

public interface IFileBackup {
    Observable<File> saveHistory();

    Observable<File> saveConfig();

    Observable<Boolean> loadConfig();
}
