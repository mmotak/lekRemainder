package pl.com.mmotak.lekremainder.data;

import pl.com.mmotak.lekremainder.models.Drug;

/**
 * Created by mmotak on 25.11.2016.
 */

public interface IDataProvider {

    String getName();

    void addNewDrug(Drug drug);

    rx.Observable<Drug> getObservable();
}
