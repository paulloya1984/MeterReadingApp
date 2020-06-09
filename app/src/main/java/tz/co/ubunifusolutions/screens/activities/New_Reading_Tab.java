package tz.co.ubunifusolutions.screens.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.adaptors.TabAdapter;
import tz.co.ubunifusolutions.screens.fragments.tab_fragments.new_read;
import tz.co.ubunifusolutions.screens.fragments.tab_fragments.readd;
import tz.co.ubunifusolutions.screens.fragments.tab_fragments.unread;

public class New_Reading_Tab extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__reading__tab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new new_read(), "New Reading");
        adapter.addFragment(new readd(), "Read Meter");
        adapter.addFragment(new unread(), "Unread Meter");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    }

