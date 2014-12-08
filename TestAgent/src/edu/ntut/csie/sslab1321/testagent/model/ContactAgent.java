/**
 * 
 */
package edu.ntut.csie.sslab1321.testagent.model;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

/**
 * @author Reverof
 * 
 */
public class ContactAgent {
	private static final String KEY_DISPLAY_NAME = "NAME";
	private static final String KEY_MOBILE_NUMBER = "PHONE";
	private static final String KEY_HOME_NUMBER = "HOME_NUMBER";
	private static final String KEY_WORK_NUMBER = "WORK_NUMBER";
	private static final String KEY_HOME_EMAIL = "HOME_EMAIL";
	private static final String KEY_WORK_EMAIL = "WORK_EMAIL";
	private static final String KEY_COMPANY_NAME = "COMPANY_NAME";
	private static final String KEY_JOB_TITLE = "JOB_TITLE";

	private Context mContext;

	private String mDisplayName;
	private String mMobileNumber;
	private String mHomeNumber;
	private String mWorkNumber;
	private String mHomeEmail;
	private String mWorkEmail;
	private String mCompanyName;
	private String mJobTitle;

	public ContactAgent(Context context, Bundle bundle) {
		mContext = context;
		mDisplayName = bundle.getString(KEY_DISPLAY_NAME);
		mMobileNumber = bundle.getString(KEY_MOBILE_NUMBER);
		mHomeNumber = bundle.getString(KEY_HOME_NUMBER);
		mWorkNumber = bundle.getString(KEY_WORK_NUMBER);
		mHomeEmail = bundle.getString(KEY_HOME_EMAIL);
		mWorkEmail = bundle.getString(KEY_WORK_EMAIL);
		mCompanyName = bundle.getString(KEY_COMPANY_NAME);
		mJobTitle = bundle.getString(KEY_JOB_TITLE);
	}

	public boolean addContact() {
		ArrayList<ContentProviderOperation> contentProviderOperation = new ArrayList<ContentProviderOperation>();
		contentProviderOperation.add(ContentProviderOperation
			.newInsert(ContactsContract.RawContacts.CONTENT_URI)
			.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
			.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
			.build());
		
		// ------------------------------------------------------ Names
		if (mDisplayName != null) {
			contentProviderOperation
				.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, mDisplayName)
				.build());
		}
		
		// ------------------------------------------------------ Mobile Number
		if (mMobileNumber != null) {
			contentProviderOperation
				.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mMobileNumber)
				.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
				.build());
		}
		
		// ------------------------------------------------------ Home Numbers
		if (mHomeNumber != null) {
			contentProviderOperation
				.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mHomeNumber)
				.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
				.build());
		}
		
		// ------------------------------------------------------ Work Numbers
		if (mWorkNumber != null) {
			contentProviderOperation
				.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mWorkNumber)
				.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
				.build());
		}
		
		// ------------------------------------------------------ workEmail
		if (mWorkEmail != null) {
			contentProviderOperation
				.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Email.DATA, mWorkEmail)
				.withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
				.build());
		}
		
		// ------------------------------------------------------ homeEmail
		if (mHomeEmail != null) {
			contentProviderOperation
				.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Email.DATA, mHomeEmail)
				.withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
				.build());
		}
		
//		// ------------------------------------------------------ Organization
//		if (!mCompanyName.equals("") && !mJobTitle.equals("")) {
//			contentProviderOperation
//				.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//				.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
//				.withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, mCompanyName)
//				.withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
//				.withValue(ContactsContract.CommonDataKinds.Organization.TITLE, mJobTitle)
//				.withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
//				.build());
//		}
		
		// Asking the Contact provider to create a new contact
		try {
			mContext.getContentResolver().applyBatch(ContactsContract.AUTHORITY, contentProviderOperation);
		} catch (Exception e) {
			e.printStackTrace();
			// show exception in toast
			Toast.makeText(mContext, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
