package uz.mq.mobilussduzb;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 5;
    ArrayList<DataModel> models;
    public MyPagerAdapter(FragmentManager fragmentManager, ArrayList<DataModel> models) {
        super(fragmentManager);
        this.models = models;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        return FirstFragment.newInstance(position, models.get(position));
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}