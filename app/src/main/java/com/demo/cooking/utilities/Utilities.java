package com.demo.cooking.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.cooking.R;
import com.demo.cooking.logging.Logger;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hamdy on 11/24/2016.
 */
public class Utilities {

    /**
     * Converts density independent pixels to pixels.
     *
     * @param dp Dp to convert.
     * @return Pixel value.
     */
    public static float convertDpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * Converts pixels to density independent pixels.
     *
     * @param px Pixels to convert.
     * @return Dp value.
     */
    public static float convertPxToDp(float px) {
        return px / Resources.getSystem().getDisplayMetrics().density;
    }

    public static String formatTimeInMilliSeconds(long timeInMilliSeconds) {
        Date date = new Date(timeInMilliSeconds);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
        return formatter.format(date);
    }

    public static String getMacAddress(Context context) {
        WifiManager wimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String macAddress = wimanager.getConnectionInfo().getMacAddress();
        if (macAddress == null) {
            Logger.error(context.getClass().getSimpleName(), "Device don't have mac address or wi-fi is disabled");
            macAddress = "";
        }
        return macAddress;
    }

    public static void sendEmail(String email, Context context) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        try {
            context.startActivity(Intent.createChooser(emailIntent, context.getString(R.string.contact_us_email_client_chooser_title)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, context.getString(R.string.contact_us_error_no_email_client), Toast.LENGTH_SHORT).show();
        }
    }


    public static void showHidePassword(EditText password, View showIcon, View hideIcon, boolean shouldShowPassword) {
        showIcon.setVisibility(View.VISIBLE);
        hideIcon.setVisibility(View.INVISIBLE);
        if (shouldShowPassword)
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        else
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        password.setSelection(password.getText().length());
    }

    //Convert arabic number to english number "," NOT added here
    private static final String arabic = "\u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9";

    public static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    //Convert arabic number to english number "," added here
    public static String getUSNumber(String Numtoconvert) {

        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        try {
            if (Numtoconvert.contains("٫"))
                Numtoconvert = formatter.parse(Numtoconvert.split("٫")[0].trim()) + "." + formatter.parse(Numtoconvert.split("٫")[1].trim());
            else
                Numtoconvert = formatter.parse(Numtoconvert).toString();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            Logger.error("Could not convert Arabic number", e.getMessage());
        }
        return Numtoconvert;
    }

}
