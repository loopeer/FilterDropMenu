package com.yanxin.filterdropmenu.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FilterDropMenu extends LinearLayout {

    private static final float DEFAULT_UNDER_LINE_HEIGHT = 0.5F;

    private LinearLayout mTabMenuLayout;
    private FrameLayout mContentLayout;
    private FrameLayout mPopupMenuLayout;

    private int mCurrentOpenPositon = -1;

    private int mDividerColor = 0xffcccccc;
    private int mTextSelectedColor = 0xff890c85;
    private int mTextUnselectedColor = 0xff111111;
    private int mMaskColor = 0x88888888;
    private int mMenuBackgroundColor = Color.WHITE;
    private int mUnderlineColor = 0xffcccccc;

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

        addTabMenuLayout();
        addUnderLine();
        addContentLayout();
        addPopupMenuLayout();
    }

    private void addPopupMenuLayout() {
        mPopupMenuLayout = new FrameLayout(getContext());
        mPopupMenuLayout.setVisibility(GONE);
        mContentLayout.addView(mPopupMenuLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        mContentLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        mContentLayout.setVisibility(GONE);
        addView(mContentLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void setAdapters(IAdapter... adapters) {
        mIAdapters = adapters;
        mTabMenuLayout.removeAllViews();
        int position = 0;
        for (IAdapter adapter : adapters) {
            if (adapter instanceof IListAdapter)
                processListAdapter((IListAdapter) adapter, position);
            position++;
        }
    }

    private void processListAdapter(IListAdapter adapter, int position) {
        addTab(adapter.getDefaultMenuTitle(), position);
        mPopupMenuLayout.addView(adapter.getMenuContentView());
    }

    public interface OnMenuClickListener {
        void onClick(int position, String menu);
    }

    private OnMenuClickListener mOnMenuClickListener;

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        mOnMenuClickListener = onMenuClickListener;
    }

    private void addTab(final String tabTitle, final int position) {
        if (position != 0) {
            View view = new View(getContext());
            LinearLayout.LayoutParams params = new LayoutParams(dpTpPx(0.5f), ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 12, 0, 12);
            view.setLayoutParams(params);
            view.setBackgroundColor(mDividerColor);
            mTabMenuLayout.addView(view);
        }

        final FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0F));
        frameLayout.addView(getMenuItemTextView(tabTitle));
        frameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuClickListener != null)
                    mOnMenuClickListener.onClick(position, tabTitle);
                switchMenu(frameLayout.getChildAt(0));
            }
        });

        mTabMenuLayout.addView(frameLayout);
    }

    private TextView getMenuItemTextView(String tabTitle) {
        TextView tab = new TextView(getContext());
        tab.setSingleLine();
        tab.setEllipsize(TextUtils.TruncateAt.END);
        tab.setGravity(Gravity.CENTER);
        tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mMenuTextSize);
        tab.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        tab.setTextColor(mTextUnselectedColor);
        tab.setCompoundDrawablesWithIntrinsicBounds(0, 0, mMenuUnselectedIcon, 0);
        tab.setCompoundDrawablePadding(12);
        tab.setPadding(12, 12, 12, 12);
        tab.setText(tabTitle);
        return tab;
    }

    public void setSelectTab(MenuItem item) {
        if (mCurrentOpenPositon != -1) {
            ((TextView) ((ViewGroup) mTabMenuLayout.getChildAt(mCurrentOpenPositon))
                    .getChildAt(0)).setText(item.isDefault ? getAdapter().getDefaultMenuTitle() : item.name);
        }
    }

    private IAdapter getAdapter() {
        return mIAdapters[mCurrentOpenPositon / 2];
    }

    public void setTabClickable(boolean clickable) {
        for (int i = 0; i < mTabMenuLayout.getChildCount(); i = i + 2) {
            mTabMenuLayout.getChildAt(i).setClickable(clickable);
        }
    }

    public void closeMenu() {
        if (mCurrentOpenPositon == -1)
            return;
        TextView openMenuItemTextView = ((TextView) ((ViewGroup) mTabMenuLayout.getChildAt(mCurrentOpenPositon)).getChildAt(0));
        openMenuItemTextView.setTextColor(mTextUnselectedColor);
        openMenuItemTextView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                ContextCompat.getDrawable(getContext(), mMenuUnselectedIcon), null);
        mPopupMenuLayout.setVisibility(View.GONE);
        mPopupMenuLayout.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
        mContentLayout.setVisibility(GONE);
        mContentLayout.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_out));
        mCurrentOpenPositon = -1;
    }

    public boolean isShowing() {
        return mCurrentOpenPositon != -1;
    }

    private void switchMenu(View target) {
        for (int i = 0; i < mTabMenuLayout.getChildCount(); i = i + 2) {
            TextView clickTextView = (TextView) ((ViewGroup) mTabMenuLayout.getChildAt(i)).getChildAt(0);
            if (target == clickTextView) {
                if (mCurrentOpenPositon == i) {
                    closeMenu();
                } else {
                    showMenu(i, clickTextView);
                }
            } else {
                clickTextView.setTextColor(mTextUnselectedColor);
                clickTextView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        ContextCompat.getDrawable(getContext(), mMenuUnselectedIcon), null);
                mPopupMenuLayout.getChildAt(i / 2).setVisibility(View.GONE);
            }
        }
    }

    private void showMenu(int childIndex, TextView clickTextView) {
        if (mCurrentOpenPositon == -1) {
            mPopupMenuLayout.setVisibility(View.VISIBLE);
            mPopupMenuLayout.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
            mContentLayout.setVisibility(VISIBLE);
            mContentLayout.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_in));
            mPopupMenuLayout.getChildAt(childIndex / 2).setVisibility(View.VISIBLE);
        } else {
            mPopupMenuLayout.getChildAt(childIndex / 2).setVisibility(View.VISIBLE);
        }
        mCurrentOpenPositon = childIndex;
        clickTextView.setTextColor(mTextSelectedColor);
        clickTextView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                ContextCompat.getDrawable(getContext(), mMenuSelectedIcon), null);
        getAdapter().notifyDataSetChanged();
    }

    public int dpTpPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }

}
