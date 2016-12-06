package pl.com.mmotak.lekremainder.data;

import java.util.ArrayList;
import java.util.List;

import pl.com.mmotak.lekremainder.models.DrugOld;
import rx.subjects.BehaviorSubject;

/**
 * Created by mmotak on 25.11.2016.
 */

public class MemoryDataProvider implements IDataProvider {

    private List<DrugOld> drugs = new ArrayList<>();
    private BehaviorSubject<DrugOld> drugBehaviorSubject = BehaviorSubject.create();

    @Override public String getName() {
        return MemoryDataProvider.class.getName();
    }

    @Override public void addNewDrug(DrugOld drug) {
        drugs.add(drug);
        drugBehaviorSubject.onNext(drug);
    }

    @Override public rx.Observable<DrugOld> getObservable() {
        return drugBehaviorSubject.asObservable();
    }

}
