package com.yanxin.filterdropmenu.library;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * Created by YanXin on 2016/4/29.
 */
public class DefaultListAdapter extends BaseMenuAdapter implements IListAdapter, ItemListAdapter.onMenuItemClickListener {

    private List<MenuItem> mMenuItems;
    private ItemListAdapter mItemListAdapter;

    public DefaultListAdapter(Context context, List<MenuItem> items, String defaultMenuTitle, FilterDropMenu filterDropMenu) {
        super(context, defaultMenuTitle, filterDropMenu);
        mMenuItems = items;
        mItemListAdapter = new ItemListAdapter(mContext, mMenuItems, mFilterDropMenu, this);
        mItemListAdapter.setOnMenuItemClickListener(this);
    }

    @Override
    public View getMenuContentView() {
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(mContext).inflate(R.layout.view_recycler_view, null);
        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    @Override
    public MenuItem getSelectMenuItem() {
        return mSelectMenuItem;
    }

    @Override
    public void setSelect(MenuItem item) {
        mSelectMenuItem = item;
        mFilterDropMenu.setSelectTab(item);
    }

    @Override
    public void notifyDataSetChanged() {
        mItemListAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mItemListAdapter);
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return mMenuItems;
    }

    @Override
    public void onMenuItemClick(MenuItem item) {
        setSelect(item);
        mFilterDropMenu.closeMenu();
    }
}
