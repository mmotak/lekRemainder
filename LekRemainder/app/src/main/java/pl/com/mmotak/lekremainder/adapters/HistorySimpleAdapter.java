package pl.com.mmotak.lekremainder.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.databinding.ItemHistoryBinding;
import pl.com.mmotak.lekremainder.entities.DbHistory;

/**
 * Created by Maciej on 2016-12-27.
 */

public class HistorySimpleAdapter extends RecyclerView.Adapter<HistorySimpleAdapter.HistoryViewHolder> {

    private List<DbHistory> list = new ArrayList<>();

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_history, parent, false));
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.binding.setViewModel(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<DbHistory> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        final ItemHistoryBinding binding;

        HistoryViewHolder(ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
