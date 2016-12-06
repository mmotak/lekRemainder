package pl.com.mmotak.lekremainder.entities;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.util.Set;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.OneToMany;
import io.requery.Persistable;

/**
 * Created by mmotak on 06.12.2016.
 */

@Entity
public interface Drug extends Observable, Parcelable, Persistable {

    @Key @Generated
    int getId();

    @Bindable
    String getName();

    @Bindable
    String getType();

    @Bindable
    int getDosesNo();

    @Bindable
    int getDosesEveryH();

    @Bindable
    DateTime getStartDate();

    @Bindable
    boolean isStartDateEnable();

    @Bindable
    DateTime getEndDate();

    @Bindable
    boolean isEndDateEnable();

//    @OneToMany(mappedBy = "alarms")
//    Set<Dose> getDoses();
}
