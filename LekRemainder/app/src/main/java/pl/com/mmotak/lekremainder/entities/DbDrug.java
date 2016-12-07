package pl.com.mmotak.lekremainder.entities;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.util.Set;

import io.requery.Column;
import io.requery.Convert;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.OneToMany;
import io.requery.Persistable;
import pl.com.mmotak.lekremainder.converters.DateTimeConverter;

/**
 * Created by mmotak on 06.12.2016.
 */

@Entity
public interface DbDrug extends Observable, Parcelable, Persistable {

    @Key @Generated
    int getId();

    String getName();
    String getType();
    int getDosesNo();
    int getDosesEveryH();

    @Column(nullable = true, length = 8)
    @Convert(DateTimeConverter.class)
    DateTime getStartDate();
    boolean isStartDateEnable();

    @Column(nullable = true, length = 8)
    @Convert(DateTimeConverter.class)
    DateTime getEndDate();
    boolean isEndDateEnable();

//    @OneToMany(mappedBy = "alarms")
//    Set<DbDose> getDosesNo();
}
