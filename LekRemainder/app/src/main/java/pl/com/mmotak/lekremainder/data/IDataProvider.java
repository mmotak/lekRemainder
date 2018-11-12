package pl.com.mmotak.lekremainder.data;

import org.joda.time.DateTime;

import java.util.List;

import pl.com.mmotak.lekremainder.models.Drug;
import pl.com.mmotak.lekremainder.models.History;
import pl.com.mmotak.lekremainder.models.TodayDose;
import rx.Observable;

/**
 * Created by mmotak on 25.11.2016.
 */

public interface IDataProvider {

    String getName();

    void addNewDrug(Drug drug);

    Drug getDrugById(Integer id);

    Observable<List<Drug>> getDrugsObservable();

    Observable<List<TodayDose>> getObservableForNotTakenTodayDoseAfterDateTime();

    Observable<List<TodayDose>> getTodayDosesObservable();

    void saveHistory(String name, int doseId, DateTime time);

    void updateTodayDose(TodayDose todayDose);

    void removeDrug(int id);

    Observable<List<History>> getAllHistoryObservable();

    Observable<List<History>> getAllLastHistoryObservable();

    void removeAllTodayDoses();

    boolean isDrugTableEmpty();
}
