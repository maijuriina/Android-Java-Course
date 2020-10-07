package com.example.testapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class ApmReceiver extends BroadcastReceiver {
    private static final String TAG = "ApmBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean state = intent.getBooleanExtra("state", false);
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        Log.d(TAG, log);
        if (isAirplaneModeOn(context)) {
            Toast.makeText(context, "AIRPLANE MODE ON", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "AIRPLANE MODE OFF", Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(context, log, Toast.LENGTH_LONG).show();
    }

    private static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }
}
