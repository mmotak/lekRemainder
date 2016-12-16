package pl.com.mmotak.lekremainder.data;

import java.util.List;

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
}
