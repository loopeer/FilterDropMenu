package com.yanxin.filterdropmenu.library;

import android.content.Context;

import com.yanxin.filterdropmenu.library.interfaces.IListAdapter;
import com.yanxin.filterdropmenu.library.interfaces.IMultipleChoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultMultipleChoiceListAdapter extends BaseChoiceListAdapter implements IListAdapter, IMultipleChoice, ItemListAdapter.onMenuItemClickListener, ItemListAdapter.onMenuMultiItemSureClickListener {

    private List<MenuItem> mMenuItems;

    public DefaultMultipleChoiceListAdapter(Context context, List<MenuItem> items, String defaultMenuTitle, FilterDropMenu filterDropMenu, MenuItem... defaultMenuItems) {
        super(context, items, defaultMenuTitle, filterDropMenu, ItemListAdapter.ChoiceType.MULTIPLE_CHOICE);
        mMenuItems = new ArrayList<>();
        mMenuItems.addAll(Arrays.asList(defaultMenuItems));
        updateMenuTitle();
        mItemListAdapter.setOnMenuMultiItemSureClickListener(this);
    }

    private void updateMenuTitle() {
        StringBuilder sb = new StringBuilder();
        if (mMenuItems == null ||
                mMenuItems.isEmpty() || mMenuItems.get(0).isDefault) {
            sb.append(getDefaultMenuTitle());
        } else {
            for (MenuItem menuItem : mMenuItems) {
                sb.append(menuItem.name);
                sb.append("/");
            }
            if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
        }
        mMenuTitleView.setText(sb.toString());
    }

    @Override
    public void onMenuItemClick(MenuItem item, int position) {
        if (item.isDefault) {
            mMenuItems.clear();
            mMenuItems.add(item);
            setSelect(mMenuItems);
            mFilterDropMenu.closeMenu();
        } else {
            if (!mMenuItems.isEmpty() && mMenuItems.get(0).isDefault) mMenuItems.remove(0);
            if (mMenuItems.contains(item))
                mMenuItems.remove(item);
            else
                mMenuItems.add(item);
            notifyDataSetChanged();
        }
        updateMenuTitle();
    }

    @Override
    public void onMenuSureClick() {
        setSelect(mMenuItems);
    }

    @Override
    public List<MenuItem> getSelectMenuItems() {
        return mMenuItems;
    }

    @Override
    public void setSelect(List<MenuItem> items) {
        notifyDataSetChanged();
        mFilterDropMenu.closeMenu();
        if (mFilterDropMenu.getOnMenuMultiSelectListener() != null)
            mFilterDropMenu.getOnMenuMultiSelectListener().onMenuSelect(getAdapterPosition(), items);
    }
}
