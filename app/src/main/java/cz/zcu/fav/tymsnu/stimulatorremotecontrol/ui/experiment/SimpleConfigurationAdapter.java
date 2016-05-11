package cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AConfiguration;

/**
 * Adapter pro recycler view konfigurace
 * @param <T> Potomek třídy AConfiguration
 */
public class SimpleConfigurationAdapter<T extends AConfiguration<T>>
        extends RecyclerView.Adapter<SimpleConfigurationAdapter.ConfigurationHolder> {

    private static ItemClickListener mListener;

    private final Context context;
    private final List<T> items;

    public SimpleConfigurationAdapter(Context context, List<T> items) {
        super();
        this.context = context;
        this.items = items;
    }

    @Override
    public ConfigurationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.control_list_view_item, parent, false);

        return new ConfigurationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConfigurationHolder holder, int position) {
        T configuration = items.get(position);
        holder.imageView1.setImageResource(getRightIcon(configuration));
        holder.imageView2.setVisibility(configuration.changed ? View.VISIBLE : View.INVISIBLE);
        holder.text1.setText(configuration.getName());
        holder.text1.setTextColor(configuration.loaded ? Color.BLACK : Color.GRAY);
        holder.text2.setText(getDescription(configuration));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItemClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    /**
     * Vrátí správnou ikonu podle stavu konfigurace
     * @param configuration Konfigurace
     * @return Ikonu stavu konfigurace
     */
    private int getRightIcon(AConfiguration<T> configuration) {
        if (configuration.selected)
            return R.drawable.checkbox_marked_outline;

        if (configuration.corrupted)
            return R.drawable.corrupted_file;

        return R.drawable.checkbox_blank_outline;
    }

    /**
     * Vrátí správný popis konfigurace (podnadpis v itemu konfigurace)
     * @param configuration Konfigurace
     * @return Podnadpis konfigurace
     */
    private String getDescription(AConfiguration<T> configuration) {
        if (configuration.corrupted)
            return context.getString(R.string.corrupted_configuration);

        if (configuration.loaded)
            return context.getString(R.string.output_count, configuration.getOutputCount());

        return context.getString(R.string.output_count, context.getString(R.string.output_count_unknown));
    }

    public static class ConfigurationHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnCreateContextMenuListener {

        ImageView imageView1, imageView2;
        TextView text1, text2;

        public ConfigurationHolder(View itemView) {
            super(itemView);

            imageView1 = (ImageView) itemView.findViewById(R.id.control_list_view_image);
            imageView2 = (ImageView) itemView.findViewById(R.id.control_list_view_image_changed);
            text1 = (TextView) itemView.findViewById(R.id.control_list_view_text1);
            text2 = (TextView) itemView.findViewById(R.id.control_list_view_text2);

            itemView.setOnClickListener(this);
            // Musím nastavit listener, který není potřeba implementovat
            // Bez tohoto nastavení se nezobrazí kontextové menu
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener == null)
                return;

            mListener.onItemClick(itemView, getAdapterPosition());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            // Musím implementovat tuto metodu, ale není potřeba ji vyplňovat.
        }
    }
    public interface ItemClickListener {
        void onItemClick(View v, int position);
    }
}
