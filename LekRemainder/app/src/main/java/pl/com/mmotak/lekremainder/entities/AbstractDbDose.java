package pl.com.mmotak.lekremainder.entities;

import android.databinding.Observable;
import android.os.Parcelable;

import org.joda.time.LocalTime;

import io.requery.Column;
import io.requery.Convert;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.ManyToOne;
import io.requery.Persistable;
import pl.com.mmotak.lekremainder.converters.LocalTimeConverter;

/**
 * Created by mmotak on 06.12.2016.
 */

@Entity
public abstract class AbstractDbDose implements Parcelable {

    @Key @Generated
    int id;

    @Column(length = 6)
    @Convert(LocalTimeConverter.class)
    LocalTime time;

    @ManyToOne
    AbstractDbDrug dbDrug;
}
