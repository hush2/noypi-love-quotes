package com.example.android.noypilovequotes;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    MyPagerAdapter pagerAdapter;
    ViewPager viewPager;
    static Typeface font;

    public static final String QUOTE_INDEX = "QUOTE_INDEX";

    private static ArrayList<String> quotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        font = Typeface.createFromAsset(getAssets(), "JennaSue.ttf");

        InputStream is = getResources().openRawResource(R.raw.quotes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        quotes = new ArrayList<>();

        try {
            while ((line = reader.readLine()) != null) {
                quotes.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.shuffle(quotes, new Random(System.nanoTime()));

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            MyFragment fragment = new MyFragment();

            Bundle args = new Bundle();
            args.putInt(QUOTE_INDEX, position);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public int getCount() {
            return quotes.size();
        }
    }


    public static class MyFragment extends Fragment {

        public MyFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            TextView textView = (TextView) rootView.findViewById(R.id.textView);
            textView.setTypeface(font);
            int pos = getArguments().getInt(MainActivity.QUOTE_INDEX, 0);
            textView.setText(quotes.get(pos));

            return rootView;
        }
    }




}
