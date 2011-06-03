package at.roadrunner.android.util;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class AppInfo {

	/*
	 * returns true if a given intent is available
	 */
	public static boolean isIntentAvailable(Context context, String action) {
	    final PackageManager packageManager = context.getPackageManager();
	    final Intent intent = new Intent(action);
	    List<ResolveInfo> list =
	            packageManager.queryIntentActivities(intent,
	                    PackageManager.MATCH_DEFAULT_ONLY);
	    return list.size() > 0;
	}
	
	/*
	 * returns true if the given package is running
	 */
	public static boolean isAppRunning(Context context, String packageName) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    List<RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);
	    
	    for (RunningServiceInfo info : services) {
	    	if (info.service.getClassName().equals(packageName) ) {
	    		return true;
	    	}
	    }
	    
		return false;
	}
	
	/*
	 * returns true if the given package is installed
	 */
	public static boolean isAppInstalled(Context context, String packageName) {
		List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
		
		for (PackageInfo pkg : packages) {
			if (pkg.packageName.equals(packageName) ){
				return true;
			}
		}
		
		return false;
	}
}
