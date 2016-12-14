package pl.com.mmotak.lekremainder.adapters;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.com.mmotak.lekremainder.bindings.tools.InflaterStrategy;
import pl.com.mmotak.lekremainder.viewModels.SingleDrugViewModel;

/**
 * Created by mmotak on 14.12.2016.
 */

public class SingleDrugViewPagerAdapter extends PagerAdapter {

    private SingleDrugViewModel viewModel;
    private Context context;
    private LayoutInflater inflater;

    public SingleDrugViewPagerAdapter(Context context, SingleDrugViewModel viewModel) {
        this.viewModel = viewModel;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return InflaterStrategy.getCount();
    }

    private ViewDataBinding inflate(ViewGroup container, LayoutInflater inflater, int position) {
        ViewDataBinding dataBinding = InflaterStrategy.inflate(inflater, container, viewModel, position);
        return dataBinding;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewDataBinding bindings = inflate(container, inflater, position);

        container.addView(bindings.getRoot());
        return bindings.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            return getText(position);
//        }

}