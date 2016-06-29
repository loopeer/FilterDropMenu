package com.yanxin.filterdropmenu.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanxin.filterdropmenu.library.interfaces.IAdapter;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<MenuItem> mMenuItems;

    private IAdapter mIAdapter;

    private ChoiceType mChoiceType;

    private onMenuItemClickListener mOnMenuItemClickListener;
    private onMenuMultiItemSureClickListener mOnMenuMultiItemSureClickListener;

    public enum ChoiceType {
        SINGLE_CHOICE,
        MULTIPLE_CHOICE
    }

    public void setOnMenuItemClickListener(onMenuItemClickListener onMenuItemClickListener) {
        mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOnMenuMultiItemSureClickListener(onMenuMultiItemSureClickListener onMenuMultiItemSureClickListener) {
        mOnMenuMultiItemSureClickListener = onMenuMultiItemSureClickListener;
    }

    public ItemListAdapter(Context context, List<MenuItem> menuItems, IAdapter iAdapter, ChoiceType choiceType) {
        mMenuItems = new ArrayList<>();
        mContext = context;
        mIAdapter = iAdapter;
        mChoiceType = choiceType;

        setData(checkItems(menuItems));
    }

    private List<MenuItem> checkItems(List<MenuItem> menuItems) {
        if (menuItems == null) return new ArrayList<>();
        return menuItems;
    }

    public List<MenuItem> getDatas() {
        return mMenuItems;
    }

    public void setData(List<MenuItem> menuItems) {
        List<MenuItem> menuItemList = checkItems(menuItems);
        if (mChoiceType == ChoiceType.MULTIPLE_CHOICE && !menuItemList.isEmpty())
            menuItemList.add(new MenuItem());
        mMenuItems.clear();
        mMenuItems.addAll(menuItemList);
    }

    public void updateData(List<MenuItem> menuItems) {
        setData(menuItems);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == R.layout.list_item_drop_menu_multi_button) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_drop_menu_multi_button, parent, false);
            return new FilterDropMenuMutilButtonItemViewHolder(view);
        } else if (viewType == R.layout.list_item_drop_menu_multi) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_drop_menu_multi, parent, false);
            return new FilterDropMenuMultiItemViewHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_drop_menu, parent, false);
            return new FilterDropMenuItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (getItemViewType(position) == R.layout.list_item_drop_menu_multi_button) {
            FilterDropMenuMutilButtonItemViewHolder holder = (FilterDropMenuMutilButtonItemViewHolder) viewHolder;
            holder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnMenuMultiItemSureClickListener.onMenuSureClick();
                }
            });
        } else if (getItemViewType(position) == R.layout.list_item_drop_menu_multi) {
            FilterDropMenuMultiItemViewHolder holder = (FilterDropMenuMultiItemViewHolder) viewHolder;
            DefaultMultipleChoiceListAdapter adapter = (DefaultMultipleChoiceListAdapter) mIAdapter;
            if (adapter.getSelectMenuItems().contains(mMenuItems.get(position)) && !mMenuItems.get(position).isDefault) {
                holder.itemView.setSelected(true);
                holder.mImgIndicator.setVisibility(View.VISIBLE);
            } else {
                holder.itemView.setSelected(false);
                holder.mImgIndicator.setVisibility(View.GONE);
            }
            holder.mTxtName.setText(mMenuItems.get(position).name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnMenuItemClickListener != null)
                        mOnMenuItemClickListener.onMenuItemClick(mMenuItems.get(position), position);
                }
            });
        } else {
            FilterDropMenuItemViewHolder holder = (FilterDropMenuItemViewHolder) viewHolder;
            DefaultSingleChoiceListAdapter adapter = (DefaultSingleChoiceListAdapter) mIAdapter;
            if (adapter.getSelectMenuItem() != null
                    && adapter.getSelectMenuItem().name.equals(mMenuItems.get(position).name)
                    && !adapter.getSelectMenuItem().isDefault) {
                holder.itemView.setSelected(true);
            } else {
                holder.itemView.setSelected(false);
            }
            holder.mTxtName.setText(mMenuItems.get(position).name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnMenuItemClickListener != null)
                        mOnMenuItemClickListener.onMenuItemClick(mMenuItems.get(position), position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mChoiceType == ChoiceType.MULTIPLE_CHOICE && position == getItemCount() - 1) {
            return R.layout.list_item_drop_menu_multi_button;
        } else if (mChoiceType == ChoiceType.MULTIPLE_CHOICE) {
            return R.layout.list_item_drop_menu_multi;
        } else {
            return R.layout.list_item_drop_menu;
        }
    }

    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }

    public interface onMenuItemClickListener {
        void onMenuItemClick(MenuItem item, int position);
    }

    public interface onMenuMultiItemSureClickListener {
        void onMenuSureClick();
    }

    class FilterDropMenuItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtName;

        FilterDropMenuItemViewHolder(View view) {
            super(view);
            mTxtName = (TextView) view.findViewById(R.id.txt_name);
        }

    }

    class FilterDropMenuMutilButtonItemViewHolder extends RecyclerView.ViewHolder {

        Button mButton;

        FilterDropMenuMutilButtonItemViewHolder(View view) {
            super(view);
            mButton = (Button) view.findViewById(R.id.btn_sure);
        }
    }

    class FilterDropMenuMultiItemViewHolder extends FilterDropMenuItemViewHolder {

        TextView mTxtName;
        ImageView mImgIndicator;

        FilterDropMenuMultiItemViewHolder(View view) {
            super(view);
            mTxtName = (TextView) view.findViewById(R.id.txt_name);
            mImgIndicator = (ImageView) view.findViewById(R.id.img_indicator);
        }

    }
}
