package pl.com.mmotak.lekremainder.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.text.Html;

import java.util.List;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.models.TodayDose;

/**
 * Created by Maciej on 2016-12-29.
 */

public class LekRemainderNotificationManager implements INotificationProvider {

    private static final int ID = 1;
    private Context context;

    public LekRemainderNotificationManager(Context context) {
        this.context = context;
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
    }

    private void hideAllNotifications() {
       getNotificationManager().cancelAll();
    }

    @Override
    public void show(TodayDose todayDose) {
        showSingleNotification(todayDose);

    }

    @Override
    public void show(List<TodayDose> todayDoses) {

        if (todayDoses != null && !todayDoses.isEmpty()) {
            if (todayDoses.size() > 1) {
                showBigNotification(todayDoses);
            } else {
                showSingleNotification(todayDoses.get(0));
            }
        }
    }

    private void showBigNotification(List<TodayDose> todayDoses) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.clock)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentTitle("Drugs to take: " + todayDoses.size())
                .setContentText("Hmm... write here some text");
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Drugs to take: " + todayDoses.size());

        for (TodayDose todayDose : todayDoses) {
            inboxStyle.addLine(todayDose.getDrugName() + ": " + todayDose.getEstimatedDateTime().toString(context.getString(R.string.time_format)));
        }
        builder.setStyle(inboxStyle);
        builder.setNumber(todayDoses.size());

        getNotificationManager().notify(ID, builder.build());
    }

    private void showSingleNotification(TodayDose todayDose) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.clock)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentTitle(todayDose.getDrugName())
                .setContentText(todayDose.getEstimatedDateTime().toString(context.getString(R.string.time_format)));

        getNotificationManager().notify(ID, builder.build());
    }
}
