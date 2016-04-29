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
    private FilterDropMenu mFilterDropMenu;

    private View mSelectView;

    public ItemListAdapter(Context context, List<MenuItem> menuItems, FilterDropMenu menu) {
        mContext = context;
        mMenuItems = menuItems;
        mFilterDropMenu = menu;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_drop_menu, parent, false);
        return new FilterDropMenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final FilterDropMenuItemViewHolder holder = (FilterDropMenuItemViewHolder) viewHolder;

        if (mMenuItems.get(position).equals(mFilterDropMenu.getTabText())) {
            holder.itemView.setSelected(true);
        } else {
            holder.itemView.setSelected(false);
        }

        if (mSelectView != null && mSelectView.getTag() != null) {
            if ((int) mSelectView.getTag() != position)
                holder.itemView.setSelected(false);
            else
                holder.itemView.setSelected(true);
        }

        holder.mTxtName.setText(mMenuItems.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterDropMenu.setTabText(mMenuItems.get(position));
                mFilterDropMenu.closeMenu();
                if (mSelectView != null)
                    mSelectView.setSelected(false);
                holder.itemView.setSelected(true);
                mSelectView = holder.itemView;
                mSelectView.setTag(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }

    class FilterDropMenuItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtName;

        FilterDropMenuItemViewHolder(View view) {
            super(view);
            mTxtName = (TextView) view.findViewById(R.id.txt_name);
        }

    }
}
