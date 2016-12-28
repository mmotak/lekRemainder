package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.adapters.HistorySimpleAdapter;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.entities.DbHistory;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Maciej on 2016-12-27.
 */

public class HistoryFragmentViewModel extends AbstractBaseViewModel {

    @Inject
    IDataProvider dataProvider;
    private HistorySimpleAdapter adapter;
    private Subscription subscription;

    public HistoryFragmentViewModel(Activity baseActivity) {
        super(baseActivity);
        getDiComponent().inject(this);
        adapter = new HistorySimpleAdapter();
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @Override
    public void subscribeOnResume() {
        if (subscription != null) {
            return;
        }

        subscription = dataProvider.getAllHistoryObservable().subscribe(new Subscriber<List<DbHistory>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(List<DbHistory> dbHistories) {
                adapter.setList(dbHistories);
            }
        });
    }

    private void unSubscribe() {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void unSubscribeOnPause() {
        unSubscribe();
    }

    @Override
    public void onDestroy() {
        unSubscribe();
    }
}
