package pl.com.mmotak.lekremainder.converters.models;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import pl.com.mmotak.lekremainder.entities.DbHistory;
import pl.com.mmotak.lekremainder.models.History;

/**
 * Created by Maciej on 2017-01-06.
 */

public class HistoryConverter {

    public static List<History> toHistoryList(List<DbHistory> dbHistories) {
        List<History> list = new ArrayList<>();

        boolean isHeaderVisible = true;
        DateTime last = null;
        for (DbHistory dbHistory : dbHistories) {

            isHeaderVisible = last == null || !areTheSameDay(last, dbHistory.getTime());
            last = dbHistory.getTime();

            list.add(new History(
                    dbHistory.getId(),
                    dbHistory.getDoseId(),
                    dbHistory.getName(),
                    dbHistory.getTime(),
                    isHeaderVisible
            ));
        }

        return list;
    }


    private static boolean areTheSameDay(DateTime last, DateTime current) {
       boolean v = last.toLocalDate().equals(current.toLocalDate());
        return v;
    }

}
