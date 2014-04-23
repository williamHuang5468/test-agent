/**
 * 
 */
package edu.ntut.csie.sslab1321.testagent;

/**
 * @author Reverof
 * usage:
 * action command>adb shell am broadcast -a action.behavior
 * result command on Windows>adb logcat | find "result.behavior"
 * result command on Linux>adb logcat | grep "result.behavior"
 */
public class TestIntent {
	public static final String ACTION_STOP_TESTAGENT = "action.STOP_TESTAGENT";
	public static final String ACTION_CONNECT_TO_WIFI = "action.CONNECT_TO_WIFI";
	public static final String RESULT_STOP_TESTAGENT = "result.STOP_TESTAGENT";
	public static final String RESULT_CONNECT_TO_WIFI = "result.CONNECT_TO_WIFI";
}
