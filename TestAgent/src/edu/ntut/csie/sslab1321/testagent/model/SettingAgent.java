/**
 * 
 */
package edu.ntut.csie.sslab1321.testagent.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

/**
 * @author Reverof
 * 
 */
public class SettingAgent {
    private Context mContext;

    public SettingAgent(Context context, Bundle bundle) {
	mContext = context;
    }

    public void setMobileData(boolean enable) {
	try {
	    final ConnectivityManager conman = (ConnectivityManager) mContext
		    .getSystemService(Context.CONNECTIVITY_SERVICE);
	    final Class conmanClass = Class
		    .forName(conman.getClass().getName());
	    final Field iConnectivityManagerField = conmanClass
		    .getDeclaredField("mService");
	    iConnectivityManagerField.setAccessible(true);
	    final Object iConnectivityManager = iConnectivityManagerField
		    .get(conman);
	    final Class iConnectivityManagerClass = Class
		    .forName(iConnectivityManager.getClass().getName());
	    final Method setMobileDataEnabledMethod = iConnectivityManagerClass
		    .getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
	    setMobileDataEnabledMethod.setAccessible(true);
	    setMobileDataEnabledMethod.invoke(iConnectivityManager, enable);
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (NoSuchFieldException e) {
	    e.printStackTrace();
	} catch (SecurityException e) {
	    e.printStackTrace();
	} catch (NoSuchMethodException e) {
	    e.printStackTrace();
	} catch (IllegalArgumentException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
    }

    public boolean addContact() {
	ArrayList<ContentProviderOperation> contentProviderOperation = new ArrayList<ContentProviderOperation>();
	contentProviderOperation.add(ContentProviderOperation
		.newInsert(ContactsContract.RawContacts.CONTENT_URI)
		.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
		.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
		.build());

	// Asking the Contact provider to create a new contact
	try {
	    mContext.getContentResolver().applyBatch(
		    ContactsContract.AUTHORITY, contentProviderOperation);
	} catch (Exception e) {
	    e.printStackTrace();
	    // show exception in toast
	    Toast.makeText(mContext, "Exception: " + e.getMessage(),
		    Toast.LENGTH_SHORT).show();
	    return false;
	}
	return true;
    }
}
