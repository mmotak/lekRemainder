package pl.com.mmotak.lekremainder.bindings;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.view.View;

/**
 * Created by mmotak on 02.12.2016.
 */
//
//@BindingMethods({
//        @BindingMethod(type = View.class, attribute = "app:dialogOnClick", method = "setDialogOnClick"),
//        @BindingMethod(type = View.class, attribute = "app:dialogResult", method = "setDialogOnClick"),
//})

public class DialogBindings {

    @BindingAdapter("dialogOnClick")
    public static void setDialogOnClick(View view, DialogManager.Factory factory) {
        view.setOnClickListener(factory.create(view, null));
    }

//    @BindingAdapter(value={"dialogOnClick", "dialogResult"})
//    public static void setDialogOnClick(View view, DialogManager.Factory factory, BaseObservable result) {
//        view.setOnClickListener(factory.create(view, result));
//    }
}
