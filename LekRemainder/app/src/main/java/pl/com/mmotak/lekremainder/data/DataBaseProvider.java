package pl.com.mmotak.lekremainder.data;

import android.content.Context;

import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.rx.RxSupport;
import io.requery.rx.SingleEntityStore;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;
import pl.com.mmotak.lekremainder.BuildConfig;
import pl.com.mmotak.lekremainder.entities.Models;
import pl.com.mmotak.lekremainder.models.DrugOld;
import rx.Observable;

/**
 * Created by mmotak on 06.12.2016.
 */

public class DataBaseProvider implements IDataProvider {

    private SingleEntityStore<Persistable> dataStore;
    private Context context;

    public DataBaseProvider(Context context) {
        this.context = context;
    }


    @Override
    public String getName() {
        return DataBaseProvider.class.getName();
    }

    @Override
    public void addNewDrug(DrugOld drug) {

    }

    @Override
    public Observable<DrugOld> getObservable() {
        return null;
    }

    private SingleEntityStore<Persistable> getData() {
        if (dataStore == null) {
            // override onUpgrade to handle migrating to a new version
            DatabaseSource source = new DatabaseSource(context, Models.DEFAULT, 1);
            if (BuildConfig.DEBUG) {
                // use this in development mode to drop and recreate the tables on every upgrade
                source.setTableCreationMode(TableCreationMode.DROP_CREATE);
            }
            Configuration configuration = source.getConfiguration();
            dataStore = RxSupport.toReactiveStore(
                    new EntityDataStore<Persistable>(configuration));
        }
        return dataStore;
    }
}
