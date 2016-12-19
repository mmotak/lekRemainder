package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.adapters.TodayDoseAdapter;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.models.TodayDose;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by mmotak on 16.12.2016.
 */

public class TodayDoseViewModel extends AbstractBaseViewModel {

    @Inject
    IDataProvider dataProvider;
    private TodayDoseAdapter adapter;
    private Subscription subscription;

    public TodayDoseViewModel(Activity baseActivity) {
        super(baseActivity);
        getDiComponent().inject(this);
        adapter = new TodayDoseAdapter(dataProvider);
        subscribe();
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    private void subscribe() {
        if (subscription != null) {
            return;
        }
        subscription = dataProvider.getTodayDosesObservable()
                .subscribe(new Subscriber<List<TodayDose>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("DrugsViewModel", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<TodayDose> doses) {
                        adapter.setList(doses);
                    }
                });
    }

    private void unsubscribe() {
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
        unsubscribe();
    }

    @Override
    public void onDestroy() {
        unsubscribe();
    }
}
