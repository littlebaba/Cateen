package com.liheng.cateen.module.fragment.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.liheng.cateen.R;
import com.liheng.cateen.base.adapter.CommonRecyclerAdapter;
import com.liheng.cateen.base.adapter.CommonRecyclerHolder;
import com.liheng.cateen.model.ImageResult;
import com.liheng.cateen.utils.TimeUtil;

import java.util.List;

/**
 * Li
 * 2018/6/22.
 */

public class CategoryRecyclerAdapter extends CommonRecyclerAdapter<ImageResult.ResultBean> {


    public CategoryRecyclerAdapter(Context context) {
        super(context, null, R.layout.item_category);
    }

    @Override
    protected void convert(CommonRecyclerHolder holder, ImageResult.ResultBean resultBean) {
        if (resultBean != null) {
            ImageView imageView = holder.getView(R.id.category_item_img);
            if (resultBean.images != null && resultBean.images.size() > 0) {
                Glide.with(mContext).load(resultBean.images.get(0)+"?imageView2/0/w/190")
                        .placeholder(R.mipmap.image_default)
                        .error(R.mipmap.image_default).into(imageView);
            }else {
                Glide.with(mContext).load(R.mipmap.image_default).into(imageView);
            }
            holder.setTextViewText(R.id.category_item_desc, resultBean.desc == null ? "unknown" : resultBean.desc);
            holder.setTextViewText(R.id.category_item_author, resultBean.who == null ? "unknown" : resultBean.who);
            holder.setTextViewText(R.id.category_item_src, resultBean.source == null ? "unknown" : resultBean.source);
            holder.setTextViewText(R.id.category_item_time, TimeUtil.dateFormat(resultBean.publishedAt));
        }
    }
}
