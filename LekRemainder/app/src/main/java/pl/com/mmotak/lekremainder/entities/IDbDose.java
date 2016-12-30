package pl.com.mmotak.lekremainder.entities;

import android.databinding.Observable;
import android.os.Parcelable;

import org.joda.time.LocalTime;

import io.requery.CascadeAction;
import io.requery.Column;
import io.requery.Convert;
import io.requery.Entity;
import io.requery.ForeignKey;
import io.requery.Generated;
import io.requery.Key;
import io.requery.ManyToOne;
import io.requery.OneToOne;
import io.requery.Persistable;
import pl.com.mmotak.lekremainder.converters.LocalTimeConverter;

/**
 * Created by mmotak on 06.12.2016.
 */

@Entity
public interface IDbDose extends Observable, Parcelable, Persistable {

    @Key @Generated
    int getId();

    @Column(length = 6)
    @Convert(LocalTimeConverter.class)
    LocalTime getTime();

    int getShiftInDays();

    @ManyToOne
    IDbDrug getDbDrug();

    @ForeignKey
    @OneToOne(cascade = {CascadeAction.NONE})
    IDbTakeDose getDbTakeDose();
}
