package com.example.william.my.module.opensource.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.william.my.module.opensource.R;

import java.util.List;

public class SwipeRecyclerAdapter extends RecyclerSwipeAdapter<SwipeRecyclerAdapter.ViewHolder> {

    private List<String> mData;

    public SwipeRecyclerAdapter(List<String> mData) {
        this.mData = mData;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.open_item_swipe, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        if (viewHolder.itemView instanceof SwipeLayout) {
            mItemManger.bindView(viewHolder.itemView, i);
        }
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemManger.closeItem(i);
                mItemManger.closeAllItems();
            }
        });
        viewHolder.textView.setText(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.item_swipe_swipeLayout;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final Button button;
        private final TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.item_swipe_button);
            textView = itemView.findViewById(R.id.item_swipe_textView);
        }
    }

    public void setData(List<String> data) {
        this.mData = data;
    }
}
