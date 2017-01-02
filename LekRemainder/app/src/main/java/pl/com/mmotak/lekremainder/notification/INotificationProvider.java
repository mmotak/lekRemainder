package pl.com.mmotak.lekremainder.notification;

import java.util.List;

import pl.com.mmotak.lekremainder.models.TodayDose;

/**
 * Created by Maciej on 2016-12-29.
 */
public interface INotificationProvider {
    void show(TodayDose todayDose, boolean playSound);

    void show(List<TodayDose> todayDoses, boolean playSound);
}