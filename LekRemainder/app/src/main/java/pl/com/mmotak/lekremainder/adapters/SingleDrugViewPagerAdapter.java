package pl.com.mmotak.lekremainder.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.bindings.tools.IViewDataBinding;
import pl.com.mmotak.lekremainder.bindings.tools.InflaterStrategy;
import pl.com.mmotak.lekremainder.databinding.SingleDrugLeftTabBinding;
import pl.com.mmotak.lekremainder.databinding.SingleDrugRightTabBinding;
import pl.com.mmotak.lekremainder.viewModels.SingleDrugViewModel;

/**
 * Created by mmotak on 14.12.2016.
 */

public class SingleDrugViewPagerAdapter extends PagerAdapter {


    private SingleDrugViewModel viewModel;
    private Context context;

    public SingleDrugViewPagerAdapter(Context context, SingleDrugViewModel viewModel) {
        this.viewModel = viewModel;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    private int getLayoutId(int position) {
        return position == 0 ? R.layout.single_drug_left_tab : R.layout.single_drug_right_tab;
    }

    private ViewDataBinding inflateStategy(ViewGroup container, LayoutInflater inflater, int position) {
        switch (position) {
            case 0:
                SingleDrugLeftTabBinding bindings = DataBindingUtil.inflate(inflater, R.layout.single_drug_left_tab, container, false);
                bindings.setViewModel(viewModel);
                return bindings;
            case 1:
                SingleDrugRightTabBinding bindings1 = DataBindingUtil.inflate(inflater, R.layout.single_drug_right_tab, container, false);
                bindings1.setViewModel(viewModel);
                return bindings1;
            default:
                return null; // should never take place
        }
    }

    private ViewDataBinding inflate(ViewGroup container, LayoutInflater inflater, int position) {
        ViewDataBinding dataBinding = InflaterStrategy.inflate(inflater, container, viewModel, position);
        return dataBinding;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        //ViewDataBinding bindings = DataBindingUtil.inflate(inflater, getLayoutId(position), container, false);
        //ViewGroup layout = (ViewGroup) inflater.inflate(getLayoutId(position), container, false);

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