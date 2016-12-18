package pl.com.mmotak.lekremainder.entities;

import android.databinding.Observable;
import android.os.Parcelable;

import java.util.List;

import io.requery.CascadeAction;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.OneToMany;
import io.requery.Persistable;

/**
 * Created by mmotak on 06.12.2016.
 */

@Entity
public interface IDbDrug extends Observable, Parcelable, Persistable {

    @Key @Generated
    int getId();

    String getName();
    String getType();
    int getDosesNo();
    int getDosesEveryH();

    @OneToMany(cascade = {CascadeAction.DELETE, CascadeAction.SAVE})
    List<IDbDose> getDbDoses();
}
