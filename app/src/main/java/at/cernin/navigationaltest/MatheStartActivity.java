package at.cernin.navigationaltest;

import java.util.Locale;

import android.content.Intent;
import android.net.Uri;
//import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static at.cernin.navigationaltest.R.string.title_section4;

public class MatheStartActivity extends AppCompatActivity
        implements  TrainingStartFragment.OnFragmentInteractionListener,
                    TestStartFragment.OnFragmentInteractionListener,
                    StatistikFragment.OnFragmentInteractionListener,
                    MatheStartFragment.OnFragmentInteractionListener,
                    ActionBar.TabListener
        {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static final String VIEW_PAGER_STATE = "MatheAppStartViewPagerState";
    private ViewPager mViewPager;

    public MatheStartActivity() {
        int i = 0;
    }
            //PagerTitleStrip mPagerTitleStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathe_start);

        final ActionBar actionBar =  getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setDisplayShowTitleEnabled(true);
            //actionBar.setDisplayShowHomeEnabled(true);
            //actionBar.setIcon(R.mipmap.ic_launcher);

        }

        //setTitle("My new title");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                        //getSupportActionBar().setTitle(mSectionsPagerAdapter.getPageTitle(position));
                    }
        });
        for (int i = 0; i < mSectionsPagerAdapter.getCount();i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                    .setText(mSectionsPagerAdapter.getPageTitle(i))
                    .setTabListener(this)
            );
        }


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //if (savedInstanceState != null) {
            mViewPager.setCurrentItem(savedInstanceState.getInt(VIEW_PAGER_STATE));
        //}
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(VIEW_PAGER_STATE, mViewPager.getCurrentItem());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mathe_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(this, DummyActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                mViewPager.setCurrentItem(0);
                Toast.makeText(this, "UP/Home gedrückt", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String id) {

    }


    @Override
    public void onClickCockpitButton(int position) {
        /*
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
            fm.beginTransaction()
                    .add(mSectionsPagerAdapter.getItem(0), "Start")
                    .addToBackStack(null)
                    .commit();
        }
        */
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        //if (ft != nul) { ft.addToBackStack("Fragment Action"); }
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }


            /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private static final int MAX_SEGMENTS  = 5;
        private  final Fragment[] fragments = new Fragment[MAX_SEGMENTS];

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            for (Fragment f:fragments) {
                f = null;
            };
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a MatheStartFragment (defined as a static inner class below).
            Fragment fragment = fragments[position];
            if (fragment == null) {
                switch (position) {
                    case 0:
                        fragment = MatheStartFragment.newInstance(position + 1);
                        break;
                    case 1:
                        fragment = TrainingStartFragment.newInstance("String A - Training", "String B - Training");
                        //fragment = StatistikFragment.newInstance("String A - Statistik", "String B - Statistik");
                        break;
                    case 2:
                        fragment = TestStartFragment.newInstance("String A - Test", "String B - Test");
                        break;
                    case 3:
                        fragment = StatistikFragment.newInstance("String A - Statistik", "String B - Statistik");
                        break;
                    case 4: // ACHTUNG: Falsches Fragment!
                        fragment = StatistikFragment.newInstance("String A - Social", "String B - Social");
                        break;
                }
                fragments[position] = fragment;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return MAX_SEGMENTS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_section0);
                case 1:
                    return getString(R.string.title_section1);
                case 2:
                    return getString(R.string.title_section2);
                case 3:
                    return getString(R.string.title_section3);
                case 4:
                    return getString(R.string.title_section4);
            }
            return null;
        }
    }

}
