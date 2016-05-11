package com.yanxin.filterdropmenu.library;

import android.content.Context;

import com.yanxin.filterdropmenu.library.interfaces.IListAdapter;
import com.yanxin.filterdropmenu.library.interfaces.IMultipleChoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultMultipleChoiceListAdapter extends BaseChoiceListAdapter implements IListAdapter, IMultipleChoice, ItemListAdapter.onMenuItemClickListener {

    private List<MenuItem> mMenuItems;

    public DefaultMultipleChoiceListAdapter(Context context, List<MenuItem> items, String defaultMenuTitle, FilterDropMenu filterDropMenu, MenuItem... defaultMenuItems) {
        super(context, items, defaultMenuTitle, filterDropMenu, ItemListAdapter.ChoiceType.MULTIPLE_CHOICE);
        mMenuItems = new ArrayList<>();
        mMenuItems.addAll(Arrays.asList(defaultMenuItems));
    }

    @Override
    public void onMenuItemClick(MenuItem item, int position) {
        if (mMenuItems.contains(item))
            mMenuItems.remove(item);
        else
            mMenuItems.add(item);
        notifyDataSetChanged();
    }

    @Override
    public List<MenuItem> getSelectMenuItems() {
        return mMenuItems;
    }

    @Override
    public void setSelect(MenuItem... items) {
        mMenuItems.clear();
        int i = 0;
        for (MenuItem item : items) {
            mMenuItems.add(item);
            i++;
        }
        notifyDataSetChanged();
    }

}
