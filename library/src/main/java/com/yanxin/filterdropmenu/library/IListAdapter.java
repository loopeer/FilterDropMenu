package com.yanxin.filterdropmenu.library;

import android.support.v7.widget.RecyclerView;

import java.util.List;

public interface IListAdapter extends IAdapter {

    List<MenuItem> getMenuItems();

    RecyclerView.Adapter getListAdapter();

}
