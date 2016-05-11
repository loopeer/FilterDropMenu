package com.yanxin.filterdropmenu.library;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yanxin.filterdropmenu.library.interfaces.IAdapter;

public abstract class BaseMenuAdapter implements IAdapter {

    protected Context mContext;
    protected String mDefaultMenuTitle;
    protected FilterDropMenu mFilterDropMenu;

    protected View mMenuContentView;
    protected TextView mMenuTitleView;

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
