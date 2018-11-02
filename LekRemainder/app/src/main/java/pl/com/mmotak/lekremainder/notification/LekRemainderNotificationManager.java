package pl.com.mmotak.lekremainder.notification;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.List;

import pl.com.mmotak.lekremainder.BuildConfig;
import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.activities.MainActivity;
import pl.com.mmotak.lekremainder.models.TodayDose;

/**
 * Created by Maciej on 2016-12-29.
 */

public class LekRemainderNotificationManager implements INotificationProvider {

    private static final String TAG = "NotificationManager";
    private static final String CHANNEL_ID = "Drug_Remainder_Notification1"; // This name is important! LOL https://stackoverflow.com/questions/50567164/custom-notification-sound-not-working-in-oreo
    private static final String CHANNEL_NAME = "DrugRemainders1";
    private static final int ID = 1;
    private Context context;

    public LekRemainderNotificationManager(Context context) {
        this.context = context;
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void hideAllNotifications() {
        getNotificationManager().cancelAll();
    }

    private PendingIntent createPendingIntent() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void show(TodayDose todayDose, boolean playSound) {
        Log.d(TAG, "show " + todayDose.toString() + " play " + playSound);
        showSingleNotification(todayDose, playSound);
    }

    @Override
    public void show(List<TodayDose> todayDoses, boolean playSound) {
        Log.d(TAG, "show " + todayDoses.size() + " play " + playSound);
        if (todayDoses == null || todayDoses.isEmpty()) {
            hideAllNotifications();
        } else {
            showBigNotification(todayDoses, playSound);
        }
    }

    private Uri getSound(boolean playSound) {
        return RingtoneManager.getDefaultUri(playSound ? RingtoneManager.TYPE_ALARM : RingtoneManager.TYPE_NOTIFICATION);
    }

    private NotificationCompat.Builder createBaseBuilder(boolean playSound) {
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setChannelId(CHANNEL_ID)
                .setSound(getSound(playSound));
    }

    private NotificationCompat.InboxStyle createInboxStyle(List<TodayDose> todayDoses, boolean playSound) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Drugs to take: " + todayDoses.size());

        if (BuildConfig.DEBUG) {
            inboxStyle.addLine("show " + todayDoses.size() + " play " + playSound);
            inboxStyle.addLine("NOW: " + DateTime.now().toString());
        }

        for (TodayDose todayDose : todayDoses) {
            inboxStyle.addLine(todayDose.getDrugName() + ": " + todayDose.getEstimatedDateTime().toString(context.getString(R.string.time_format)));
        }

        return inboxStyle;
    }

    private void showBigNotification(List<TodayDose> todayDoses, boolean playSound) {
        NotificationCompat.Builder builder = createBaseBuilder(playSound)
                .setContentTitle("Drugs to take: " + todayDoses.size())
                .setContentText("Drugs to take: " + todayDoses.size())
                .setContentIntent(createPendingIntent())
                .setStyle(createInboxStyle(todayDoses, playSound));
        showNotifications(builder, playSound);
    }

    private void showSingleNotification(TodayDose todayDose, boolean playSound) {
        NotificationCompat.Builder builder = createBaseBuilder(playSound)
                .setContentTitle(todayDose.getDrugName())
                .setContentText(todayDose.getEstimatedDateTime().toString(context.getString(R.string.time_format)))
                .setContentIntent(createPendingIntent());
        showNotifications(builder, playSound);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private NotificationChannel getNotificationChannel(boolean playSound) {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();

        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLightColor(Color.BLUE);
        notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setSound(getSound(playSound), attributes);

        return notificationChannel;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void removePreviousChannels() {
        NotificationManager notificationManager = getNotificationManager();
        List<NotificationChannel> channelList = notificationManager.getNotificationChannels();

        for (int i = 0; channelList != null && i < channelList.size(); i++) {
            notificationManager.deleteNotificationChannel(channelList.get(i).getId());
        }
    }

    private void showNotifications(NotificationCompat.Builder builder, boolean playSound) {
        NotificationManager notificationManager = getNotificationManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //removePreviousChannels();
            notificationManager.createNotificationChannel(getNotificationChannel(playSound));
        }
        notificationManager.notify(ID, builder.build());
    }
}
