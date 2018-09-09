package com.syosseths.shsmobile;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        GradeCalculatorFragment.OnFragmentInteractionListener,
        ScheduleFragment.OnFragmentInteractionListener,
        NotificationsFragment.OnFragmentInteractionListener {

    ActionBar actionBar;
    private FragmentManager fragmentManager;
    private DrawerLayout drawer;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            actionBar = Objects.requireNonNull(getSupportActionBar());
        }

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass;
            fragmentClass = GradeCalculatorFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();

            actionBar.setTitle(R.string.home);
        }

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //String url = "https://spreadsheets.google.com/tq?key=1DLzFux6KkiMEpvf5Rqn0sZz84Fdl7S-hhBc5nU4Vo-M";
        //new GetDayTask().execute(url);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        switch (id) {
            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                actionBar.setTitle(R.string.home);
                break;
            case R.id.nav_grade_calculator:
                fragmentClass = GradeCalculatorFragment.class;
                actionBar.setTitle(R.string.grade_calculator);
                break;
            case R.id.nav_schedule:
                fragmentClass = ScheduleFragment.class;
                actionBar.setTitle(R.string.schedule);
                break;
            case R.id.nav_notifications:
                fragmentClass = NotificationsFragment.class;
                actionBar.setTitle(R.string.notifications);
                break;
        }
        if (fragmentClass == null) {

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
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

            /*bannerTextView.setText(dayText);

            if (!announcementText.equals("-"))
                announcementTextView.setText(announcementText);
            else {
                announcementTextView.setPadding(0, 0, 0, 0);
                announcementTextView.setHeight(0);
                announcementTextView.setText("");
            }*/
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
