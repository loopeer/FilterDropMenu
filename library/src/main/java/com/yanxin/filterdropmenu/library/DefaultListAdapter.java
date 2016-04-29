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
public class DefaultListAdapter implements IListAdapter {

    private Context mContext;
    private List<MenuItem> mMenuItems;
    private String mDefaultMenuTitle;
    private FilterDropMenu mFilterDropMenu;

    public DefaultListAdapter(Context context, List<MenuItem> items, String defaultMenuTitle, FilterDropMenu filterDropMenu) {
        mContext = context;
        mMenuItems = items;
        mDefaultMenuTitle = defaultMenuTitle;
        mFilterDropMenu = filterDropMenu;
    }

    @Override
    public View getMenuContentView() {
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(mContext).inflate(R.layout.view_recycler_view, null);
        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new ItemListAdapter(mContext, mMenuItems, mFilterDropMenu));
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return mMenuItems;
    }

    @Override
    public String getDefaultMenuTitle() {
        return mDefaultMenuTitle;
    }

}
