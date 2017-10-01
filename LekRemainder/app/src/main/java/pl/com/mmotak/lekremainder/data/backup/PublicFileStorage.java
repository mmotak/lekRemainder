package pl.com.mmotak.lekremainder.data.backup;

import java.io.File;
import java.util.List;

/**
 * Created by Maciej on 2017-09-30.
 */

public interface PublicFileStorage {
    List<File> getAllFiles();

    boolean saveFile(String name, String body);

    String loadFile(String name);
}
