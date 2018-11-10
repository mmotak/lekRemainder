package pl.com.mmotak.lekremainder;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import java.io.File;

public class LekFileProvider extends FileProvider {
    private static final String AUTHORITY = "pl.com.mmotak.lekremainder.fileprovider";

    public static Uri getUriForFile(Context context, File file) {
        return LekFileProvider.getUriForFile(context, AUTHORITY, file);
    }
}
