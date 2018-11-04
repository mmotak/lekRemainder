package pl.com.mmotak.lekremainder.notification;

import android.annotation.TargetApi;
import android.app.Notification;
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
    // This name is important! LOL https://stackoverflow.com/questions/50567164/custom-notification-sound-not-working-in-oreo
    private static final String ALARM_CHANNEL_ID = "DrugRemainderAlarmID";
    private static final String NOTIFICATION_CHANNEL_ID = "DrugRemainderNotificationID";
    private static final String ALARM_CHANNEL_NAME = "DrugRemainderAlarm";
    private static final String NOTIFICATION_CHANNEL_NAME = "DrugRemainderNotification";

    private static final String NEXT_DRUG_CHANNEL_ID = "NextDrugID";
    private static final String NEXT_DRUG_CHANNEL_NAME = "NextDrugService";
    private static final String RESET_CHANNEL_ID = "ResetDrugID";
    private static final String RESET_CHANNEL_NAME = "ResetDrugService";


    private static final int TAKE_DRUG_ID = 1;
    private static final int NEXT_DRUG_SERVICE_ID = 2;
    private static final int RESET_SERVICE_ID = 3;

    private Context context;
    private NotificationManager notificationManager;

    public LekRemainderNotificationManager(Context context) {
        this.context = context;
        createChannels();
    }

    private NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
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
    public void show(TodayDose todayDose, boolean isAlarmType) {
        Log.d(TAG, "show " + todayDose.toString() + " play is alarm type " + isAlarmType);
        showSingleNotification(todayDose, isAlarmType);
    }

    @Override
    public void show(List<TodayDose> todayDoses, boolean isAlarmType) {
        Log.d(TAG, "show " + todayDoses.size() + " play is alarm type " + isAlarmType);
        if (todayDoses == null || todayDoses.isEmpty()) {
            hideAllNotifications();
        } else {
            showBigNotification(todayDoses, isAlarmType);
        }
    }

    @Override
    public int getResetId() {
        return RESET_SERVICE_ID;
    }

    @Override
    public Notification getResetNotification() {
        return createNotification(RESET_CHANNEL_ID, "Reset the drugs for next day");
    }

    @Override
    public int getNextDoseId() {
        return NEXT_DRUG_SERVICE_ID;
    }

    @Override
    public Notification getNextDoseNotification() {
        return createNotification(NEXT_DRUG_CHANNEL_ID, "Calculating drugs");
    }

    private Notification createNotification(String channelId, String message) {
        return new NotificationCompat.Builder(context, channelId)
                .setContentTitle("Lek Remainder in progress")
                .setContentText(message)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.ic_notification)
                .setCategory(NotificationCompat.CATEGORY_PROGRESS)
                .setChannelId(channelId)
                .setContentIntent(createPendingIntent())
                .build();
    }

    @TargetApi(Build.VERSION_CODES.N)
    private int getImportance(boolean isAlarmType) {
        return isAlarmType ? NotificationManager.IMPORTANCE_HIGH : NotificationManager.IMPORTANCE_DEFAULT;
    }

    private int getPriority(boolean isAlarmType) {
        return isAlarmType ? NotificationCompat.PRIORITY_HIGH : NotificationCompat.PRIORITY_DEFAULT;
    }

    private String getChannelName(boolean isAlarmType) {
        return isAlarmType ? ALARM_CHANNEL_NAME : NOTIFICATION_CHANNEL_NAME;
    }

    private String getChannelId(boolean isAlarmType) {
        return isAlarmType ? ALARM_CHANNEL_ID : NOTIFICATION_CHANNEL_ID;
    }

    private Uri getSound(boolean isAlarmType) {
        return RingtoneManager.getDefaultUri(isAlarmType ? RingtoneManager.TYPE_ALARM : RingtoneManager.TYPE_NOTIFICATION);
    }

    private NotificationCompat.Builder createBaseBuilder(boolean isAlarmType) {
        return new NotificationCompat.Builder(context, getChannelId(isAlarmType))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(getPriority(isAlarmType))
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setChannelId(getChannelId(isAlarmType))
                .setSound(getSound(isAlarmType));
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

    private void showBigNotification(List<TodayDose> todayDoses, boolean isAlarmType) {
        NotificationCompat.Builder builder = createBaseBuilder(isAlarmType)
                .setContentTitle("Drugs to take: " + todayDoses.size())
                .setContentText("Drugs to take: " + todayDoses.size())
                .setContentIntent(createPendingIntent())
                .setStyle(createInboxStyle(todayDoses, isAlarmType));
        showNotifications(builder);
    }

    private void showSingleNotification(TodayDose todayDose, boolean isAlarmType) {
        NotificationCompat.Builder builder = createBaseBuilder(isAlarmType)
                .setContentTitle(todayDose.getDrugName())
                .setContentText(todayDose.getEstimatedDateTime().toString(context.getString(R.string.time_format)))
                .setContentIntent(createPendingIntent());
        showNotifications(builder);
    }

    private void createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            removePreviousChannels();
            getNotificationManager().createNotificationChannel(getNotificationChannel(true));
            getNotificationManager().createNotificationChannel(getNotificationChannel(false));
            getNotificationManager().createNotificationChannel(getNotificationChannel(NEXT_DRUG_CHANNEL_ID, NEXT_DRUG_CHANNEL_NAME));
            getNotificationManager().createNotificationChannel(getNotificationChannel(RESET_CHANNEL_ID, RESET_CHANNEL_NAME));
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void removePreviousChannels() {
        NotificationManager notificationManager = getNotificationManager();
        List<NotificationChannel> channelList = notificationManager.getNotificationChannels();

        for (int i = 0; channelList != null && i < channelList.size(); i++) {
            notificationManager.deleteNotificationChannel(channelList.get(i).getId());
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private NotificationChannel getNotificationChannel(String channelId, String channelName) {
        NotificationChannel notificationChannel = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setImportance(NotificationManager.IMPORTANCE_DEFAULT);

        return notificationChannel;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private NotificationChannel getNotificationChannel(boolean isAlarmType) {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();

        NotificationChannel notificationChannel = new NotificationChannel(getChannelId(isAlarmType),
                getChannelName(isAlarmType), getImportance(isAlarmType));
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLightColor(Color.BLUE);
        notificationChannel.setImportance(getImportance(isAlarmType));
        notificationChannel.setSound(getSound(isAlarmType), attributes);

        return notificationChannel;
    }

    private void showNotifications(NotificationCompat.Builder builder) {
        getNotificationManager().notify(TAKE_DRUG_ID, builder.build());
    }
}
