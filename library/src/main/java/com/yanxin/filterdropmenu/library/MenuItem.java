package com.yanxin.filterdropmenu.library;

import java.io.Serializable;

public class MenuItem implements Serializable {

    public String name;
    public String value;
    public boolean isDefault;

    public MenuItem() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuItem menuItem = (MenuItem) o;

        if (isDefault != menuItem.isDefault) return false;
        if (name != null ? !name.equals(menuItem.name) : menuItem.name != null) return false;
        return value != null ? value.equals(menuItem.value) : menuItem.value == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (isDefault ? 1 : 0);
        return result;
    }
}
