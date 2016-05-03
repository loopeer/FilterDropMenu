package com.yanxin.filterdropmenu.library;

import java.io.Serializable;

/**
 * Created by YanXin on 2016/4/29.
 */
public class MenuItem implements Serializable {

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
