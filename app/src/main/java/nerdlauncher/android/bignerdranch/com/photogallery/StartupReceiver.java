package nerdlauncher.android.bignerdranch.com.photogallery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Denis on 05.01.2016.
 */
public class StartupReceiver extends BroadcastReceiver {
    private static final String TAG="StartupReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Received broadcast intent: " + intent.getAction());

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        boolean isOn=pref.getBoolean(PollService.PREF_IS_ALARM_ON,false);
        PollService.setServiceAlarm(context,isOn);
    }
}
