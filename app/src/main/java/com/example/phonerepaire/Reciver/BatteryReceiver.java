package com.example.phonerepaire.Reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class BatteryReceiver extends BroadcastReceiver {
    public static int level;
    @Override
    public void onReceive(Context context, Intent intent) {
         level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
    }
}
