package com.yanxin.filterdropmenu.library;

import android.view.View;

public interface IAdapter {

    String getDefaultMenuTitle();

    View getMenuContentView();

    MenuItem getSelectMenuItem();

    void setSelect(MenuItem item);

    void notifyDataSetChanged();

}
