package com.yanxin.filterdropmenu.library.interfaces;

import android.support.v7.widget.RecyclerView;

import com.yanxin.filterdropmenu.library.MenuItem;

import java.util.List;

public interface IListAdapter extends IAdapter {

    List<MenuItem> getMenuItems();

    RecyclerView.Adapter getListAdapter();

}
