package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.activities.Henson;
import pl.com.mmotak.lekremainder.adapters.DrugsAdapter;
import pl.com.mmotak.lekremainder.data.IDataProvider;
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
        view.getContext().startActivity(Henson.with(view.getContext())
                .gotoSingleDrugActivity()
                //.drugID(0)
                .build()
        );
        //view.getContext().startActivity(new Intent(view.getContext(), NewDrugActivity.class));
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    private void subscribe() {
        subscription = dataProvider.getObservable()
                .subscribe(new Subscriber<List<Drug>>() {
            @Override public void onCompleted() {
                Log.d("DrugsViewModel", "onCompleted");
            }

            @Override public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override public void onNext(List<Drug> drugs) {
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

    @Override
    public void subscribeOnResume() {
        subscribe();
    }

    @Override
    public void unSubscribeOnPause() {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
