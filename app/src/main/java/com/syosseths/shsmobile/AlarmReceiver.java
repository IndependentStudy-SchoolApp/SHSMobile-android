package com.syosseths.shsmobile;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

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

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    private final static String url = "https://spreadsheets.google.com/tq?key=1DLzFux6KkiMEpvf5Rqn0sZz84Fdl7S-hhBc5nU4Vo-M";
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        new GetDayTask().execute(url);
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
            String result = "N";

            int start = apiResponse.indexOf("{", apiResponse.indexOf("{") + 1);
            int end = apiResponse.lastIndexOf("}");
            String jsonResponse = apiResponse.substring(start, end);
            try {
                JSONObject table = new JSONObject(jsonResponse);
                //Log.d("JSONRESPONSE", String.valueOf(table));
                result = processJson(table);
                //Log.d("JSONRESPONSE", result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String contentText = "";

            int contentIcon = R.drawable.s_nd;
            Bitmap contentBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.s_nd);

            switch (result) {
                case "R":
                    contentText = "Today is a Red Day.";
                    contentIcon = R.drawable.s_red;
                    contentBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.s_red);
                    break;
                case "W":
                    contentText = "Today is a White Day.";
                    contentIcon = R.drawable.s_white;
                    contentBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.s_white);
                    break;
                default:
                    break;
            }

            if (result.equals("R") || result.equals("W")) {
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context, "1")
                                .setSmallIcon(contentIcon)
                                .setLargeIcon(contentBitmap)
                                .setContentTitle("Syosset High School")
                                .setContentText(contentText)
                                .setColor(Color.argb(100, 255, 0, 0)); //text color on notification

                // Sets an ID for the notification
                int mNotificationId = 1;

                NotificationManager mNotifyMgr =
                        (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                // Builds the notification and issues it
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
            }

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
            int responseCode = conn.getResponseCode();
            is = conn.getInputStream();

            String contentAsString = convertStreamToString(is);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private static String convertStreamToString(InputStream is) {
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

    private static String processJson(JSONObject object) {
        String dayType = "";

        Date cDate = new Date();
        String fDate = new SimpleDateFormat("MM/dd/yyyy").format(cDate);

        try {
            JSONArray rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);

                if (row.getJSONArray("c").getJSONObject(0).getString("f").equals(fDate)) {
                    dayType = row.getJSONArray("c").getJSONObject(1).getString("v");
                    break;
                }
            }

            return dayType;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
