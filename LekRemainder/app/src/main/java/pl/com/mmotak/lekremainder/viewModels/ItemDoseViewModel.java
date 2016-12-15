package pl.com.mmotak.lekremainder.viewModels;

import android.databinding.ObservableField;

import org.joda.time.LocalTime;

/**
 * Created by mmotak on 15.12.2016.
 */

public class ItemDoseViewModel {

    public ObservableField<LocalTime> time = new ObservableField<>(LocalTime.now());

    public ItemDoseViewModel(LocalTime time) {
        this.time.set(time);
    }
}
