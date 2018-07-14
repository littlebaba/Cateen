package com.liheng.cateen.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liheng.cateen.R;

import org.w3c.dom.Text;

/**
 * Li
 * 2018/6/23.
 */

public class DefaultFootItem extends FootItem{

    private ProgressBar mProgressBar;
    private TextView mLoadingText;
    private TextView mEndTextView;


    @Override
    public View onCreateView(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.rv_with_footer_loading, parent, false);
        mProgressBar = view.findViewById(R.id.rv_with_footer_loading_progress);
        mEndTextView = view.findViewById(R.id.rv_with_footer_loading_end);
        mLoadingText = view.findViewById(R.id.rv_with_footer_loading_load);
        return view;
    }

    @Override
    public void onBindData(View view, int state) {
        if (state == RecyclerViewWithFooter.STATE_LOADING){
            if (TextUtils.isEmpty(loadingText)){
                showProgressBar(view.getContext().getResources().getString(R.string.rv_with_footer_loading));
            }else {
                showProgressBar(loadingText);
            }
        }else if (state == RecyclerViewWithFooter.STATE_END){
            showEnd(endText);
        }else if (state == RecyclerViewWithFooter.STATE_PULL_TO_LOAD){
            if (TextUtils.isEmpty(pullToLoadText)){
                showPullToLoad(view.getContext().getResources().getString(R.string.rv_with_footer_pull_load_more));
            }else {
                showProgressBar(pullToLoadText);
            }
        }
    }

    private void showPullToLoad(CharSequence string) {
        showEnd(string);
    }

    private void showEnd(CharSequence endText) {
        mEndTextView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mLoadingText.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(endText)){
            mEndTextView.setText(endText);
        }
    }

    private void showProgressBar(CharSequence load) {
        mEndTextView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(load)){
            mLoadingText.setVisibility(View.VISIBLE);
            mLoadingText.setText(load);
        }else {
            mLoadingText.setVisibility(View.GONE);
        }
    }
}
