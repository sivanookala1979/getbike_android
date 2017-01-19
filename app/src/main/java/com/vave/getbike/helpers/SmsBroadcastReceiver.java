package com.vave.getbike.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.vave.getbike.activity.LoginActivity;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
            }

            //this will update the UI with message
            LoginActivity inst = LoginActivity.instance();
            if (inst != null) {
                int index = smsMessageStr.indexOf(" is ");
                if (smsMessageStr.contains("NETSECURE") && index > 0) {
                    inst.updateOtp(smsMessageStr.substring(index + 4, index + 10));
                }
            }
        }
    }
}