package tz.co.ubunifusolutions.screens.fragments.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import tz.co.ubunifusolutions.screens.fragments.tab_fragments.*;

public class PagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    public PagerAdapter(FragmentManager fm) {
        super(fm);
        //this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                new_read tab1 = new new_read();
                return tab1;
            case 1:
                readd tab2 = new readd();
                return tab2;
//            case 2:
//                TabFragment3 tab3 = new TabFragment3();
//                return tab3;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    /**
     * This method returns the title of the tab according to the position.
     */

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                String New_Read = "New Read";
                return New_Read;
            case 1:
                String Read = "Read";
                return Read;
            case 2:
                String Unreaad = "Unread";
                return Unreaad;
        }
        return null;
    }


}
