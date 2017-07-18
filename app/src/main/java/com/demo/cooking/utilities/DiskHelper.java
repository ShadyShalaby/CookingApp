package com.demo.cooking.utilities;

import android.os.Environment;

/**
 * Created by hamdy on 8/21/2016.
 */
public class DiskHelper {
    //    Return true if External storage is mounted for read and write
    public boolean isSdCardMounted() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);

    }
}
