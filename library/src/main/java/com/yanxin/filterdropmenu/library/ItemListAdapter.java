package com.yanxin.filterdropmenu.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<MenuItem> mMenuItems;

    private IAdapter mIAdapter;

    private onMenuItemClickListener mOnMenuItemClickListener;

    public void setOnMenuItemClickListener(onMenuItemClickListener onMenuItemClickListener) {
        mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public ItemListAdapter(Context context, List<MenuItem> menuItems, IAdapter iAdapter) {
        mContext = context;
        mMenuItems = menuItems;
        mIAdapter = iAdapter;
    }

    public void updateData(List<MenuItem> menuItems) {
        mMenuItems.clear();
        mMenuItems.addAll(menuItems);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_drop_menu, parent, false);
        return new FilterDropMenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        FilterDropMenuItemViewHolder holder = (FilterDropMenuItemViewHolder) viewHolder;

        if (mIAdapter.getSelectMenuItem() != null
                && mIAdapter.getSelectMenuItem().name.equals(mMenuItems.get(position).name)
                && !mIAdapter.getSelectMenuItem().isDefault) {
            holder.itemView.setSelected(true);
        } else {
            holder.itemView.setSelected(false);
        }

        holder.mTxtName.setText(mMenuItems.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuItemClickListener != null)
                    mOnMenuItemClickListener.onMenuItemClick(mMenuItems.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }

    public interface onMenuItemClickListener {
        void onMenuItemClick(MenuItem item);
    }

    class FilterDropMenuItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtName;

        FilterDropMenuItemViewHolder(View view) {
            super(view);
            mTxtName = (TextView) view.findViewById(R.id.txt_name);
        }

    }
}
