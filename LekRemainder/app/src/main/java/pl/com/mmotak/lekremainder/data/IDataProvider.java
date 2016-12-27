package pl.com.mmotak.lekremainder.data;

import org.joda.time.DateTime;

import java.util.List;

import pl.com.mmotak.lekremainder.entities.DbHistory;
import pl.com.mmotak.lekremainder.models.Drug;
import pl.com.mmotak.lekremainder.models.TodayDose;

/**
 * Created by mmotak on 25.11.2016.
 */

public interface IDataProvider {

    String getName();

    void addNewDrug(Drug drug);

    Drug getDrugById(Integer id);

    rx.Observable<List<Drug>> getDrugsObservable();

    rx.Observable<List<TodayDose>> getTodayDosesObservable();

    void saveHistory(String name, int doseId, DateTime time);

    void updateTodayDose(TodayDose todayDose);

    void RemoveDrug(int id);

    rx.Observable<List<DbHistory>> getAllHistory();
}
