package com.yanxin.filterdropmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yanxin.filterdropmenu.library.DefaultListAdapter;
import com.yanxin.filterdropmenu.library.FilterDropMenu;
import com.yanxin.filterdropmenu.library.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 0X0001;
    public static final String EXTRA_SELECT_TAG = "extra_select_tag";

    FilterDropMenu mFilterDropMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFilterDropMenu = (FilterDropMenu) findViewById(R.id.filter_drop_menu);
        List<MenuItem> menuItems1 = new ArrayList<>();
        menuItems1.add(new MenuItem("全部", "", true));
        menuItems1.add(new MenuItem("测试1", ""));
        menuItems1.add(new MenuItem("测试11", ""));

        List<MenuItem> menuItems2 = new ArrayList<>();
        menuItems2.add(new MenuItem("全部", "", true));
        menuItems2.add(new MenuItem("测试2", ""));
        menuItems2.add(new MenuItem("测试22", ""));

        mFilterDropMenu.setAdapters(new DefaultListAdapter(this, menuItems1, "测试1", mFilterDropMenu, menuItems1.get(0))
                , new DefaultListAdapter(this, menuItems2, "测试2", mFilterDropMenu, menuItems2.get(0))
                , new DefaultListAdapter(this, null, "测试3", mFilterDropMenu, new MenuItem("", "")));

        mFilterDropMenu.setOnMenuClickListener(new FilterDropMenu.OnMenuClickListener() {
            @Override
            public void onClick(int position) {
                if (position == 2) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, CustomActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            MenuItem item = (MenuItem) data.getSerializableExtra(EXTRA_SELECT_TAG);
            mFilterDropMenu.getIAdapters()[2].setSelect(item);
        }
    }
}
