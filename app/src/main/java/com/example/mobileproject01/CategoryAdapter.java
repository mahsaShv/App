package com.example.mobileproject01;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private ArrayList<Category> mDataset;
    private static Context context;
    NotificationCenter notificationCenter;
    private static boolean isThemeDark = true;

    private ArrayList<Category> c = new ArrayList<>();


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.topic_title);
            title.setBackgroundColor(isThemeDark ? context.getResources().getColor(R.color.colorPrimaryDark, context.getTheme())
                    : context.getResources().getColor(R.color.colorPrimaryDarkLight, context.getTheme()));
        }
    }

    public CategoryAdapter(Context con, ArrayList<Category> myDataset,
                           Category category, NotificationCenter notificationCenter) {
        context = con;
        mDataset = myDataset;
        this.notificationCenter = notificationCenter;
        isThemeDark = Constant.getAppTheme() == 1;
        c.add(category);
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
        int imgsrc = this.context.getResources().getIdentifier("@drawable/" + category.getTitle().toLowerCase(),
                null, this.context.getPackageName());
        viewHolder.title.setImageResource(imgsrc);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
//                viewHolder.title.setImageTintMode(PorterDuff.Mode.DARKEN);

                c.get(0).setIsSelected(category.getIsSelected());
                c.get(0).setTitle(category.getTitle());
                c.get(0).setId(category.getId());

                notificationCenter.notifyy();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

