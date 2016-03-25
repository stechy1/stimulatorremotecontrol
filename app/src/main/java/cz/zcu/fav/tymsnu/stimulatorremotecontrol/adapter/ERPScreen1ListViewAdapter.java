package cz.zcu.fav.tymsnu.stimulatorremotecontrol.adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;

public class ERPScreen1ListViewAdapter extends ArrayAdapter<Scheme> {

    private final Context context;
    private final List<Scheme> objects;

    public ERPScreen1ListViewAdapter(Context context, List<Scheme> objects) {
        super(context, R.layout.control_list_view_item, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SchemeHolder schemeHolder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.control_list_view_item, parent, false);

            schemeHolder = new SchemeHolder();
            schemeHolder.imageView = (ImageView) convertView.findViewById(R.id.control_scheme_view_image);
            schemeHolder.text1 = (TextView) convertView.findViewById(R.id.control_scheme_view_text1);
            schemeHolder.text2 = (TextView) convertView.findViewById(R.id.control_scheme_view_text2);

            convertView.setTag(schemeHolder);
        } else {
            schemeHolder = (SchemeHolder) convertView.getTag();
        }

        Scheme scheme = objects.get(position);
        schemeHolder.imageView.setImageResource(scheme.selected ?
                R.drawable.checkbox_marked_outline : R.drawable.checkbox_blank_outline);
        schemeHolder.text1.setText(scheme.getName());
        schemeHolder.text1.setTextColor(scheme.loaded ? Color.BLACK : Color.GRAY);
        schemeHolder.text2.setText("Count: " + scheme.getOutputCount());

        return convertView;
    }

    private static class SchemeHolder {
        ImageView imageView;
        TextView text1;
        TextView text2;
    }
}
