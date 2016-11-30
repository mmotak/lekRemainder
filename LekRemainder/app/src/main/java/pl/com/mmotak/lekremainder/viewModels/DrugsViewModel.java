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
    public ObservableArrayList<Drug> drugs = new ObservableArrayList<>();

    private Subscription subscription;

    public DrugsViewModel(Activity baseActivity) {
        super(baseActivity);
        getDiComponent().inject(this);
        subscribe();
    }

    public void onAddNewDrugClick(View view) {
        getBaseActivity().startActivity(new Intent(getBaseActivity(), NewDrugActivity.class));
    }

    public RecyclerView.Adapter getAdapter() {
        return new DrugsAdapter(getBaseActivity(), drugs);
    }

    private void subscribe() {
        subscription = dataProvider.getObservable().subscribe(new Subscriber<Drug>() {
            @Override public void onCompleted() {
                recyclerViewVisibility.set(View.VISIBLE);
            }

            @Override public void onError(Throwable e) {
                recyclerViewVisibility.set(View.INVISIBLE);
            }

            @Override public void onNext(Drug drug) {
                drugs.add(drug);
            }
        });
    }

    public void onDestroy() {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
