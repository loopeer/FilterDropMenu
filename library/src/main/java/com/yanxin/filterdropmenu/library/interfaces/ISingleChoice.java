package com.yanxin.filterdropmenu.library.interfaces;

import com.yanxin.filterdropmenu.library.MenuItem;

public interface ISingleChoice {

    MenuItem getSelectMenuItem();

    MenuItem getDefaultMenu();

    void setSelect(MenuItem item);

}
