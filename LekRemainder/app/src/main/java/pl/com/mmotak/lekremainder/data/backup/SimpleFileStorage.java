package pl.com.mmotak.lekremainder.data.backup;

import android.content.Context;

import com.snatik.storage.Storage;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by Maciej on 2017-09-30.
 */

public class SimpleFileStorage implements PublicFileStorage {

    private final Storage storage;
    private final String mainDirName;
    private String externalPath;
    private String mainDirectoryPath;

    public SimpleFileStorage(Context context, String mainDirName) {
        this.mainDirName = mainDirName;
        this.storage = new Storage(context.getApplicationContext());
        createDir();
    }

    private boolean createDir() {
        externalPath = storage.getExternalStorageDirectory();
        mainDirectoryPath = externalPath + mainDirName;
        return storage.isDirectoryExists(mainDirectoryPath) || storage.createDirectory(mainDirectoryPath);
    }

    private String getFilePath(String name) {
        return mainDirectoryPath + File.separator + name;
    }

    @Override
    public boolean saveFile(String name, String body) {
        return storage.createFile(getFilePath(name), body);
    }

    @Override
    public String loadFile(String name) {
        return storage.readTextFile(getFilePath(name));
    }

    @Override
    public List<File> getAllFiles() {
        List<File> files = storage.getFiles(mainDirectoryPath);
        if (files != null) {
            return files;
        } else {
            return Collections.emptyList();
        }
    }


}
