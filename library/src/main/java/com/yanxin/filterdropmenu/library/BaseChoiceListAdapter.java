package com.yanxin.filterdropmenu.library;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yanxin.filterdropmenu.library.interfaces.IListAdapter;

import java.util.List;

public abstract class BaseChoiceListAdapter extends BaseMenuAdapter implements IListAdapter, ItemListAdapter.onMenuItemClickListener {

    protected ItemListAdapter mItemListAdapter;

    public BaseChoiceListAdapter(Context context, List<MenuItem> items, String defaultMenuTitle, FilterDropMenu filterDropMenu, ItemListAdapter.ChoiceType choiceType) {
        super(context, defaultMenuTitle, filterDropMenu);
        mMenuTitleView = createMenuTitleView();
        mItemListAdapter = new ItemListAdapter(mContext, items, this, choiceType);
        mItemListAdapter.setOnMenuItemClickListener(this);
        mMenuContentView = createMenuContentView();
    }

    @Override
    public View getMenuContentView() {
        return mMenuContentView;
    }

    @Override
    public View getMenuTitleView() {
        return mMenuTitleView;
    }

    public interface OnMenuSelectListener {
        void onMenuSelect(int position, MenuItem item);
    }

    public interface OnMenuMultiSelectListener {
        void onMenuSelect(int position, List<MenuItem> items);
    }

    @Override
    public void notifyDataSetChanged() {
        mItemListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isEmpty() {
        return mItemListAdapter.getItemCount() <= 0;
    }

    @Override
    public View createMenuContentView() {
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(mContext).inflate(R.layout.view_recycler_view, null);
        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    @Override
    public TextView createMenuTitleView() {
        TextView tab = new TextView(mContext);
        tab.setSingleLine();
        tab.setEllipsize(TextUtils.TruncateAt.END);
        tab.setGravity(Gravity.CENTER);
        tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mFilterDropMenu.getMenuTextSize());
        tab.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        tab.setTextColor(mFilterDropMenu.getTextUnselectedColor());
        tab.setCompoundDrawablesWithIntrinsicBounds(0, 0, mFilterDropMenu.getMenuUnselectedIcon(), 0);
        tab.setCompoundDrawablePadding(mFilterDropMenu.dpTpPx(8));
        tab.setPadding(12, 12, 12, 12);
        tab.setText(getDefaultMenuTitle());
        return tab;
    }

    @Override
    public void setUnSelect() {
        mMenuTitleView.setTextColor(mFilterDropMenu.getTextUnselectedColor());
        mMenuTitleView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                ContextCompat.getDrawable(mContext, mFilterDropMenu.getMenuUnselectedIcon()), null);
    }

    @Override
    public void setSelect() {
        mMenuTitleView.setTextColor(mFilterDropMenu.getTextSelectedColor());
        mMenuTitleView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                ContextCompat.getDrawable(mContext, mFilterDropMenu.getMenuSelectedIcon()), null);
        notifyDataSetChanged();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mItemListAdapter);
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return mItemListAdapter.getDatas();
    }

    @Override
    public RecyclerView.Adapter getListAdapter() {
        return mItemListAdapter;
    }

}
