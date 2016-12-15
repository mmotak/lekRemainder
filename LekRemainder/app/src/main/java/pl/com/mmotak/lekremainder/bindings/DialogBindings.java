package pl.com.mmotak.lekremainder.bindings;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mmotak on 02.12.2016.
 */
//
//@BindingMethods({
//        @BindingMethod(type = View.class, attribute = "app:dialogOnClick", method = "setDialogOnClick"),
//        @BindingMethod(type = View.class, attribute = "app:dialogResult", method = "setDialogOnClick"),
//})

public class DialogBindings {

    @BindingAdapter(value = {"dialogOnClick", "dialogData"}, requireAll = false)
    public static void setDialogOnClick(View view, DialogManager.Factory factory, DialogManager.IDialogData data) {
        view.setOnClickListener(factory.create(view, data));
    }

    @BindingAdapter("errorMessage")
    public static void setErrorMessage(TextView tv, CharSequence error) {
        if (error.length() > 0) {
            tv.setError(error);
        } else tv.setError(null);
    }
}
