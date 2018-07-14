package com.liheng.cateen.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Li
 * 2018/6/15.
 */

public class ImageResult {
    public boolean error;



    public List<ResultBean> results;

    public static class ResultBean {
        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;
        public List<String> images;

    }
}
