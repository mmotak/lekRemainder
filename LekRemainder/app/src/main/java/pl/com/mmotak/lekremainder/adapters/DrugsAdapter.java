package pl.com.mmotak.lekremainder.adapters;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.ItemDrugBinding;
import pl.com.mmotak.lekremainder.models.Drug;
import pl.com.mmotak.lekremainder.viewModels.ItemDrugViewModel;

/**
 * Created by mmotak on 30.11.2016.
 */

public class DrugsAdapter extends ListRecyclerViewAdapter<Drug, ItemDrugBinding> {

    public DrugsAdapter() {
        super(R.layout.item_drug);
    }

    @Override
    public void onBindViewHolder(ViewHolder<ItemDrugBinding> holder, int position) {
        holder.binding.setViewModel(new ItemDrugViewModel(get(position)));
    }
}
