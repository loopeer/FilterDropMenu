package com.yanxin.filterdropmenu.library;

import android.content.Context;

import com.yanxin.filterdropmenu.library.interfaces.IListAdapter;
import com.yanxin.filterdropmenu.library.interfaces.ISingleChoice;

import java.util.List;

public class DefaultSingleChoiceListAdapter extends BaseChoiceListAdapter implements IListAdapter, ISingleChoice, ItemListAdapter.onMenuItemClickListener {

    protected MenuItem mSelectMenuItem;

    private MenuItem mDefaultMenu;

    public DefaultSingleChoiceListAdapter(Context context, List<MenuItem> items, String defaultMenuTitle, FilterDropMenu filterDropMenu, MenuItem defaultMenuItem) {
        super(context, items, defaultMenuTitle, filterDropMenu, ItemListAdapter.ChoiceType.SINGLE_CHOICE);
        mSelectMenuItem = defaultMenuItem;
        mDefaultMenu = defaultMenuItem;
        updateMenuTitle();
    }

    private void updateMenuTitle() {
        mMenuTitleView.setText(mSelectMenuItem == null ||
                mSelectMenuItem.isDefault ? getDefaultMenuTitle() : mSelectMenuItem.name);
    }

    @Override
    public MenuItem getSelectMenuItem() {
        return mSelectMenuItem;
    }

    @Override
    public void setSelect(MenuItem item) {
        mSelectMenuItem = item;
        updateMenuTitle();
        mFilterDropMenu.closeMenu();

        if (mFilterDropMenu.getOnMenuSelectListener() != null)
            mFilterDropMenu.getOnMenuSelectListener().onMenuSelect(getAdapterPosition(), item);
    }

    @Override
    public void onMenuItemClick(MenuItem item, int position) {
        setSelect(item);
        mFilterDropMenu.closeMenu();
    }

    @Override
    public MenuItem getDefaultMenu() {
        return mDefaultMenu;
    }
}
