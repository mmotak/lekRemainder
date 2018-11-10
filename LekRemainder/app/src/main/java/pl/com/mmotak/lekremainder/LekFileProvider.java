package pl.com.mmotak.lekremainder;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import java.io.File;

public class LekFileProvider extends FileProvider {
    public static Uri getUriForFile(Context context, File file) {
        LekFileProvider.getUriForFile(context,
                context.getApplicationContext().getPackageName() + "."
                        //+".my.package.name.provider",
                + LekFileProvider.class.getName(),
                file);
        return null;
    }
}
