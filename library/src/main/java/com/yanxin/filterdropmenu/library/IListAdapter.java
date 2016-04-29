package com.yanxin.filterdropmenu.library;

import android.view.View;

import java.util.List;

/**
 * Created by YanXin on 2016/4/29.
 */
public interface IListAdapter extends IAdapter {

    View getMenuContentView();

    List<MenuItem> getMenuItems();

}
