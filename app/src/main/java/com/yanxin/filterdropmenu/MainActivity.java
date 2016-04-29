package com.yanxin.filterdropmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yanxin.filterdropmenu.library.DefaultListAdapter;
import com.yanxin.filterdropmenu.library.FilterDropMenu;
import com.yanxin.filterdropmenu.library.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FilterDropMenu mFilterDropMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFilterDropMenu = (FilterDropMenu) findViewById(R.id.filter_drop_menu);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("全部", "", true));
        menuItems.add(new MenuItem("哈哈哈", ""));
        menuItems.add(new MenuItem("啦啦啦", ""));
        mFilterDropMenu.setAdapters(new DefaultListAdapter(this, menuItems, "测试", mFilterDropMenu)
                , new DefaultListAdapter(this, menuItems, "测试", mFilterDropMenu));
    }
}
