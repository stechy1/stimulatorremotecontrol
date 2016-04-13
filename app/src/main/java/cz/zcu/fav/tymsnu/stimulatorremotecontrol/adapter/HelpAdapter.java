package cz.zcu.fav.tymsnu.stimulatorremotecontrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;


public class HelpAdapter extends BaseExpandableListAdapter {

    private final Context mContext;
    private final List<String> mParentItem;
    private final HashMap<String, List<String>> mChildItem;
    private final LayoutInflater mInflater;

    public HelpAdapter(Context context, List<String> parentItem, HashMap<String, List<String>> childItem) {
        mContext = context;
        mParentItem = parentItem;
        mChildItem = childItem;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return mParentItem.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<String> childrenList = mChildItem.get(mParentItem.get(groupPosition));

        return (childrenList == null) ? 0 : childrenList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mParentItem.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildItem.get(mParentItem.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentHolder holder;

        String listTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_help_parent, parent, false);

            holder = new ParentHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.help_parent);

            convertView.setTag(holder);

        } else {
            holder = (ParentHolder) convertView.getTag();
        }

        holder.textView.setText(listTitle);
        holder.textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, isExpanded ? R.drawable.arrow_up : R.drawable.arrow_down, 0);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildrenHolder holder;

        final String expandedListText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_help_children, parent, false);

            holder = new ChildrenHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.help_child);

            convertView.setTag(holder);

        } else {
            holder = (ChildrenHolder) convertView.getTag();
        }

        holder.textView.setText(expandedListText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private static final class ParentHolder {
        TextView textView;
    }

    private static final class ChildrenHolder {
        TextView textView;
    }
}
