package pl.com.mmotak.lekremainder.data;

import android.content.Context;

import java.util.List;

import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.query.Result;
import io.requery.rx.RxSupport;
import io.requery.rx.SingleEntityStore;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import pl.com.mmotak.lekremainder.BuildConfig;
import pl.com.mmotak.lekremainder.converters.DrugConverter;
import pl.com.mmotak.lekremainder.entities.DbDrug;
import pl.com.mmotak.lekremainder.entities.DbDrugEntity;
import pl.com.mmotak.lekremainder.entities.Models;
import pl.com.mmotak.lekremainder.models.Drug;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    public void addNewDrug(DbDrug drug) {

        if (drug == null) {
            throw new NullPointerException("New DbDrug cannot be NULL!");
        }
        if (drug.getId() == 0) {
            getData().insert(drug).subscribe();
        } else {
            getData().update(drug).subscribe();
        }
    }

    @Override
    public DbDrug getDbDrugById(Integer id) {

        DbDrug dbDrug = getData()
                .select(DbDrug.class)
                .where(DbDrugEntity.ID.eq(id))
                .get()
                .firstOrNull();

        return dbDrug == null ? createNewDrug() : dbDrug;

    }

    @Override
    public Observable<List<DbDrug>> getObservable() {
        return getData()
                .select(DbDrug.class)
                .orderBy(DbDrugEntity.NAME.asc())
                .get()
                .toSelfObservable()
                .map(Result::toList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    private DbDrug createNewDrug() {
        DbDrugEntity drug = new DbDrugEntity();

        drug.setName("");
        drug.setType("");
        drug.setDosesEveryH(4);
        drug.setDosesNo(3);

        return drug;
    }

    private SingleEntityStore<Persistable> getData() {
        if (dataStore == null) {
            // override onUpgrade to handle migrating to a new version
            DatabaseSource source = new DatabaseSource(context, Models.DEFAULT, 1);

            if (BuildConfig.DEBUG) {
                source.setLoggingEnabled(true);

                // use this in development mode to drop and recreate the tables on every upgrade
                //source.setTableCreationMode(TableCreationMode.DROP_CREATE);
            }
            Configuration configuration = source.getConfiguration();

            dataStore = RxSupport.toReactiveStore(
                    new EntityDataStore<Persistable>(configuration));
        }
        return dataStore;
    }
}
