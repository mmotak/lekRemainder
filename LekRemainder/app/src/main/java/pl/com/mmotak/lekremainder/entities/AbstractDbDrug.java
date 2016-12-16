package pl.com.mmotak.lekremainder.entities;

import android.os.Parcelable;

import java.util.List;

import io.requery.CascadeAction;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.OneToMany;

/**
 * Created by mmotak on 06.12.2016.
 */

@Entity
public abstract class AbstractDbDrug implements Parcelable {

    @Key @Generated
    int id;

    String name;
    String type;
    int dosesNo;
    int dosesEveryH;

    @OneToMany(cascade = {CascadeAction.DELETE, CascadeAction.SAVE})
    List<AbstractDbDose> dbDoses;
}
