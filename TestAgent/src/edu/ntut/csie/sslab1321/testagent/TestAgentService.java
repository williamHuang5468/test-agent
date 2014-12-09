/**
 * 
 */
package edu.ntut.csie.sslab1321.testagent;

import java.util.List;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import edu.ntut.csie.sslab1321.testagent.model.ContactAgent;
import edu.ntut.csie.sslab1321.testagent.model.SettingAgent;
import edu.ntut.csie.sslab1321.testagent.model.WifiAgent;

/**
 * @author Reverof usage: start service command>adb shell am start
 *         edu.ntut.csie.
 *         sslab1321.testagent/edu.ntut.csie.sslab1321.testagent.DummyActivity
 *         action command>adb shell am broadcast -a testagent -e action
 *         STOP_TESTAGENT result command on Windows>adb logcat | find
 *         "result.behavior" result command on Linux>adb logcat | grep
 *         "result.behavior"
 */
public class TestAgentService extends Service {
    private static final String TESTAGENT_ACTION = "testagent";

    private static final String ACTION = "action";
    private static final String ACTION_STOP_TESTAGENT = "STOP_TESTAGENT";
    private static final String ACTION_CONNECT_TO_WIFI = "CONNECT_TO_WIFI";
    private static final String ACTION_TURN_ON_WIFI = "TURN_ON_WIFI";
    private static final String ACTION_TURN_OFF_WIFI = "TURN_OFF_WIFI";
    private static final String ACTION_TURN_ON_MOBILE_DATA = "TURN_ON_MOBILE_DATA";
    private static final String ACTION_TURN_OFF_MOBILE_DATA = "TURN_OFF_MOBILE_DATA";
    private static final String ACTION_CLEAR_CONNECTED_WIFIS = "CLEAR_CONNECTED_WIFIS";
    private static final String ACTION_OPEN_APP = "OPEN_APP";
    private static final String ACTION_ADD_CONTACT = "ADD_CONTACT";
    private static final String KEY_APP_NAME = "name";
    private final boolean TURN_ON = true;
    private final boolean TURN_OFF = false;

    private CommandReceiver mCommandReceiver;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Service#onBind(android.content.Intent)
     */
    @Override
    public IBinder onBind(Intent intent) {
	return null;
    }

    @Override
    public void onCreate() {
	IntentFilter intentFilter = new IntentFilter();
	intentFilter.addAction(TESTAGENT_ACTION);
	mCommandReceiver = new CommandReceiver();
	registerReceiver(mCommandReceiver, intentFilter);
	super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	setNotification(intent);
	return START_STICKY;
    }

    @Override
    public void onDestroy() {
	unregisterReceiver(mCommandReceiver);
	super.onDestroy();
    }

    // Make this service wouldn't stop when device becomes sleep.
    private void setNotification(Intent intent) {
	PendingIntent pendingIntnet = PendingIntent.getActivity(
		getApplicationContext(), 0, intent, 0);
	NotificationCompat.Builder builder = new NotificationCompat.Builder(
		getApplicationContext());
	builder.setSmallIcon(R.drawable.ic_launcher);
	builder.setContentTitle("Test Agent Service");
	builder.setTicker("Test Agent Service started...");
	builder.setContentText("Test Agent Service is running...");
	builder.setContentIntent(pendingIntnet);
	builder.setOngoing(true);
	Notification notification = builder.build();
	notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
	startForeground(1, notification);
    }

    private void handleRequest(Context context, Bundle bundle) {
	String actionName = bundle.getString(ACTION);
	if (actionName.equals(ACTION_STOP_TESTAGENT)) {
	    stopSelf();
	} else if (actionName.equals(ACTION_CONNECT_TO_WIFI)) {
	    new WifiAgent(context, bundle).connectToWifi();
	} else if (actionName.equals(ACTION_CLEAR_CONNECTED_WIFIS)) {
	    new WifiAgent(context, bundle).clearConnectedWifis();
	} else if (actionName.equals(ACTION_OPEN_APP)) {
	    openApp(context, bundle);
	} else if (actionName.equals(ACTION_ADD_CONTACT)) {
	    new ContactAgent(context, bundle).addContact();
	} else if (actionName.equals(ACTION_TURN_ON_WIFI)) {
	    new WifiAgent(context, bundle).setWifi(TURN_ON);
	} else if (actionName.equals(ACTION_TURN_OFF_WIFI)) {
	    new WifiAgent(context, bundle).setWifi(TURN_OFF);
	} else if (actionName.equals(ACTION_TURN_ON_MOBILE_DATA)) {
	    new SettingAgent(context, bundle).setMobileData(TURN_ON);
	} else if (actionName.equals(ACTION_TURN_OFF_MOBILE_DATA)) {
	    new SettingAgent(context, bundle).setMobileData(TURN_OFF);
	}
    }

    /**
     * Support English or Number of App Name only
     * 
     * @param context
     * @param bundle
     * @return {@code true} if specific app opened successfully
     */
    private boolean openApp(Context context, Bundle bundle) {
	String appName = bundle.getString(KEY_APP_NAME);
	Log.i(TESTAGENT_ACTION, "Input name: " + appName);

	final PackageManager packageManager = getPackageManager();
	List<ApplicationInfo> appInfos = packageManager
		.getInstalledApplications(PackageManager.GET_META_DATA);
	for (ApplicationInfo appInfo : appInfos) {
	    if (appInfo.loadLabel(packageManager).equals(appName)) {
		Log.i(TESTAGENT_ACTION, "Installed package: "
			+ appInfo.packageName);
		Log.i(TESTAGENT_ACTION, "Source dir: " + appInfo.sourceDir);
		startActivity(packageManager
			.getLaunchIntentForPackage(appInfo.packageName));
		return true;
	    }
	}
	return false;
    }

    // Wait for command received
    private class CommandReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
	    handleRequest(context, intent.getExtras());
	}
    }
}
