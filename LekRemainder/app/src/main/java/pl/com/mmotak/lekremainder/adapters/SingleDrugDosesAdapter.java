package pl.com.mmotak.lekremainder.adapters;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.ItemSingleDrugDoseBinding;
import pl.com.mmotak.lekremainder.models.Dose;
import pl.com.mmotak.lekremainder.viewModels.ItemDoseViewModel;

/**
 * Created by mmotak on 15.12.2016.
 */

public class SingleDrugDosesAdapter extends ListRecyclerViewAdapter<Dose, ItemSingleDrugDoseBinding> {

    public SingleDrugDosesAdapter() {
        super(R.layout.item_single_drug_dose);
    }

    @Override
    public void onBindViewHolder(ViewHolder<ItemSingleDrugDoseBinding> holder, int position) {
        holder.binding.setViewModel(new ItemDoseViewModel(get(position).getTime()));
    }
}
