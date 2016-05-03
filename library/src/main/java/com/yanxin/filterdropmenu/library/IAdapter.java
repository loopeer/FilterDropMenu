package com.yanxin.filterdropmenu.library;

import android.view.View;
import android.widget.TextView;

public interface IAdapter {

    String getDefaultMenuTitle();

    View getMenuContentView();

    View getMenuTitleView();

    MenuItem getSelectMenuItem();

    void setSelect(MenuItem item);

    void notifyDataSetChanged();

    boolean isHasMenuContentView();

    View createMenuContentView();

    TextView createMenuTitleView();

    void setUnSelect();

    void setSelect();
}
