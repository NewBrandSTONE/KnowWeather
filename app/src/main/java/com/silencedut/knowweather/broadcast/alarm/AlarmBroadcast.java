package com.silencedut.knowweather.broadcast.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/19
 */

public class AlarmBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.d("MyTag", "onclock......................");
        String msg = intent.getStringExtra("msg");

        Intent alaramIntent = new Intent(context, AlarmTimeOutActivity.class);
        alaramIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alaramIntent);
    }
}
