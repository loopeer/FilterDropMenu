package com.yanxin.filterdropmenu.library.interfaces;

import com.yanxin.filterdropmenu.library.MenuItem;

import java.util.List;

public interface IMultipleChoice {

    List<MenuItem> getSelectMenuItems();

    void setSelect(List<MenuItem> items);

}
