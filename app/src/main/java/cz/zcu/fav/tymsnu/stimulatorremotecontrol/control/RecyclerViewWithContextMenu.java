package cz.zcu.fav.tymsnu.stimulatorremotecontrol.control;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;

/**
 * Vylepšené standartní RecyclerView o možnost kontextového menu
 */
public class RecyclerViewWithContextMenu extends RecyclerView {

    private ContextMenuInfo mContextMenuInfo = null;

    public RecyclerViewWithContextMenu(Context context) {
        super(context);
    }

    public RecyclerViewWithContextMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewWithContextMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected ContextMenuInfo getContextMenuInfo() {
        return mContextMenuInfo;
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        final int longPressPosition = getChildPosition(originalView);
        if (longPressPosition >= 0) {
            final long longPressId = getAdapter().getItemId(longPressPosition);
            mContextMenuInfo = createContextMenuInfo(longPressPosition, longPressId);
            return super.showContextMenuForChild(originalView);
        }
        return false;
    }

    ContextMenuInfo createContextMenuInfo(int position, long id) {
        return new RecyclerContextMenuInfo(position, id);
    }

    /**
     * Extra menu information provided to the
     * {@link android.view.View.OnCreateContextMenuListener#onCreateContextMenu(android.view.ContextMenu, View, ContextMenuInfo) }
     * callback when a context menu is brought up for this AdapterView.
     */
    public static class RecyclerContextMenuInfo implements ContextMenu.ContextMenuInfo {

        // Pozice v adapteru, pro kterou je zobrazeno kontextove menu
        public int position;

        // ID řádku položky, pro které je kontextové menu zobrazeno
        public long id;

        public RecyclerContextMenuInfo(int position, long id) {
            this.position = position;
            this.id = id;
        }
    }

}