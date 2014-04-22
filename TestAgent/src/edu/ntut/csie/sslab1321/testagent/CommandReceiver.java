/**
 * 
 */
package edu.ntut.csie.sslab1321.testagent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Reverof
 *
 */
public class CommandReceiver extends BroadcastReceiver {

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("testagent.stop")) {
			context.stopService(new Intent(context, TestAgentService.class));
		}
	}

}
