package pl.com.mmotak.lekremainder.email;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import pl.com.mmotak.lekremainder.R;

public class EmailSender {
    public static void sendFileToEmail(Context context, String subject, File file) {
        context.startActivity(Intent.createChooser(createEmailIntent(subject, file), context.getString(R.string.send_email)));
    }

    private static Intent createEmailIntent(String subject, File file) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("vnd.android.cursor.dir/email");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, subject);
        return emailIntent;
    }
}
