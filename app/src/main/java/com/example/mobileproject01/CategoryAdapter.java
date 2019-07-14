package com.example.mobileproject01;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private ArrayList<Category> mDataset;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.topic_title);
        }
    }

    public CategoryAdapter(Context context, ArrayList<Category> myDataset) {
        this.context = context;
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.topics, viewGroup, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        Category category = mDataset.get(i);
        viewHolder.title.setText(category.getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

