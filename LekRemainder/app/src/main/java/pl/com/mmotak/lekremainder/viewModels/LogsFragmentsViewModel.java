package pl.com.mmotak.lekremainder.viewModels;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.hypertrack.hyperlog.HyperLog;

import java.util.Collections;
import java.util.List;

import pl.com.mmotak.lekremainder.adapters.LogsAdapter;
import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;

public class LogsFragmentsViewModel extends AbstractBaseViewModel {
    private static final ILogger LOGGER = LekLogger.create(LogsFragmentsViewModel.class.getSimpleName());

    private LogsAdapter adapter;

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public LogsFragmentsViewModel(Activity baseActivity) {
        super(baseActivity);
        adapter = new LogsAdapter();
    }

    @Override
    public void subscribeOnResume() {
        List<String> list = HyperLog.getDeviceLogsAsStringList(false);
        Collections.reverse(list);
        list.add(""); // add empty item to scroll hack
        adapter.setList(list);
    }

    @Override
    public void unSubscribeOnPause() {

    }

    @Override
    public void onDestroy() {

    }
}
