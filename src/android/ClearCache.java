package de.cyberkatze.phonegap.plugin;

import java.io.File;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class ClearCache extends CordovaPlugin {

    private static final String TAG = "ClearCachePlugin";

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        String url = data.getString(0);

        if (action.equals("clearAppCache")) {
        	final long duration = args.getLong(0);
                        cordova.getThreadPool().execute(new Runnable() {
                            public void run() {
                                    Log.i(TAG, String.format("Starting cache folder"));
                                                   int numDeletedFiles = clearCacheFolder(context.getCacheDir());
                                                   Log.i(TAG, String.format("Cache pruning completed, %d files deleted", numDeletedFiles));
                                callbackContext.success(numDeletedFiles); // Thread-safe.
                            }
                        });
                        return true;
        }

        return false;
    }//execute



    //helper method for clearCache() , recursive
    //returns number of deleted files
    static int clearCacheFolder(final File dir) {

        int deletedFiles = 0;
        if (dir!= null && dir.isDirectory()) {
            try {
                for (File child:dir.listFiles()) {

                    //first delete subdirectories recursively
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child);
                    }

                    //then delete the files and subdirectories in this dir
                    //only empty directories can be deleted, so subdirs have been done first

                        if (child.delete()) {
                            deletedFiles++;
                        }

                }
            }
            catch(Exception e) {
                Log.e(TAG, String.format("Failed to clean the cache, error %s", e.getMessage()));
            }
        }
        return deletedFiles;
    }
}
