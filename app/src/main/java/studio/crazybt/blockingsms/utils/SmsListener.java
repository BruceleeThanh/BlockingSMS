package studio.crazybt.blockingsms.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import studio.crazybt.blockingsms.MainActivity;
import studio.crazybt.blockingsms.models.SmsBlocked;

/**
 * Created by Brucelee Thanh on 09/10/2016.
 */

public class SmsListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            if (bundle != null) {
                //---retrieve the SMS message received---
                try {
                    List<SmsBlocked> smsBlockedList = new ArrayList<>();
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        smsBlockedList.add(new SmsBlocked(msgs[i].getOriginatingAddress(), msgs[i].getMessageBody(),
                                Calendar.getInstance().getTime()));
                        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
                        databaseHelper.insertSms(new SmsBlocked(msgs[i].getOriginatingAddress(), msgs[i].getMessageBody(),
                                Calendar.getInstance().getTime()));
                    }
                    MainActivity mainActivity = MainActivity.getInstance();
                    mainActivity.listenSmsBlocked(smsBlockedList);
                } catch (Exception e) {
                    Log.d("Exception caught", e.getMessage());
                }
            }
        }
    }
}