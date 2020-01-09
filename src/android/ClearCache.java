package de.cyberkatze.phonegap.plugin;

import java.io.File;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class ClearCache extends CordovaPlugin {

    private static final String TAG = "ClearCachePlugin";

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("clearAppCache")) {
            /*cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                       int result = clearCacheFolder(cordova.getActivity().getExternalCacheDir());
					   result += clearCacheFolder(cordova.getActivity().getCacheDir());
                       callbackContext.success(result); // Thread-safe.
                }
            });*/
			cordova.getThreadPool().execute(new MyRunnable(cordova,callbackContext));
            return true;
        }
        return false;
    }//execute


    static int clearCacheFolder(final File dir) {
        if (dir!= null && dir.isDirectory()) {
            try {
                for (File child:dir.listFiles()) {
                    if (child.isDirectory()) {
                        clearCacheFolder(child);
                    }

                    Log.e(TAG, String.format("开始移除文件： %s", child.getAbsolutePath()));
                    child.delete();
                }
            } catch(Exception e) {
                Log.e(TAG, String.format("Failed to clean the cache, error %s", e.getMessage()));

                return 1;
            }
        }
        return 0;
    }
	
	private static class MyRunnable implements Runnable {
      private CordovaInterface cordova;
      private CallbackContext callbackContext;
      public MyRunnable(CordovaInterface cordova,CallbackContext callbackContext) {
        this.cordova = cordova;
        this.callbackContext = callbackContext;
      }
      @Override
      public void run() {
        try {
          int result = clearCacheFolder(cordova.getActivity().getExternalCacheDir());
          callbackContext.success(result); // Thread-safe.
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
	
}
