package com.example.mobileproject01;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private ArrayList<Category> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;

        public MyViewHolder(LinearLayout linearLayout) {
            super(linearLayout);
            this.linearLayout = linearLayout;
        }

    }

    public CategoryAdapter(ArrayList<Category> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout l = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_layout, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(l);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ImageView imageView = new ImageView(myViewHolder.linearLayout.getContext());
        imageView.setImageIcon(mDataset.get(i).getIcon());
        TextView t = new TextView(myViewHolder.linearLayout.getContext());
        t.setText(mDataset.get(i).getTitle());

        myViewHolder.linearLayout.addView(imageView);
        myViewHolder.linearLayout.addView(t);
//        myViewHolder.linearLayout.setMinimumHeight(20);
        myViewHolder.linearLayout.setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
