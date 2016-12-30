package pl.com.mmotak.lekremainder.data;

import org.joda.time.DateTime;

import java.util.List;

import pl.com.mmotak.lekremainder.entities.DbDose;
import pl.com.mmotak.lekremainder.entities.DbHistory;
import pl.com.mmotak.lekremainder.models.Drug;
import pl.com.mmotak.lekremainder.models.TodayDose;
import rx.Observable;

/**
 * Created by mmotak on 25.11.2016.
 */

public interface IDataProvider {

    String getName();

    void addNewDrug(Drug drug);

    Drug getDrugById(Integer id);

    rx.Observable<List<Drug>> getDrugsObservable();

    Observable<List<TodayDose>> getObservableForNotTakenTodayDoseAfterDateTime(DateTime dateTime);

    rx.Observable<List<TodayDose>> getTodayDosesObservable();

    void saveHistory(String name, int doseId, DateTime time);

    void updateTodayDose(TodayDose todayDose);

    void RemoveDrug(int id);

    rx.Observable<List<DbHistory>> getAllHistoryObservable();

    void removeAllTodayDoses();

    boolean isDrugTableEmpty();
}
