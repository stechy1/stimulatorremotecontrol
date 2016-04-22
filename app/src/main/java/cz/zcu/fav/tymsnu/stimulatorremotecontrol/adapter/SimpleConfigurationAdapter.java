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
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AConfiguration;

public class SimpleConfigurationAdapter<T extends AConfiguration<T>> extends ArrayAdapter<T> {

    private final Context context;
    private final List<T> objects;

    public SimpleConfigurationAdapter(Context context, List<T> objects) {
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
            schemeHolder.imageView1 = (ImageView) convertView.findViewById(R.id.control_list_view_image);
            schemeHolder.imageView2 = (ImageView) convertView.findViewById(R.id.control_list_view_image_changed);
            schemeHolder.text1 = (TextView) convertView.findViewById(R.id.control_list_view_text1);
            schemeHolder.text2 = (TextView) convertView.findViewById(R.id.control_list_view_text2);

            convertView.setTag(schemeHolder);
        } else {
            schemeHolder = (SchemeHolder) convertView.getTag();
        }

        T configuration = objects.get(position);
        schemeHolder.imageView1.setImageResource(configuration.selected ?
                R.drawable.checkbox_marked_outline : R.drawable.checkbox_blank_outline);
        schemeHolder.imageView2.setVisibility(configuration.changed ? View.VISIBLE : View.INVISIBLE);
        schemeHolder.text1.setText(configuration.getName());
        schemeHolder.text1.setTextColor(configuration.loaded ? Color.BLACK : Color.GRAY);
        schemeHolder.text2.setText("Count: " + (configuration.loaded ? configuration.getOutputCount() : "unknown"));

        return convertView;
    }

    private static class SchemeHolder {
        ImageView imageView1;
        ImageView imageView2;
        TextView text1;
        TextView text2;
    }
}
