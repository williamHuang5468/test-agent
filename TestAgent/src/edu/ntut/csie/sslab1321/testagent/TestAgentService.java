/**
 * 
 */
package edu.ntut.csie.sslab1321.testagent;

import java.util.Date;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * @author Reverof
 *
 */
public class TestAgentService extends Service {
	private CommandReceiver mCommandReceiver;
	private Handler mHandler = new Handler();//範例
	
	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action");
		intentFilter.addAction("testagent.stop");
		mCommandReceiver = new CommandReceiver();
		registerReceiver(mCommandReceiver, intentFilter);
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mHandler.postDelayed(showTime, 1000);//範例
		setNotification(intent);
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		mHandler.removeCallbacks(showTime);//範例
		unregisterReceiver(mCommandReceiver);
		super.onDestroy();
	}
	
	// Make this service wouldn't stop when device becomes sleep.
	private void setNotification(Intent intent) {
		PendingIntent pendingIntnet = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setTicker("Test Agent service started...");
		builder.setContentText("Test Agent service is running...");
		builder.setContentIntent(pendingIntnet);
		builder.setOngoing(true);

		Notification notification = builder.build();
		notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
		startForeground(1, notification);
	}
	
	//範例
	private Runnable showTime = new Runnable() {
		public void run() {
			Log.e("Reverof", new Date().toString());
			mHandler.postDelayed(this, 1000);
		}
	};
}
