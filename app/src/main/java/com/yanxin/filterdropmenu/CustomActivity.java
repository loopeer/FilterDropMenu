package com.yanxin.filterdropmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yanxin.filterdropmenu.library.MenuItem;

/**
 * Created by YanXin on 2016/5/3.
 */
public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
    }

    public void returnMain(View view) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_SELECT_TAG, new MenuItem("测试333", ""));
        setResult(RESULT_OK, intent);
        finish();
    }

}
