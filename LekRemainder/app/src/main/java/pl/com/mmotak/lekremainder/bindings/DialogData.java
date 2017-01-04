package pl.com.mmotak.lekremainder.bindings;

import android.databinding.BaseObservable;

/**
 * Created by mmotak on 14.12.2016.
 */
public class DialogData<T> extends BaseObservable implements DialogManager.IDialogData {
    private T object;

    public DialogData(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
        notifyChange();
    }

    @Override
    public Object load() {
        return object;
    }

    @Override
    public void save(Object o) {
        object = (T) o;
        notifyChange();
    }
}
