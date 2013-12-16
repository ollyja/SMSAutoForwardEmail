package com.ringwraith.smsautoforwardemail;

import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.util.Log;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

	public static final String RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("onReceive", "Received!");

		if (intent.getAction().equals(RECEIVED)) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object objpdus[] = (Object[]) bundle.get("pdus");
				SmsMessage sms[] = new SmsMessage[objpdus.length];
				String msg = "";
				SimpleDateFormat datefmt = (SimpleDateFormat) SimpleDateFormat
						.getDateTimeInstance();
				datefmt.applyPattern("MM/dd HH:mm");

				for (int i = 0; i < objpdus.length; i++) {
					sms[i] = SmsMessage.createFromPdu((byte[]) objpdus[i]);
					msg += "Time: " + datefmt.format(sms[i].getTimestampMillis()) + "\n";
					msg += "From: " + sms[i].getOriginatingAddress() + "\n";
					msg += "Content: " + sms[i].getMessageBody() + "\n";
					Log.e("onReceive", msg);
					
				}
			}
		}
	}
}

	
