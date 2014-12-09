/**
 * 
 */
package edu.ntut.csie.sslab1321.testagent.model;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Reverof
 * Open V
 * WEP V
 * WPA-PSK AES V
 * WPA2-PSK AES V
 * WPA/WPA2 瘛瑕�璅∪� PSK AES V
 * EAP X
 */
public class WifiAgent {
	private static final String TAG = "WifiAgent";

	// Constants used for different security types
	private static final String PSK = "PSK";
	private static final String WEP = "WEP";
	private static final String EAP = "EAP";
	private static final String OPEN = "Open";

	private static final String KEY_SSID = "ssid";
	private static final String KEY_SSID_PASSWORD = "password";
	
	private static final long WAITING_TIME = 5000; 

	private Context mContext;
	private String mSSID;
	private String mPassword;
	private WifiManager mWifiManager;

	public WifiAgent(Context context, Bundle bundle) {
		mContext = context;
		mSSID = bundle.getString(KEY_SSID);
		mPassword = bundle.getString(KEY_SSID_PASSWORD);
		mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (!mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(true);
			try {
				Thread.sleep(WAITING_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return {@code true} connect to the specified WiFi successfully
	 */
	public boolean connectToWifi() {
		List<ScanResult> scanResults = mWifiManager.getScanResults();
		if (scanResults == null) {
			Log.i(TAG, "Can not find any valid Wifi");
			return false;
		}
		
		for (ScanResult scanResult : scanResults) {
			if (scanResult.SSID.equals(mSSID)) {
				WifiConfiguration wifiConfig = new WifiConfiguration();
				String securityType = getScanResultSecurityType(scanResult);
				wifiConfig.SSID = "\"" + mSSID + "\"";
				if (securityType.equals(PSK)) {
					wifiConfig.preSharedKey = "\"" + mPassword + "\"";
				} else if (securityType.equals(WEP)) {
					wifiConfig.wepKeys[0] = "\"" + mPassword + "\"";
					wifiConfig.wepTxKeyIndex = 0;
					wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
					wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
				} else if (securityType.equals(EAP)) {
					Log.i(TAG, "Unimplemented feature");
				} else if (securityType.equals(OPEN)) {
					wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
				}
				mWifiManager.addNetwork(wifiConfig);
				List<WifiConfiguration> wifiConfigs = mWifiManager.getConfiguredNetworks();
				for (WifiConfiguration i : wifiConfigs) {
					if (i.SSID != null && i.SSID.equals("\"" + mSSID + "\"")) {
						mWifiManager.disconnect();
						mWifiManager.enableNetwork(i.networkId, true);
						return mWifiManager.reconnect();
					}
				}
			}
		}
		return false;
	}

	/**
	 * @return {@code true} if connected WiFis clear successfully
	 */
	public boolean clearConnectedWifis() {
		WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		List<WifiConfiguration> wifiConfigs = wifiManager.getConfiguredNetworks();
		boolean successFlag = true;
		for (WifiConfiguration i : wifiConfigs) {
			if (!wifiManager.removeNetwork(i.networkId)) {
				successFlag = false;
			}
		}
		return successFlag;
	}

	/**
	 * @return The security of a given {@link ScanResult}.
	 */
	private String getScanResultSecurityType(ScanResult scanResult) {
		final String cap = scanResult.capabilities;
		final String[] securityModes = { WEP, PSK, EAP };
		for (int i = securityModes.length - 1; i >= 0; i--) {
			if (cap.contains(securityModes[i])) {
				return securityModes[i];
			}
		}
		return OPEN;
	}
	
	public void setWifi(boolean condiction){
	    mWifiManager.setWifiEnabled(condiction);
	}
}
