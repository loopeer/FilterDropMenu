package com.yanxin.filterdropmenu.library;

/**
 * Created by YanXin on 2016/4/29.
 */
public class MenuItem {

    public String name;
    public String value;
    public boolean isDefault;

    public MenuItem(String name, String value, boolean isDefault) {
        this.name = name;
        this.value = value;
        this.isDefault = isDefault;
    }

    public MenuItem(String name, String value) {
        this.name = name;
        this.value = value;
        this.isDefault = false;
    }

}
