package pl.com.mmotak.lekremainder.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.databinding.ItemTodayDoseBinding;
import pl.com.mmotak.lekremainder.models.TodayDose;
import pl.com.mmotak.lekremainder.viewModels.ItemTodayDoseViewModel;

/**
 * Created by mmotak on 16.12.2016.
 */

public class TodayDoseAdapter extends RecyclerView.Adapter<TodayDoseAdapter.DoseViewHolder> {

    private final IDataProvider dataProvider;
    private List<TodayDose> times = new ArrayList<>();

    public TodayDoseAdapter(IDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public DoseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTodayDoseBinding bindings = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_today_dose, parent, false);
        return new DoseViewHolder(bindings);
    }

    @Override
    public void onBindViewHolder(DoseViewHolder holder, int position) {
        holder.binding.setViewModel(new ItemTodayDoseViewModel(times.get(position),dataProvider));
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public void setList(List<TodayDose> list) {
        times.clear();
        times.addAll(list);
        notifyDataSetChanged();
    }

    public static class DoseViewHolder extends RecyclerView.ViewHolder {
        final ItemTodayDoseBinding binding;

        public DoseViewHolder(ItemTodayDoseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}