package com.demo.cooking.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.demo.cooking.models.Customer;
import com.google.gson.Gson;

/**
 * Created by egypt2 on 10/06/2017.
 */

public class PreferenceManager {
    private static final String TAG = PreferenceManager.class.getName();

    private static final String APP_PREF_NAME = "MyPrefsFile";

    private static final String PREF_CUSTOMER_DATA = "customer";

    public static String getPrefCustomerData() {
        return PREF_CUSTOMER_DATA;
    }

    public static SharedPreferences getAppSharedPreferences(Context context) {
        if (context == null)
            return null;
        return context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static boolean setCustomerData(Context context, String data) {
        return getAppSharedPreferences(context).edit().putString(PREF_CUSTOMER_DATA, data).commit();
    }

    public static Customer getCustomerData(Context context) {
        if (context == null)
            return null;
        String customerDataJson = getAppSharedPreferences(context).getString(PREF_CUSTOMER_DATA, "");
        Log.i(TAG, "getCustomerData " + customerDataJson);
        if (TextUtils.isEmpty(customerDataJson))
            return null;
        return new Gson().fromJson(customerDataJson, Customer.class);
    }

}
