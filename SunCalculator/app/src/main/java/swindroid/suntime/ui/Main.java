package swindroid.suntime.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import swindroid.suntime.R;
import swindroid.suntime.calc.AstronomicalCalendar;
import swindroid.suntime.calc.FileIO;
import swindroid.suntime.calc.GeoLocation;


public class Main extends AppCompatActivity implements AddLocationFragment.AddLocationListener,QueryFragment.QueryListener
{
	private PageAdapter fAdapter;
	private ViewPager fViewPage;
	private TabLayout fTabLayout;

	private FileIO fFile;
	private SharedPreferences fPref;

	private QueryFragment fQuery;
	private AddLocationFragment fAddLocation;
	private MapFragment fMap;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		initalizeFile();
		initalizeCalc();
        initializeUI();

    }


	@Override
	protected void onResume() {
		super.onResume();

		if(fPref.getBoolean("firstRun",true))
		{
			Log.d("...","IS TRUE");
			fFile.initFile();
			fPref.edit().putBoolean("firstRun",false).commit();
		}
	}


	private void initalizeFile()
	{
		fFile = new FileIO(getApplicationContext());

	}

	private void initalizeCalc()
	{

		fPref = getSharedPreferences(getPackageName(),MODE_PRIVATE);
	}

	private void initializeUI()
	{

		Toolbar lToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(lToolbar);
		fTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        if (fTabLayout != null)
        {
            fTabLayout.addTab(fTabLayout.newTab().setText("Query"));
            fTabLayout.addTab(fTabLayout.newTab().setText("Map"));
            fTabLayout.addTab(fTabLayout.newTab().setText("Add Location"));
        }
        else
            throw new RuntimeException("Error: TabLayout is null \n" + getApplicationContext().toString());


		fAdapter = new PageAdapter(getSupportFragmentManager(),3);
		fViewPage = (ViewPager) findViewById(R.id.viewPager);

        if (fViewPage != null)
        {
            fViewPage.setAdapter(fAdapter);
        }
        else
            throw new RuntimeException("Error: ViewPager is null \n" + getApplicationContext().toString());

        fTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
		{
			@Override
			public void onTabSelected(TabLayout.Tab tab)
			{
				fViewPage.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater lInflater = getMenuInflater();
		lInflater.inflate(R.menu.toolbar_menu,menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_share:
            {
                if(fQuery != null)
                    fQuery.share();
                return true;
            }
            case R.id.menu_clear:
            {
                if(fMap != null && fQuery != null) // just in case
                {
                    fMap.clear();
                    fQuery.clear();
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
	public void updateFile(String[] aData)
	{
		// Recieved String array from AddLocationFragment
		// Update File
		Double lLat = Double.parseDouble(aData[1]);
		Double lLong = Double.parseDouble(aData[2]);
		TimeZone lTime = TimeZone.getTimeZone(aData[3]);
		Log.d("TIMEZONE",lTime.getDisplayName());
		fFile.writeToFile(new GeoLocation(aData[0],lLat,lLong,lTime));

	}

	@Override
	public void updateLocation(GeoLocation aLocation, ArrayList<String> aResult)
	{

		fMap.addMarker(aLocation,aResult);
	}

    @Override
	public HashMap<String, GeoLocation> populateSearchList()
	{
		return fFile.readFile();
	}


	class PageAdapter extends FragmentStatePagerAdapter
	{
		private int fTabCount;

		public PageAdapter(android.support.v4.app.FragmentManager aManager, int aTabCount) {
			super(aManager);
			fTabCount = aTabCount;
		}

		public Fragment getItem(int pos)
		{
			switch(pos)
			{
				case 0:
					fQuery = new QueryFragment();
					return fQuery;
				case 1:
					fMap = new MapFragment();
					return fMap;
				case 2:
					fAddLocation = new AddLocationFragment();
					return fAddLocation;
				default:
					return null;
			}
		}

		public int getCount()
		{
			return fTabCount;
		}
	}
}