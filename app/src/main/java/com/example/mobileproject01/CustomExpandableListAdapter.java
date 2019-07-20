
package com.example.mobileproject01;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private MessageController messageController;

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        messageController = MessageController.getInstance(context);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        Switch expandedListTextView = (Switch) convertView
                .findViewById(R.id.expandedListItem);
        if (messageController.storageManager.getTheme() == 1)
            expandedListTextView.setTextColor(context.getResources().getColor(R.color.colorPrimary, context.getTheme()));
        else
            expandedListTextView.setTextColor(context.getResources().getColor(R.color.colorPrimaryLight, context.getTheme()));


        String corrected = expandedListText;
        expandedListTextView.setChecked((corrected.charAt(corrected.length() - 1) == '1') ? true : false);
        corrected = expandedListText.replace("_", " ");
        corrected = corrected.substring(0, corrected.length() - 1);
        expandedListTextView.setText(corrected);
        expandedListTextView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String name = expandedListText.substring(0, expandedListText.length() - 1);
                messageController.storageManager.changeWebsiteStatus(name);
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        if (this.expandableListDetail.isEmpty())
            return 0;
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        ImageView icon;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }

        ImageView imageView = convertView.findViewById(R.id.title_icon);
        int imgsrc = this.context.getResources().getIdentifier("@drawable/" + listTitle.toLowerCase() + "icon", null, this.context.getPackageName());
        imageView.setImageResource(imgsrc);
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        if (messageController.storageManager.getTheme() == 1) {
            imageView.setColorFilter(context.getResources().getColor(R.color.colorAccent, context.getTheme()), PorterDuff.Mode.SRC_ATOP);
            listTitleTextView.setTextColor(context.getResources().getColor(R.color.colorAccent, context.getTheme()));
        } else {
            imageView.setColorFilter(context.getResources().getColor(R.color.colorAccentLight, context.getTheme()), PorterDuff.Mode.SRC_ATOP);
            listTitleTextView.setTextColor(context.getResources().getColor(R.color.colorAccentLight, context.getTheme()));
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}

