package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.activities.NewDrugActivity;
import pl.com.mmotak.lekremainder.adapters.DrugsAdapter;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.models.Drug;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by mmotak on 29.11.2016.
 */

public class DrugsViewModel extends AbstractBaseViewModel {

    @Inject IDataProvider dataProvider;

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
        subscription = dataProvider.getObservable().subscribe(new Subscriber<Drug>() {
            @Override public void onCompleted() {
            }

            @Override public void onError(Throwable e) {
            }

            @Override public void onNext(Drug drug) {
                adapter.addDrug(drug);
            }
        });
    }

    public void onDestroy() {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
