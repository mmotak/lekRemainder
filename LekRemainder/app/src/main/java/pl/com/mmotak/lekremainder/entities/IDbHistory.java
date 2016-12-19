package pl.com.mmotak.lekremainder.entities;

import android.databinding.Observable;
import android.os.Parcelable;

import org.joda.time.DateTime;

import io.requery.Convert;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Persistable;
import pl.com.mmotak.lekremainder.converters.DateTimeConverter;


/**
 * Created by mmotak on 19.12.2016.
 */

@Entity
public interface IDbHistory extends Observable, Parcelable, Persistable {

    @Key @Generated
    int getId();

    @Convert(DateTimeConverter.class)
    DateTime getTime();

    int getDoseId();

    String getName(); // drugName
}
