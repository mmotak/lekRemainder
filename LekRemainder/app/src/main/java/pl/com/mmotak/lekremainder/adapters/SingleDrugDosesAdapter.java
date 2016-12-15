package pl.com.mmotak.lekremainder.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.ItemSingleDrugDoseBinding;
import pl.com.mmotak.lekremainder.viewModels.ItemDoseViewModel;

/**
 * Created by mmotak on 15.12.2016.
 */

public class SingleDrugDosesAdapter extends RecyclerView.Adapter<SingleDrugDosesAdapter.DoseViewHolder> {

    private List<LocalTime> times = new ArrayList<>();

    @Override
    public DoseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSingleDrugDoseBinding bindings = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_single_drug_dose, parent, false);
        return new DoseViewHolder(bindings);
    }

    @Override
    public void onBindViewHolder(DoseViewHolder holder, int position) {
        holder.binding.setViewModel(new ItemDoseViewModel(times.get(position)));
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public void setList(List<LocalTime> list) {
        times.clear();
        times.addAll(list);
        notifyDataSetChanged();
    }

    public static class DoseViewHolder extends RecyclerView.ViewHolder {
        final ItemSingleDrugDoseBinding binding;

        public DoseViewHolder(ItemSingleDrugDoseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
