package tz.co.ubunifusolutions.screens.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.zip.Inflater;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.fragments.pager.PagerAdapter;
import tz.co.ubunifusolutions.screens.fragments.tab_fragments.new_read;

public class dashboardFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2;

    private FragmentTabHost tabHost;

    public dashboardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_dashboard,container,false);
//       tabLayout = (TabLayout) rootView.findViewById(R.id.tabs_holder);
//       viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

    //   viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */



        return rootView;

    }

}
