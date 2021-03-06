package com.yanxin.filterdropmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yanxin.filterdropmenu.library.DefaultMultipleChoiceListAdapter;
import com.yanxin.filterdropmenu.library.DefaultSingleChoiceListAdapter;
import com.yanxin.filterdropmenu.library.FilterDropMenu;
import com.yanxin.filterdropmenu.library.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    public static final String EXTRA_SELECT_TAG = "extra_select_tag";

    private FilterDropMenu mFilterDropMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFilterDropMenu = (FilterDropMenu) findViewById(R.id.filter_drop_menu);
        List<MenuItem> menuItems1 = new ArrayList<>();
        menuItems1.add(new MenuItem("全部", "", true));
        menuItems1.add(new MenuItem("中国", ""));
        menuItems1.add(new MenuItem("美国", ""));

        List<MenuItem> menuItems2 = new ArrayList<>();
        menuItems2.add(new MenuItem("全部", "", true));
        menuItems2.add(new MenuItem("北京", ""));
        menuItems2.add(new MenuItem("上海", ""));

        mFilterDropMenu.setAdapters(new DefaultSingleChoiceListAdapter(this, menuItems1, "国家", mFilterDropMenu, menuItems1.get(0))
                , new DefaultMultipleChoiceListAdapter(this, menuItems2, "城市", mFilterDropMenu, menuItems2.get(0))
                , new DefaultSingleChoiceListAdapter(this, null, "公司", mFilterDropMenu, new MenuItem("绿葡科技", "")));

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
            ((DefaultSingleChoiceListAdapter) mFilterDropMenu.getIAdapters()[2]).setSelect(item);
        }
    }
}
