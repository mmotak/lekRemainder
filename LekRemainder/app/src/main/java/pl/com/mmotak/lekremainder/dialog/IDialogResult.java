package pl.com.mmotak.lekremainder.dialog;

/**
 * Created by mmotak on 15.12.2016.
 */
public interface IDialogResult<T> {

    void onSuccess(T data);

    void onFail();
}
