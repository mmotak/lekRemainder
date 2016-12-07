package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import io.requery.query.Result;
import pl.com.mmotak.lekremainder.activities.NewDrugActivity;
import pl.com.mmotak.lekremainder.adapters.DrugsAdapter;
import pl.com.mmotak.lekremainder.converters.DrugConverter;
import pl.com.mmotak.lekremainder.data.DataBaseProvider;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.entities.DbDrug;
import pl.com.mmotak.lekremainder.models.Drug;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by mmotak on 29.11.2016.
 */

public class DrugsViewModel extends AbstractBaseViewModel {

    @Inject
    IDataProvider dataProvider;

    public ObservableInt recyclerViewVisibility = new ObservableInt(View.VISIBLE);

    private Subscription subscription;
    private DrugsAdapter adapter;

    public DrugsViewModel(Activity baseActivity) {
        super(baseActivity);
        getDiComponent().inject(this);
        adapter = new DrugsAdapter(getBaseActivity());
        subscribe();
    }

    public void onAddNewDrugClick(View view) {
        getBaseActivity().startActivity(new Intent(getBaseActivity(), NewDrugActivity.class));
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    private void subscribe() {
        subscription = dataProvider.getObservable()
                .subscribe(new Subscriber<List<DbDrug>>() {
            @Override public void onCompleted() {
                Log.d("XXX", "onCompleted");
            }

            @Override public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override public void onNext(List<DbDrug> drugs) {
                adapter.setDrugList(drugs);
                //adapter.addDrug(drug);
            }
        });
    }

    public void onDestroy() {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
