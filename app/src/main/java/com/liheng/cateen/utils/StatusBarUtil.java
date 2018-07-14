package com.liheng.cateen.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.liheng.cateen.R;

import java.sql.BatchUpdateException;

/**
 * Li
 * 2018/6/14.
 */

public class StatusBarUtil {

    public static final int DEFAULT_STATUS_BAR_ALPHA = 112;
    private static final int FAKE_TRANSLUCENT_VIEW_ID = R.id.statusbarutil_translucent_view;

    public static void setTranslucent(Activity activity) {
        setTranslucent(activity, DEFAULT_STATUS_BAR_ALPHA);
    }

    public static void setTranslucent(Activity activity, int defaultStatusBarAlpha) {
        setTransparent(activity);
        addTranslucentView(activity, defaultStatusBarAlpha);
    }


    public static void setTransparent(Activity activity) {
        transparentStatusBar(activity);
        setRootView(activity);
    }


    private static void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private static void setRootView(Activity activity) {
        ViewGroup parent = activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup){
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

    public static void addTranslucentView(Activity activity, int defaultStatusBarAlpha) {
        ViewGroup contentView = activity.findViewById(android.R.id.content);
        View fakeTranslucentView = contentView.findViewById(FAKE_TRANSLUCENT_VIEW_ID);
        if (fakeTranslucentView != null){
            if (fakeTranslucentView.getVisibility() == View.GONE){
                fakeTranslucentView.setVisibility(View.VISIBLE);
            }
            fakeTranslucentView.setBackgroundColor(Color.argb(defaultStatusBarAlpha,0,0,0));
        }else{
            contentView.addView(createTranslucentStatusBarView(activity,defaultStatusBarAlpha));
        }
    }

    private static View createTranslucentStatusBarView(Activity activity, int defaultStatusBarAlpha) {
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.argb(defaultStatusBarAlpha,0,0,0));
        statusBarView.setId(FAKE_TRANSLUCENT_VIEW_ID);
        return statusBarView;
    }

    private static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelOffset(resourceId);
    }


}
