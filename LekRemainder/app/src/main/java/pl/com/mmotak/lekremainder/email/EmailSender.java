package pl.com.mmotak.lekremainder.email;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import pl.com.mmotak.lekremainder.LekFileProvider;
import pl.com.mmotak.lekremainder.R;

public class EmailSender {
    public static void sendFileToEmail(Context context, String subject, File file) {
        context.startActivity(Intent.createChooser(createEmailIntent(context, subject, file), context.getString(R.string.send_email)));
    }

    private static Intent createEmailIntent(Context context, String subject, File file) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("vnd.android.cursor.dir/email");
        emailIntent.putExtra(Intent.EXTRA_STREAM, createUri(context, file));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return emailIntent;
    }

    private static Uri createUri(Context context, File file) {
        // nice issue: https://stackoverflow.com/questions/38200282/android-os-fileuriexposedexception-file-storage-emulated-0-test-txt-exposed
        //return Uri.fromFile(file);
        return LekFileProvider.getUriForFile(context, file);
    }
}
