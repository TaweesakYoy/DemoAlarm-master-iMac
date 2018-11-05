package com.example.taweesak.demoalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent(context,ShowNotiActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // intFlag from MainFragment
        context.startActivity(intent1);

    }


}// Main Class
