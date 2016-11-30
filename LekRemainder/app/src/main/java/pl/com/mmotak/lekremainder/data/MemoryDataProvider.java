package pl.com.mmotak.lekremainder.data;

import java.util.ArrayList;
import java.util.List;

import pl.com.mmotak.lekremainder.models.Drug;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

/**
 * Created by mmotak on 25.11.2016.
 */

public class MemoryDataProvider implements IDataProvider {

    private List<Drug> drugs = new ArrayList<>();
    private BehaviorSubject<Drug> drugBehaviorSubject = BehaviorSubject.create();

    @Override public String getName() {
        return MemoryDataProvider.class.getName();
    }

    @Override public void addNewDrug(Drug drug) {
        drugs.add(drug);
        drugBehaviorSubject.onNext(drug);
    }

    @Override public rx.Observable<Drug> getObservable() {
        return drugBehaviorSubject.asObservable();
    }

}
