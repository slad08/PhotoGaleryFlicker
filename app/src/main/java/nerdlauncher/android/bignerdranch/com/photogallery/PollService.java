package nerdlauncher.android.bignerdranch.com.photogallery;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Denis on 03.01.2016.
 */
public class PollService extends IntentService {
    private static final String TAG = "PollService";
    private static final int POLL_INTERVAL=1000*5;//5 мин

    public static final String PREF_IS_ALARM_ON="isAlarmOn";

    public static final String ACTION_SHOW_NOTIFICATION=
            "nerdlauncher.android.bignerdranch.com.photogallery.SHOW_NOTIFICATION";

    public static final String PERM_PRIVATE=
            "nerdlauncher.android.bignerdranch.com.photogallery.PRIVATE";

     public PollService() {
        super(TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        boolean isNetworkAvailable = cm.getBackgroundDataSetting() &&
                cm.getActiveNetworkInfo() != null;
        if (!isNetworkAvailable) return;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String query = prefs.getString(FlickrFetchr.PREF_SEARCH_QUERY, null);
        String lastResultId = prefs.getString(FlickrFetchr.PREF_LAST_RESULT_ID, null);

        ArrayList<GalleryItem> items;
        if (query != null) {
            items = new FlickrFetchr().search(query);
        } else {
            items = new FlickrFetchr().fetchItems();
        }
        if (items.size() == 0)
            return;
        String resultId = items.get(0).getId();

        if (!resultId.equals(lastResultId)) {
            Log.i(TAG, "Got a new result: " + resultId);

        Resources r = getResources();
        PendingIntent pi = PendingIntent
                .getActivities(this, 0, new Intent[]{new Intent(this, PhotoGalleryActivity.class)}, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(r.getString(R.string.new_picture_title))
                .setSmallIcon(R.drawable.flickr)
                .setContentTitle(r.getString(R.string.new_picture_title))
                .setContentTitle(r.getString(R.string.new_picture_text))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

     /*   NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

            sendBroadcast(new Intent(ACTION_SHOW_NOTIFICATION),PERM_PRIVATE);
*/
                showBackgroundNotification(0,notification);
        }
        prefs.edit()
                .putString(FlickrFetchr.PREF_LAST_RESULT_ID,resultId)
                .commit();
        Log.i(TAG, "Received an intent: " + intent);
    }
    public static void setServiceAlarm(Context context,boolean isOn){
        Intent i=new Intent(context,PollService.class);
        PendingIntent pi=PendingIntent.getService(context,0,i,0);

        AlarmManager alarmManager=(AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        if (isOn){
            alarmManager.setRepeating(AlarmManager.RTC,
                    System.currentTimeMillis(),POLL_INTERVAL,pi);
        }else{
            alarmManager.cancel(pi);
        pi.cancel();
        }
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PollService.PREF_IS_ALARM_ON,isOn)
                .commit();

    }
    public static boolean isServiceAlarmOn(Context context){
        Intent i=new Intent(context, PollService.class);
        PendingIntent pi =PendingIntent.getService(context,0,i,PendingIntent.FLAG_NO_CREATE);
        return pi!=null;

    }
    void showBackgroundNotification(int requestCode,Notification notification){
        Intent i=new Intent(ACTION_SHOW_NOTIFICATION);
        i.putExtra("REQUEST_CODE",requestCode);
        i.putExtra("NOTIFICATION",notification);

        sendOrderedBroadcast(i,PERM_PRIVATE,null,null,
                Activity.RESULT_OK,null,null);
    }
}
