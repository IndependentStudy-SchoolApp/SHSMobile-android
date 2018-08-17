package com.syosseths.shsmobile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TextView bannerTextView, announcementTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setLogo(R.drawable.logoti);
            getSupportActionBar().setHomeButtonEnabled(true);

        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        bannerTextView = findViewById(R.id.bannerTextView);
        announcementTextView = findViewById(R.id.announcementTextView);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        String url = "https://spreadsheets.google.com/tq?key=1DLzFux6KkiMEpvf5Rqn0sZz84Fdl7S-hhBc5nU4Vo-M";
        new GetDayTask().execute(url);
    }

    // deleted code for options menu

    // deleted PlaceholderFragment class here

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // Returning the current tabs
            switch (position) {
                case 0:
                    return new NotificationsFragment();
                case 1:
                    return new GradeCalculatorFragment();
                case 2:
                    return new ScheduleFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3; //change to total # of tabs
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "NOTIFICATIONS";
                case 1:
                    return "GRADE CALCULATOR";
                case 2:
                    return "PERIOD SCHEDULE";
            }
            return null;
        }
    }

    private static String[] processJson(JSONObject object) {
        String[] dateInfo = new String[2];

        Date cDate = new Date();
        String fDate = new SimpleDateFormat("MM/dd/yyyy").format(cDate);

        try {
            JSONArray rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);

                if (row.getJSONArray("c").getJSONObject(0).getString("f").equals(fDate)) {
                    dateInfo[0] = row.getJSONArray("c").getJSONObject(1).getString("v");
                    dateInfo[1] = row.getJSONArray("c").getJSONObject(2).getString("v");
                    break;
                }
            }

            return dateInfo;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class GetDayTask extends AsyncTask<String, Void, String> {

        GetDayTask() {
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                return downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String apiResponse) {
            String[] arrayResult = new String[2];

            int start = apiResponse.indexOf("{", apiResponse.indexOf("{") + 1);
            int end = apiResponse.lastIndexOf("}");
            String jsonResponse = apiResponse.substring(start, end);
            try {
                JSONObject table = new JSONObject(jsonResponse);
                arrayResult = processJson(table);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String dayText = "", announcementText = arrayResult[1];

            switch (arrayResult[0]) {
                case "R":
                    dayText = "TODAY IS A RED DAY";
                    break;
                case "W":
                    dayText = "TODAY IS A WHITE DAY";
                    break;
                default:
                    break;
            }

            bannerTextView.setText(dayText);

            if (!announcementText.equals("-"))
                announcementTextView.setText(announcementText);
            else {
                announcementTextView.setPadding(0, 0, 0, 0);
                announcementTextView.setHeight(0);
                announcementTextView.setText("");
            }
        }

        private String downloadUrl(String urlString) throws IOException {
            InputStream is = null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                is = conn.getInputStream();

                String contentAsString = convertStreamToString(is);
                return contentAsString;
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }
}
