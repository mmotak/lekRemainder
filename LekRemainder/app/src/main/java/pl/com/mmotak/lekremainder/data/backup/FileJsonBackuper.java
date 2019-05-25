package pl.com.mmotak.lekremainder.data.backup;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.logger.ILogger;
import pl.com.mmotak.lekremainder.logger.LekLogger;
import pl.com.mmotak.lekremainder.models.Dose;
import pl.com.mmotak.lekremainder.models.Drug;
import pl.com.mmotak.lekremainder.models.History;
import rx.Observable;

/**
 * Created by Maciej on 2017-09-30.
 */

public class FileJsonBackuper implements IFileBackup {
    private static final ILogger LOGGER = LekLogger.create(FileJsonBackuper.class.getSimpleName());
    private static final String DRUG_FILE_NAME = "drugs.json";

    private IDataProvider dataProvider;
    private PublicFileStorage simpleFileStorage;
    private JsonAdapter<List<History>> historyJsonAdapter;
    private JsonAdapter<List<Drug>> drugsJsonAdapter;

    public FileJsonBackuper(IDataProvider dataProvider, PublicFileStorage simpleFileStorage) {
        this.dataProvider = dataProvider;
        this.simpleFileStorage = simpleFileStorage;
        createMoshiJsonAdapters();
    }

    @Override
    public Observable<File> saveHistory() {
        return dataProvider.getAllHistoryObservable()
                .map(historyList -> saveHistoryFile(historyList));
    }

    @Override
    public Observable<File> saveConfig() {
        return dataProvider.getDrugsObservable()
                .map(drugs -> saveDrugsFile(drugs));
    }

    @Override
    public Observable<Boolean> loadConfig() {
        return Observable.zip(Observable.from(loadDrugsFile()),
                Observable.interval(1200, TimeUnit.MILLISECONDS),
                (drug, aLong) -> drug)
                .doOnNext(drug -> dataProvider.addNewDrug(drug))
                .toList()
                .map(drugs -> new Boolean(true));
    }

    private List<Drug> loadDrugsFile() {
        String body = simpleFileStorage.loadFile(DRUG_FILE_NAME);
        try {
            return drugsJsonAdapter.fromJson(body);
        } catch (IOException e) {
            LOGGER.e(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private File saveDrugsFile(List<Drug> drugs) {
        String body = drugsJsonAdapter.toJson(drugs);
        return simpleFileStorage.saveFile(DRUG_FILE_NAME, body);
    }

    private File saveHistoryFile(List<History> historyList) {
        DateTime now = DateTime.now();
        String name = "history" + now.toString().replace(":", "") + ".json";
        String body = historyJsonAdapter.toJson(historyList);
        return simpleFileStorage.saveFile(name, body);
    }

    private void createMoshiJsonAdapters() {
        Moshi moshi = new Moshi.Builder()
                .add(DateTime.class, new DateTimeSerializer())
                .add(LocalTime.class, new LocalTimeSerializer())
                .build();
        Type historyListTypes = Types.newParameterizedType(List.class, History.class);
        historyJsonAdapter = moshi.adapter(historyListTypes);

        Type drugsListTypes = Types.newParameterizedType(List.class, Drug.class, Dose.class);
        drugsJsonAdapter = moshi.adapter(drugsListTypes);
    }

}
