package com.liheng.cateen.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.liheng.cateen.R;
import com.liheng.cateen.utils.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Li
 * 2018/6/15.
 */

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInit();
        if (getContentViewId() != 0) {
            setContentView(getContentViewId());
            intView(savedInstanceState);
        }

    }

    protected abstract void intView(Bundle savedInstanceState);

    protected void beforeInit() {
    }

    protected abstract int getContentViewId();
}
