package pl.com.mmotak.lekremainder.data;

import pl.com.mmotak.lekremainder.models.DrugOld;

/**
 * Created by mmotak on 25.11.2016.
 */

public interface IDataProvider {

    String getName();

    void addNewDrug(DrugOld drug);

    rx.Observable<DrugOld> getObservable();
}
