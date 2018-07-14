package com.liheng.cateen.widget;

import android.view.View;
import android.view.ViewGroup;

/**
 * Li
 * 2018/6/23.
 */

public abstract class FootItem {

    public CharSequence loadingText;
    public CharSequence endText;
    public CharSequence pullToLoadText;

    public abstract View onCreateView(ViewGroup parent);

    public abstract void onBindData(View view,int state);

}
