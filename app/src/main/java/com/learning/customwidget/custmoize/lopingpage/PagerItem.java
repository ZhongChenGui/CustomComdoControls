package com.learning.customwidget.custmoize.lopingpage;

/**
 * Created by chengui.zhong
 * on 2022/4/21
 */
public class PagerItem {
    private String title;

    private Integer coverResId;

    public PagerItem(String title, Integer coverResId) {
        this.title = title;
        this.coverResId = coverResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCoverResId() {
        return coverResId;
    }

    public void setCoverResId(Integer coverResId) {
        this.coverResId = coverResId;
    }
}
