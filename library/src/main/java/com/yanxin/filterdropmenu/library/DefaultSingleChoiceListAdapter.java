package com.yanxin.filterdropmenu.library;

import android.content.Context;

import com.yanxin.filterdropmenu.library.interfaces.IListAdapter;
import com.yanxin.filterdropmenu.library.interfaces.ISingleChoice;

import java.util.List;

public class DefaultSingleChoiceListAdapter extends BaseChoiceListAdapter implements IListAdapter, ISingleChoice, ItemListAdapter.onMenuItemClickListener {

    protected MenuItem mSelectMenuItem;

    public DefaultSingleChoiceListAdapter(Context context, List<MenuItem> items, String defaultMenuTitle, FilterDropMenu filterDropMenu, MenuItem defaultMenuItem) {
        super(context, items, defaultMenuTitle, filterDropMenu, ItemListAdapter.ChoiceType.SINGLE_CHOICE);
        mSelectMenuItem = defaultMenuItem;
    }

    @Override
    public MenuItem getSelectMenuItem() {
        return mSelectMenuItem;
    }

    @Override
    public void setSelect(MenuItem item) {
        mSelectMenuItem = item;
        mMenuTitleView.setText(item.isDefault ? getDefaultMenuTitle() : item.name);
        mFilterDropMenu.closeMenu();

        if (mFilterDropMenu.getOnMenuSelectListener() != null)
            mFilterDropMenu.getOnMenuSelectListener().onMenuSelect(item);
    }

    @Override
    public void onMenuItemClick(MenuItem item, int position) {
        setSelect(item);
        mFilterDropMenu.closeMenu();
    }

}
