package com.liheng.cateen.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Li
 * 2018/6/22.
 */

public class TimeUtil {

    public static String dateFormat(String timestamp){
        if (timestamp==null){
            return "unknown";
        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = inputFormat.parse(timestamp);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return "unknown";
        }

    }

}
