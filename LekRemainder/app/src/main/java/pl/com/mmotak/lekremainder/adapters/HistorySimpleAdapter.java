package pl.com.mmotak.lekremainder.adapters;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.ItemHistoryBinding;
import pl.com.mmotak.lekremainder.models.History;

/**
 * Created by Maciej on 2016-12-27.
 */

public class HistorySimpleAdapter extends ListRecyclerViewAdapter<History, ItemHistoryBinding> {

    public HistorySimpleAdapter() {
        super(R.layout.item_history);
    }

    @Override
    public void onBindViewHolder(ViewHolder<ItemHistoryBinding> holder, int position) {
        holder.binding.setViewModel(get(position));
    }

}
