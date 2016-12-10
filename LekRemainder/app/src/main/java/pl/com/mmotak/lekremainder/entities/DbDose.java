package pl.com.mmotak.lekremainder.entities;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.os.Parcelable;

import org.joda.time.LocalTime;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.ManyToOne;
import io.requery.Persistable;

/**
 * Created by mmotak on 06.12.2016.
 */

@Entity
public interface DbDose extends Observable, Parcelable, Persistable {

    @Key @Generated
    int getId();

    LocalTime getTime();

//    @Bindable
//    @ManyToOne
//    DbDrug getDrug();
}
