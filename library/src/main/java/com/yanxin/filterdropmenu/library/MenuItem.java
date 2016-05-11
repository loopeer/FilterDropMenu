package com.yanxin.filterdropmenu.library;

import java.io.Serializable;

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
