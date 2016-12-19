package pl.com.mmotak.lekremainder.data;

import android.content.Context;

import org.joda.time.DateTime;

import java.util.List;

import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.query.Result;
import io.requery.rx.RxSupport;
import io.requery.rx.SingleEntityStore;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import pl.com.mmotak.lekremainder.BuildConfig;
import pl.com.mmotak.lekremainder.converters.DoseConverter;
import pl.com.mmotak.lekremainder.converters.DrugConverter;
import pl.com.mmotak.lekremainder.entities.DbDose;
import pl.com.mmotak.lekremainder.entities.DbDrug;
import pl.com.mmotak.lekremainder.entities.DbHistory;
import pl.com.mmotak.lekremainder.entities.DbTakeDose;
import pl.com.mmotak.lekremainder.entities.IDbDose;
import pl.com.mmotak.lekremainder.entities.IDbTakeDose;
import pl.com.mmotak.lekremainder.entities.Models;
import pl.com.mmotak.lekremainder.models.Dose;
import pl.com.mmotak.lekremainder.models.Drug;
import pl.com.mmotak.lekremainder.models.TodayDose;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
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
    public void addNewDrug(Drug drug) {
        if (drug == null) {
            throw new NullPointerException("New IDbDrug cannot be NULL!");
        }

        getData().findByKey(DbDrug.class, drug.getId())
                .flatMap(dbDrug ->
                {
                    if (dbDrug == null) {
                        // new one
                        return getData().insert(DrugConverter.toDbDrug(drug));
                    } else {
                        // update existing
                        DbDrug dbDrugToUpdate = DrugConverter.toDbDrug(drug, dbDrug);
                        for (IDbDose idbDose : dbDrugToUpdate.getDbDoses()) {
                            if (idbDose.getId() == 0) {
                                getData().insert(idbDose).subscribe();
                            } else {
                                getData().update(idbDose).subscribe();
                            }
                        }
                        return getData().update(dbDrugToUpdate);
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DbDrug>() {
                    @Override
                    public void onCompleted() {
                        getData() // remove doses with no parent
                                .select(DbDose.class)
                                .where(DbDose.DB_DRUG.isNull())
                                .get()
                                .each(dbDose -> getData()
                                        .delete(dbDose)
                                        .subscribeOn(Schedulers.io())
                                        .subscribe()
                                )
                        ;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(DbDrug dbDrug) {

                    }
                });
    }

    @Override
    public Drug getDrugById(Integer id) {

        DbDrug dbDrug = getData()
                .select(DbDrug.class)
                .where(DbDrug.ID.eq(id))
                .get()
                .firstOrNull();

        return dbDrug == null ? createNewDrug() : DrugConverter.toDrug(dbDrug);

    }

    @Override
    public Observable<List<Drug>> getDrugsObservable() {
        return getData()
                .select(DbDrug.class)
                .orderBy(DbDrug.NAME.asc())
                .get()
                .toSelfObservable()
                .map(Result::toList)
                .map(DrugConverter::toDrugs)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    @Override
    public Observable<List<TodayDose>> getTodayDosesObservable() {
        return getData()
                .select(DbDose.class)
                .orderBy(DbDose.TIME.asc())
                .get()
                .toSelfObservable()
                .map(Result::toList)
                .map(DoseConverter::toTodayDoses)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    @Override
    public void saveHistory(String name, int doseId, DateTime time) {
        DbHistory dbHistory = new DbHistory();
        dbHistory.setName(name);
        dbHistory.setDoseId(doseId);
        dbHistory.setTime(time);

        getData().insert(dbHistory).subscribe(new Subscriber<DbHistory>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(DbHistory dbHistory) {

            }
        });
    }

    @Override
    public void updateTodayDose(TodayDose todayDose) {

        getData().findByKey(DbDose.class, todayDose.getId())
                .flatMap(dbDose ->
                {
                    DbDose dbDoseToUpdate = DoseConverter.toDbFullDose(todayDose, dbDose);
                    IDbTakeDose dbTakeDose = dbDoseToUpdate.getDbTakeDose();

                    //getData().insert(dbTakeDose).subscribe();
                    return getData().update(dbDoseToUpdate);
                }).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DbDose>() {
                    @Override
                    public void onCompleted() {
                        getData().select(DbDose.class)
                                .where(DbDose.DB_DRUG_ID.eq(todayDose.getDrug().getId()))
                                .and(DbDose.ID.greaterThan(todayDose.getId()))
                                .get()
                                .each(dbDose ->
                                {
                                    DbTakeDose dbTakeDose = new DbTakeDose();
                                    dbTakeDose.setShift(todayDose.getShift());
                                    dbDose.setDbTakeDose(dbTakeDose);

                                    getData().update(dbDose).subscribe();
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
e.printStackTrace();
                    }

                    @Override
                    public void onNext(DbDose dbDose) {

                    }
                });

//        Observable<DbDose> doseObservable = getData().findByKey(DbDose.class, todayDose.getId()).toObservable();
//        Observable<DbDrug> drugObservable = getData().findByKey(DbDrug.class, todayDose.getDrug().getId()).toObservable();
//
//        Subscription obs = Observable.zip(doseObservable, drugObservable,
//                new Func2<DbDose, DbDrug, Subscription>() {
//            @Override
//            public Subscription call(DbDose dbDose, DbDrug dbDrug) {
//
//                DbDose dbDoseToUpdate = DoseConverter.toDbFullDose(todayDose, dbDose, dbDrug);
//
//                getData().insert(dbDoseToUpdate.getDbTakeDose()).subscribe();
//
//                return getData().update(dbDoseToUpdate).subscribe();
//            }
//        }).subscribeOn(Schedulers.io())
//                .subscribe();
    }

    private Drug createNewDrug() {
        return new Drug();
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
