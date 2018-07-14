package com.liheng.cateen.config;

/**
 * Li
 * 2018/6/17.
 */

public class GlobalConfig {


    public final static String CATEGORY_NAME_APP = "App";
    public final static String CATEGORY_NAME_ANDROID = "Android";
    public final static String CATEGORY_NAME_IOS = "iOS";
    public final static String CATEGORY_NAME_FRONT_END = "前端";
    public final static String CATEGORY_NAME_RECOMMEND = "瞎推荐";
    public final static String CATEGORY_NAME_RESOURCE = "拓展资源";

    public static String[] getCategoryNames(){
        return new String[]{
                CATEGORY_NAME_APP,
                CATEGORY_NAME_ANDROID,
                CATEGORY_NAME_IOS,
                CATEGORY_NAME_FRONT_END,
                CATEGORY_NAME_RECOMMEND,
                CATEGORY_NAME_RESOURCE
        };
    }

}
