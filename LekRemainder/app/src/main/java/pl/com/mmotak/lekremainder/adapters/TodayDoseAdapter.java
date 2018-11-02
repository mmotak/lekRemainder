package pl.com.mmotak.lekremainder.adapters;

import android.content.Context;
import android.support.annotation.NonNull;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.data.IDataProvider;
import pl.com.mmotak.lekremainder.databinding.ItemTodayDoseBinding;
import pl.com.mmotak.lekremainder.models.TodayDose;
import pl.com.mmotak.lekremainder.viewModels.ItemTodayDoseViewModel;

/**
 * Created by mmotak on 16.12.2016.
 */

public class TodayDoseAdapter extends ListRecyclerViewAdapter<TodayDose, ItemTodayDoseBinding> {

    private final IDataProvider dataProvider;
    private Context context;

    public TodayDoseAdapter(Context context, IDataProvider dataProvider) {
        super(R.layout.item_today_dose);
        this.context = context;
        this.dataProvider = dataProvider;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<ItemTodayDoseBinding> holder, int position) {
        holder.binding.setViewModel(new ItemTodayDoseViewModel(context, get(position), dataProvider));
    }
}