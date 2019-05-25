package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.adapters.HistorySimpleAdapter;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;
import pl.com.mmotak.lekremainder.models.History;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Maciej on 2016-12-27.
 */

public class HistoryFragmentViewModel extends AbstractBaseViewModel {
    private static final ILogger LOGGER = LekLogger.create(HistoryFragmentViewModel.class.getSimpleName());

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

        subscription = dataProvider.getAllLastHistoryObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<History>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LOGGER.e(e.getMessage(), e);
            }

            @Override
            public void onNext(List<History> histories) {
                adapter.setList(histories);
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
