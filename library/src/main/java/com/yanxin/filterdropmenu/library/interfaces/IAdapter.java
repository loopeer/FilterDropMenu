package com.yanxin.filterdropmenu.library.interfaces;

import android.view.View;
import android.widget.TextView;

public interface IAdapter {

    String getDefaultMenuTitle();

    View getMenuContentView();

    View getMenuTitleView();

    void notifyDataSetChanged();

    boolean isHasMenuContentView();

    View createMenuContentView();

    TextView createMenuTitleView();

    void setUnSelect();

    void setSelect();
}
