package com.yanxin.filterdropmenu.library;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Created by YanXin on 2016/4/29.
 */
public abstract class BaseMenuAdapter implements IAdapter {

    protected Context mContext;
    protected String mDefaultMenuTitle;
    protected FilterDropMenu mFilterDropMenu;

    protected View mMenuContentView;
    protected TextView mMenuTitleView;

    protected MenuItem mSelectMenuItem;

    public BaseMenuAdapter(Context context, String defaultMenuTitle, FilterDropMenu filterDropMenu) {
        mContext = context;
        mDefaultMenuTitle = defaultMenuTitle;
        mFilterDropMenu = filterDropMenu;
    }

    @Override
    public String getDefaultMenuTitle() {
        return mDefaultMenuTitle;
    }

}
