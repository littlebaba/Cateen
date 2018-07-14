package com.liheng.cateen.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Li
 * 2018/6/21.
 */

public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    protected List<T> mData;
    private int layoutId;
    private View mView;


    public void addData(List<T> data){
        if (data != null){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void setData(List<T> data){
        mData.clear();
        addData(data);
    }

    public CommonRecyclerAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        this.mData = data==null?new ArrayList<T>() : data;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(layoutId, parent, false);
        return new CommonRecyclerHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommonRecyclerHolder){
            CommonRecyclerHolder commonHolder = (CommonRecyclerHolder) holder;
            commonHolder.position = position;
            convert(commonHolder,mData.get(position));
        }
    }


    @Override
    public int getItemCount() {
        return mData!=null?mData.size():0;
    }


    protected abstract void convert(CommonRecyclerHolder commonHolder, T t);
}
