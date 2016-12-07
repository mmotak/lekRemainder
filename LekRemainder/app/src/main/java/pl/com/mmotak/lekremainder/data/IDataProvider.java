package pl.com.mmotak.lekremainder.data;

import java.util.List;

import pl.com.mmotak.lekremainder.models.Drug;

/**
 * Created by mmotak on 25.11.2016.
 */

public interface IDataProvider {

    String getName();

    void addNewDrug(Drug drug);

    rx.Observable<List<Drug>> getObservable();
}
