package pl.com.mmotak.lekremainder.models;

/**
 * Created by mmotak on 30.11.2016.
 */

public class EnableObject<T> {

    private T value;
    private boolean enable;

    public EnableObject() {
        this(null, false);
    }

    public EnableObject(T value, boolean enable) {
        setValue(value);
        setEnable(enable);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        if (value == null) {
            this.enable = false;
        }
    }

    public boolean isEnable() {
        return enable;
    }

    public boolean hasValue() {
        return value != null;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        if (value == null) {
            this.enable = false;
        }
    }


}
