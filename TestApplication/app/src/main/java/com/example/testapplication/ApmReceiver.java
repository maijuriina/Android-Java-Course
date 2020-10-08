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

        if (state) {
            Toast.makeText(context, "AIRPLANE MODE ON", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "AIRPLANE MODE OFF", Toast.LENGTH_SHORT).show();
        }
    }
}
