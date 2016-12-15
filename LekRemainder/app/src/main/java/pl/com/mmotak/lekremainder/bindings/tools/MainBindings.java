package pl.com.mmotak.lekremainder.bindings.tools;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.TextView;

import org.joda.time.LocalTime;

import pl.com.mmotak.lekremainder.R;

/**
 * Created by mmotak on 15.12.2016.
 */

public class MainBindings {

    @BindingAdapter("android:text")
    public static void setText(TextView view, int value) {
        view.setText(Integer.toString(value));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static int getText(TextView view) {
        try {
            return Integer.parseInt(view.getText().toString());
        } catch (NumberFormatException e) {
            view.setText("0");
            return 0;
        }
    }

    @BindingAdapter("android:text")
    public static void setText(TextView view, LocalTime value) {
        String format = view.getContext().getString(R.string.time_format);
        view.setText(value.toString(format));
    }
}
