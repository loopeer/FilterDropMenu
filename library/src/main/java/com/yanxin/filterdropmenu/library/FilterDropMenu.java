package com.yanxin.filterdropmenu.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class FilterDropMenu extends LinearLayout {

    private static final float DEFAULT_UNDER_LINE_HEIGHT = 0.5F;

    private LinearLayout mTabMenuLayout;
    private FrameLayout mContentLayout;
    private FrameLayout mPopupMenuLayout;

    private int mCurrentOpenPositon = -1;

    private int mDividerColor = 0XFFCCCCCC;
    private int mTextSelectedColor = 0XFF890C85;
    private int mTextUnselectedColor = 0XFF111111;
    private int mMaskColor = 0X88888888;
    private int mMenuBackgroundColor = Color.WHITE;
    private int mUnderlineColor = 0XFFCCCCCC;
    private int mMenuTextSize = 14;
    private int mDropMenuHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int mMenuSelectedIcon;
    private int mMenuUnselectedIcon;

    private IAdapter[] mIAdapters;

    public FilterDropMenu(Context context) {
        super(context, null);
    }

    public FilterDropMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterDropMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FilterDropMenu);
        mUnderlineColor = a.getColor(R.styleable.FilterDropMenu_underline_color, mUnderlineColor);
        mDividerColor = a.getColor(R.styleable.FilterDropMenu_divider_color, mDividerColor);
        mTextSelectedColor = a.getColor(R.styleable.FilterDropMenu_text_selected_color, mTextSelectedColor);
        mTextUnselectedColor = a.getColor(R.styleable.FilterDropMenu_text_unselected_color, mTextUnselectedColor);
        mMenuBackgroundColor = a.getColor(R.styleable.FilterDropMenu_menu_background_color, mMenuBackgroundColor);
        mMaskColor = a.getColor(R.styleable.FilterDropMenu_mask_color, mMaskColor);
        mMenuTextSize = a.getDimensionPixelSize(R.styleable.FilterDropMenu_menu_text_size, mMenuTextSize);
        mMenuSelectedIcon = a.getResourceId(R.styleable.FilterDropMenu_menu_selected_icon, mMenuSelectedIcon);
        mMenuUnselectedIcon = a.getResourceId(R.styleable.FilterDropMenu_menu_unselected_icon, mMenuUnselectedIcon);
        mDropMenuHeight = a.getDimensionPixelSize(R.styleable.FilterDropMenu_menu_height, mDropMenuHeight);
        a.recycle();

        setupLayout();
    }

    private void setupLayout() {
        addTabMenuLayout();
        addUnderLine();
        addContentLayout();
        addPopupMenuLayout();
    }

    private void addTabMenuLayout() {
        mTabMenuLayout = new LinearLayout(getContext());
        mTabMenuLayout.setOrientation(HORIZONTAL);
        mTabMenuLayout.setBackgroundColor(mMenuBackgroundColor);
        mTabMenuLayout.setGravity(Gravity.CENTER_VERTICAL);
        addView(mTabMenuLayout, ViewGroup.LayoutParams.MATCH_PARENT, mDropMenuHeight);
    }

    private void addUnderLine() {
        View underLine = new View(getContext());
        underLine.setBackgroundColor(mUnderlineColor);
        addView(underLine, ViewGroup.LayoutParams.MATCH_PARENT, dpTpPx(DEFAULT_UNDER_LINE_HEIGHT));
    }

    private void addContentLayout() {
        mContentLayout = new FrameLayout(getContext());
        mContentLayout.setBackgroundColor(mMaskColor);
        mContentLayout.setVisibility(GONE);
        mContentLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        addView(mContentLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void addPopupMenuLayout() {
        mPopupMenuLayout = new FrameLayout(getContext());
        mPopupMenuLayout.setVisibility(GONE);
        mContentLayout.addView(mPopupMenuLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void setAdapters(IAdapter... adapters) {
        mIAdapters = adapters;
        mTabMenuLayout.removeAllViews();
        mPopupMenuLayout.removeAllViews();
        int position = 0;
        for (IAdapter adapter : adapters) {
            if (adapter instanceof IListAdapter)
                processListAdapter((IListAdapter) adapter, position);
            position++;
        }
    }

    private void processListAdapter(IListAdapter adapter, int position) {
        addTab(adapter, position);
        if (adapter.isHasMenuContentView())
            mPopupMenuLayout.addView(adapter.getMenuContentView());
    }

    public interface OnMenuClickListener {
        void onClick(int position);
    }

    private OnMenuClickListener mOnMenuClickListener;

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        mOnMenuClickListener = onMenuClickListener;
    }

    public int getMenuTextSize() {
        return mMenuTextSize;
    }

    public int getTextSelectedColor() {
        return mTextSelectedColor;
    }

    public int getTextUnselectedColor() {
        return mTextUnselectedColor;
    }

    public int getMenuSelectedIcon() {
        return mMenuSelectedIcon;
    }

    public int getMenuUnselectedIcon() {
        return mMenuUnselectedIcon;
    }

    private void addTab(IListAdapter adapter, final int position) {
        if (position != 0) {
            addVerticalDivider();
        }

        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0F));
        frameLayout.addView(adapter.getMenuTitleView());
        frameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMenu(position);
            }
        });

        mTabMenuLayout.addView(frameLayout);
    }

    private void addVerticalDivider() {
        View view = new View(getContext());
        LayoutParams params = new LayoutParams(dpTpPx(0.5f), ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 12, 0, 12);
        view.setLayoutParams(params);
        view.setBackgroundColor(mDividerColor);
        mTabMenuLayout.addView(view);
    }

    public void closeMenu() {
        if (mCurrentOpenPositon == -1 || !mIAdapters[mCurrentOpenPositon].isHasMenuContentView())
            return;
        mIAdapters[mCurrentOpenPositon].setUnSelect();
        mPopupMenuLayout.setVisibility(View.GONE);
        mPopupMenuLayout.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
        mContentLayout.setVisibility(GONE);
        mContentLayout.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_out));
        mCurrentOpenPositon = -1;
    }

    private void switchMenu(int position) {
        if (mOnMenuClickListener != null)
            mOnMenuClickListener.onClick(position);

        if (!mIAdapters[position].isHasMenuContentView()) {
            if (mCurrentOpenPositon != -1 && mIAdapters[mCurrentOpenPositon].isHasMenuContentView())
                closeMenu();
            mCurrentOpenPositon = position;
            return;
        }

        if (mCurrentOpenPositon == position) {
            closeMenu();
            return;
        }

        showMenu(position);
    }

    private void showMenu(int position) {
        if (mCurrentOpenPositon == -1 || !mIAdapters[mCurrentOpenPositon].isHasMenuContentView()) {
            mPopupMenuLayout.setVisibility(VISIBLE);
            mPopupMenuLayout.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
            mContentLayout.setVisibility(VISIBLE);
            mContentLayout.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_in));
        } else {
            mIAdapters[mCurrentOpenPositon].setUnSelect();
        }
        mIAdapters[position].setSelect();
        showMenuContentView(mIAdapters[position].getMenuContentView());
        mCurrentOpenPositon = position;
    }

    public int dpTpPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }

    private void showMenuContentView(View view) {
        for (int i = 0; i < mPopupMenuLayout.getChildCount(); i++) {
            if (view == mPopupMenuLayout.getChildAt(i)) {
                view.setVisibility(VISIBLE);
            } else {
                mPopupMenuLayout.getChildAt(i).setVisibility(GONE);
            }
        }
    }

    public IAdapter[] getIAdapters() {
        return mIAdapters;
    }
}
