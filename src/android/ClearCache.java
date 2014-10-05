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
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                        clearCacheFolder(cordova.getActivity().getExternalCacheDir());
                        callbackContext.success(); // Thread-safe.
                }
            });
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
                    child.delete();
                }
            } catch(Exception e) {
                Log.e(TAG, String.format("Failed to clean the cache, error %s", e.getMessage()));
            }
        }
        return deletedFiles;
    }
}
