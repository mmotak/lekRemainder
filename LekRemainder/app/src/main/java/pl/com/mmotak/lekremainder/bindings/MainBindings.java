package pl.com.mmotak.lekremainder.bindings;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTime;
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

    @BindingAdapter("android:text")
    public static void setText(TextView view, DateTime value) {
        String format = view.getContext().getString(R.string.datetime_format);
        view.setText(value.toString(format));
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    @InverseBindingAdapter(attribute = "android:visibility")
    public static boolean getVisibility(View view) {
        int visibility = view.getVisibility();
        return visibility == View.VISIBLE;
    }

    @BindingAdapter("android:tint")
    public static void setColorTint(ImageView view, @ColorRes int color) {
        final int version = Build.VERSION.SDK_INT;
        final Context context = view.getContext();
        if (version < Build.VERSION_CODES.LOLLIPOP) { // will it work ?
            DrawableCompat.wrap(view.getDrawable());
        }

        DrawableCompat.setTint(view.getDrawable(), ContextCompat.getColor(context, color));
    }


}
