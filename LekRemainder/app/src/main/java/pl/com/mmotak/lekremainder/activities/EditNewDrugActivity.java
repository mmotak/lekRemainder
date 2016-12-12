package pl.com.mmotak.lekremainder.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.com.mmotak.lekremainder.R;

public class EditNewDrugActivity extends BaseNavDrawerActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_new_drug);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyViewPagerAdapter(this));
    }

    private class MyViewPagerAdapter extends PagerAdapter {

        private Context context;
        private List<Integer> list;

        public MyViewPagerAdapter(Context context) {
            this.context = context;
            this.list = new ArrayList<>();
            for (int i = 1; i <=9 ; i++) {
                list.add(new Integer(i));
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        private String getText(int position) {
            return " -->  " + list.get(position) + "  <-- ";
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(context);

            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.vp_layaut1, container, false);

            TextView tv = (TextView) layout.findViewById(R.id.text17);
            tv.setText(getText(position));

            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getText(position);
        }
    }
}
