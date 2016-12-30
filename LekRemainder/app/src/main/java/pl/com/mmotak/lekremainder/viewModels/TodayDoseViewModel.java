package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import pl.com.mmotak.lekremainder.adapters.TodayDoseAdapter;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.models.TodayDose;
import pl.com.mmotak.lekremainder.notification.INotificationProvider;
import pl.com.mmotak.lekremainder.services.NextDoseAlarmService;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by mmotak on 16.12.2016.
 */

public class TodayDoseViewModel extends AbstractBaseViewModel {

    @Inject
    IDataProvider dataProvider;
    @Inject
    INotificationProvider notificationProvider;

    private TodayDoseAdapter adapter;
    private Subscription subscription;

    public TodayDoseViewModel(Activity baseActivity) {
        super(baseActivity);
        getDiComponent().inject(this);
        adapter = new TodayDoseAdapter(baseActivity, dataProvider);
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
                        // getBaseActivity().startService(new Intent(getBaseActivity(), NextDoseAlarmService.class));
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
