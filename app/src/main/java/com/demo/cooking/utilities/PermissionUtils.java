package com.demo.cooking.utilities;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * Created by hamdy on 11/15/2016.
 */
public class PermissionUtils {

    public final static int LOCATION_PERMISSION_CODE = 101;
    public final static int FILE_PERMISSION_CODE = 102;
    public final static int CAMERA_PERMISSION_CODE = 103;

    public static boolean isPermissionGranted(Context context, String permission){
        int permissionCheck = ContextCompat.checkSelfPermission(context, permission);
        if(permissionCheck == PackageManager.PERMISSION_DENIED)
            return false;
        return true;
    }

    public static void requestPermission(Fragment fragment, String permission, int code ){
        fragment.requestPermissions(new String[]{permission}, code);
    }

}
