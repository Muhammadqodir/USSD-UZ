package uz.mq.mobilussduzb;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SubFragmentAdapter extends FragmentPagerAdapter {
    int PAGE_COUNT = 0;
    public static ArrayList<ArrayList<DataItem>> items;
    ArrayList<String> titles;
    public static Context context;
    public String targetType;
    public String comTitle;

    public SubFragmentAdapter(FragmentManager fm, Context context, int count, ArrayList<ArrayList<DataItem>> items, ArrayList<String> titles, String targettype, String comTitle) {
        super(fm);
        this.items = items;
        this.titles = titles;
        this.PAGE_COUNT = count;
        this.context = context;
        this.targetType = targettype;
        this.comTitle = comTitle;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return SubFragment.newInstance(position, targetType, comTitle);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // генерируем заголовок в зависимости от позиции
        return titles.get(position);
    }
}