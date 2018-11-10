package pl.com.mmotak.lekremainder.adapters;

import android.support.annotation.NonNull;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.ItemLogBinding;
import pl.com.mmotak.lekremainder.viewModels.ItemLogViewModel;

public class LogsAdapter extends ListRecyclerViewAdapter<String, ItemLogBinding> {
    public LogsAdapter() {
        super(R.layout.item_log);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<ItemLogBinding> itemLogBindingViewHolder, int position) {
            itemLogBindingViewHolder.binding.setViewModel(new ItemLogViewModel(position, get(position)));
    }
}
