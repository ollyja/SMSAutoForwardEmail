package com.ringwraith.smsautoforwardemail;

import java.text.SimpleDateFormat;

import android.os.AsyncTask;
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
		// Log.e("onReceive", "Received!");

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
					msg += "Time: "
							+ datefmt.format(sms[i].getTimestampMillis())
							+ "\n";
					msg += "From: " + sms[i].getOriginatingAddress() + "\n";
					msg += "Content: " + sms[i].getMessageBody() + "\n";
					// Log.e("onReceive", msg);
					SendTask send = new SendTask();
					send.execute(msg);

				}
			}
		}
	}

	class SendTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			Email email = new Email("123456789@qq.com", "123456789");
			email.setFrom("123456789@qq.com");
			email.setTo(new String[] { "123456789@qq.com" });
			email.setSubject("New SMS");
			email.setBody(params[0]);

			try {
				// If you want add attachment use function addAttachment.
				// email.addAttachment("/sdcard/filelocation");

				if (email.send()) {
					// Log.e("doInBackground", "Email was sent successfully.");
				} else {
					Log.e("doInBackground", "Sent Failed!");
				}
			} catch (Exception e) {
				Log.e("doInBackground", "Exception!", e);
			}

			return "";
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			super.onProgressUpdate(progress);
		}

		@Override
		protected void onPostExecute(String r) {
			super.onPostExecute(r);
		}
	}
}
