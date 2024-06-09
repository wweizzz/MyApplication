package com.example.william.my.core.widget.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.william.my.core.widget.R;

import java.util.List;

public class SpinnerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<String> data;

    SpinnerAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerClickListener.onItemClick(holder.getBindingAdapterPosition());
            }
        });

        ((ViewHolder) holder).textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        private ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_spinner_textView);
        }
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    private SpinnerClickListener spinnerClickListener;

    public interface SpinnerClickListener {
        void onItemClick(int position);
    }

    void setOnItemClickListener(SpinnerClickListener spinnerClickListener) {
        this.spinnerClickListener = spinnerClickListener;
    }
}
