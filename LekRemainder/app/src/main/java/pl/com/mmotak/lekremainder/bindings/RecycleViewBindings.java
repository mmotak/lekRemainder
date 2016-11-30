package pl.com.mmotak.lekremainder.bindings;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;

/**
 * Created by mmotak on 29.11.2016.
 */

public class RecycleViewBindings {

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, LayoutManagers.LayoutManagerFactory layoutManagerFactory) {
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
    }

    @BindingAdapter("adapter") public static void setNewAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, ObservableArrayList<?> list) {
        if (recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

//    @BindingConversion
//    public static BindingRecyclerViewAdapterFactory toRecyclerViewAdapterFactory(final String className) {
//        return new BindingRecyclerViewAdapterFactory() {
//            @Override
//            public <T> BindingRecyclerViewAdapter<T> create(RecyclerView recyclerView, ItemViewArg<T> arg) {
//                return Utils.createClass(className, arg);
//            }
//        };
//    }
}
