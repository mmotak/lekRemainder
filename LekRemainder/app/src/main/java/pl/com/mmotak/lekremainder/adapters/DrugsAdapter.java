package pl.com.mmotak.lekremainder.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.ItemDrugBinding;
import pl.com.mmotak.lekremainder.models.Drug;
import pl.com.mmotak.lekremainder.viewModels.ItemDrugViewModel;

/**
 * Created by mmotak on 30.11.2016.
 */

public class DrugsAdapter extends RecyclerView.Adapter<DrugsAdapter.DrugViewHolder> {

    private List<Drug> list = new ArrayList<>();
    private Context context;

    public DrugsAdapter(Context context) {
        this.context = context;
    }

    @Override public DrugViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemDrugBinding itemDrugBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_drug, parent, false);
        return new DrugViewHolder(itemDrugBinding);
    }

    @Override public void onBindViewHolder(DrugViewHolder holder, int position) {
        ItemDrugBinding itemDrugBinding = holder.binding;
        itemDrugBinding.setViewModel(new ItemDrugViewModel(list.get(position)));
    }

    @Override public int getItemCount() {
        return list.size();
    }

    public void setDrugList(List<Drug> inputList) {
        list.clear();
        if (inputList != null) {
            list.addAll(inputList);
        }
        notifyDataSetChanged();
    }

    public void addDrug(Drug drug) {
        list.add(drug);
        notifyDataSetChanged();
    }

    public static class DrugViewHolder extends RecyclerView.ViewHolder {
        final ItemDrugBinding binding;

        public DrugViewHolder(ItemDrugBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }

//        void bindDrug(AbstractDbDrug drug) {
//            if (binding.getViewModel() == null) {
//                binding.setViewModel(new ItemDrugViewModel(itemView.getContext(), drug));
//            } else {
//                binding.getViewModel().setRepository(drug);
//            }
//        }
    }
}
