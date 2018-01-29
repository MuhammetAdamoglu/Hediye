package com.hediye.abra.hediye;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private static ViewPager viewPager;
    static int nowPosition;

    static boolean stop=false;

    @Override
    protected void onPause() {
        super.onPause();
        stop=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        stop=false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop=true;
        System.exit(-1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        stop=false;
    }

    public SharedPreferences SharedPrefences() {
        final SharedPreferences prefSettings = getSharedPreferences("",0);
        return prefSettings;
    }

    public SharedPreferences.Editor Editor() {
        final SharedPreferences prefSettings = getSharedPreferences("",0);
        final SharedPreferences.Editor editor = prefSettings.edit();
        return editor;
    }

    public static void setCurrentItem (int item, boolean smoothScroll) {
        viewPager.setCurrentItem(item, smoothScroll);
    }

    static int say=0;

    public static void NextPage(){setCurrentItem(++say,true);}

    ProgressBar progressBar;
    boolean finish=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar= (ProgressBar) findViewById(R.id.progressBar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(1);
        new LongOperation().execute();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(nowPosition >=say){
                    setCurrentItem(say,true);
                }

                if(say-nowPosition>=1){
                    setCurrentItem(say-1,true);
                }
            }
            @Override
            public void onPageSelected(int position) {
                nowPosition =position;
                if(!finish)
                    Editor().putInt("Pos",nowPosition).commit();


                if(nowPosition>=pageCount-1){
                    Editor().clear().commit();
                    Toast.makeText(MainActivity.this, "Finished", Toast.LENGTH_SHORT).show();
                    finish=true;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });


    }

    int pageCount=0;
    ArrayList<Drawable> pitcures = new ArrayList<>();
    Drawable nowPicture;
    ViewPagerAdapter adapter;
    int start;

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        getElement element = new getElement(getApplicationContext());

        pitcures=element.pitcures;


        start=SharedPrefences().getInt("Pos",0);

        String Text=element.Text;


        say=start;
        nowPosition=start;


        for (String text: Text.split("-")) {

            if(pitcures.size()>pageCount){
                nowPicture=pitcures.get(pageCount);
            }
            else{
                nowPicture=null;
            }


            adapter.addFragment(new OneFragment(text,(pageCount+text).length(),nowPicture,pageCount), "");

            pageCount++;
        }
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            setupViewPager(viewPager);
            return "Executed";
        }

        @Override
        protected void onPreExecute() {}


        @Override
        protected void onPostExecute(String result) {
            viewPager.setAdapter(adapter);


            setCurrentItem(start,false);
            if(start<=0)
                Toast.makeText(MainActivity.this, "Biraz Bekle...", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            if(position==2){
                return null;
            }
            return mFragmentTitleList.get(position);
        }
    }

}