package rarolabs.com.br.rvp.models.drawer;

import android.content.Context;
import android.util.Log;

import rarolabs.com.br.rvp.R;

/**
 * Created by rodrigosol on 1/23/15.
 */
public class DrawerItem {


    private int label;
    private int icon;
    private int selectedIcon;
    private int size = 0;
    private boolean checked = false;

    public DrawerItem(int label, int icon, int selectedIcon) {
        this.label = label;
        this.icon = icon;
        this.selectedIcon = selectedIcon;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


    public long getSize(String target) {
        Log.d("DrawerItem", "getSizeChamado");
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Boolean isChecked(){
        return this.checked;
    }


    public int getSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(int selectedIcon) {
        this.selectedIcon = selectedIcon;
    }
}
