package com.liheng.cateen.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Li
 * 2018/6/23.
 */

public class RecyclerViewWithFooter extends RecyclerView {

    public static final int STATE_END = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_EMPTY = 2;
    public static final int STATE_NONE = 3;
    public static final int STATE_PULL_TO_LOAD = 4;

    private static final String TAG = "RecyclerViewWithFooter";
    private boolean mIsGetDataForNet = false;

    private int mState = STATE_NONE;

    private FootItem mFootItem = new DefaultFootItem();

    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            reset();
        }

        private void reset() {
            mIsGetDataForNet = false;
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            reset();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            reset();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            reset();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            reset();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            reset();
        }
    };


    public RecyclerViewWithFooter(Context context) {
        super(context);
        init();
    }

    public RecyclerViewWithFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecyclerViewWithFooter(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
    }


    @Override
    public void setAdapter(Adapter adapter) {
        LoadMoreAdapter loadMoreAdapter;
        if (adapter instanceof LoadMoreAdapter) {
            loadMoreAdapter = (LoadMoreAdapter) adapter;
            loadMoreAdapter.registerAdapterDataObserver(mAdapterDataObserver);
            super.setAdapter(adapter);
        } else {
            loadMoreAdapter = new LoadMoreAdapter(adapter);
            loadMoreAdapter.registerAdapterDataObserver(mAdapterDataObserver);
            super.setAdapter(loadMoreAdapter);
        }
    }


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        mState = STATE_PULL_TO_LOAD;
        final OnLoadMoreListenerWrapper wrapper = new OnLoadMoreListenerWrapper(onLoadMoreListener);
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager){
                        int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        if (lastVisibleItemPosition >= recyclerView.getAdapter().getItemCount()-1){
                            if (mState == STATE_PULL_TO_LOAD){
                                setLoading();
                            }
                            wrapper.onLoadMore();
                        }
                    }
                }
            }
        });
    }

    public void setLoading() {
        if (getAdapter() != null){
            mState = STATE_LOADING;
            mIsGetDataForNet = false;
            getAdapter().notifyItemChanged(getAdapter().getItemCount()-1);
        }
    }


    public void setEnd(CharSequence end) {
        if (getAdapter() != null) {
            mIsGetDataForNet = false;
            mState = STATE_END;
            mFootItem.endText = end;
            getAdapter().notifyItemChanged(getAdapter().getItemCount() - 1);
        }
    }

    public void setEmpty() {
        if (getAdapter() != null) {
            mState = STATE_EMPTY;
            if (isEmpty()) {
                getAdapter().notifyDataSetChanged();
            }
        }
    }

    /**
     * 数据是否为空
     */
    private boolean isEmpty() {
        return (mState == STATE_NONE && getAdapter().getItemCount() == 0) ||
                (mState != STATE_NONE && getAdapter().getItemCount() == 1);
    }



    private class OnLoadMoreListenerWrapper implements OnLoadMoreListener{

        private OnLoadMoreListener mOnLoadMoreListener;

        public OnLoadMoreListenerWrapper(OnLoadMoreListener onLoadMoreListener){
            mOnLoadMoreListener = onLoadMoreListener;
        }

        @Override
        public void onLoadMore() {
            if (!mIsGetDataForNet&&!isLoadMoreEnable()){
                mIsGetDataForNet = true;
                mOnLoadMoreListener.onLoadMore();
            }
        }
    }

    public boolean isLoadMoreEnable(){
        return mState != STATE_LOADING;
    }

    public class LoadMoreAdapter extends Adapter<ViewHolder> {

        public static final int LOAD_MORE_VIEW_TYPE = -404;
        public static final int EMPTY_VIEW_TYPE = -403;

        public Adapter mAdapter;

        public LoadMoreAdapter(Adapter adapter) {
            mAdapter = adapter;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == LOAD_MORE_VIEW_TYPE){
                return new LoadMoreVH();
            }else if (viewType == EMPTY_VIEW_TYPE){
//                return new EmptyVH();
            }
            return mAdapter.onCreateViewHolder(parent,viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (!isFootView(position)){
                mAdapter.onBindViewHolder(holder,position);
            }else {
                if (holder instanceof VH){
                    ((VH)holder).onBindViewHolder();
                }
            }
        }


        private boolean isFootView(int position) {
            return position == getItemCount()-1 && mState != STATE_NONE;
        }


        @Override
        public int getItemViewType(int position) {
            if (!isFootView(position)) {
                return mAdapter.getItemViewType(position);
            } else {
                if (mState == STATE_EMPTY && getItemCount() == 1) {
                    return EMPTY_VIEW_TYPE;
                } else {
                    return LOAD_MORE_VIEW_TYPE;
                }
            }
        }

        @Override
        public int getItemCount() {
            if (mState == STATE_NONE){
                return mAdapter.getItemCount();
            }else {
                return mAdapter.getItemCount()+1;
            }
        }

        @Override
        public void registerAdapterDataObserver(@NonNull AdapterDataObserver observer) {
//            super.registerAdapterDataObserver(observer);
            mAdapter.registerAdapterDataObserver(observer);
        }


        private class LoadMoreVH extends VH {
            private View mItemView;
            public LoadMoreVH() {
                super(mFootItem.onCreateView(RecyclerViewWithFooter.this));
                mItemView = itemView;
            }

            @Override
            public void onBindViewHolder() {
                super.onBindViewHolder();
                if (mState == STATE_LOADING || mState == STATE_END || mState==STATE_PULL_TO_LOAD){
                    mFootItem.onBindData(mItemView,mState);
                }
            }
        }

        class VH extends ViewHolder{

            public VH(View itemView) {
                super(itemView);
            }

            public void onBindViewHolder(){

            }
        }
    }
}
