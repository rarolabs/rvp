package rarolabs.com.br.rvp.models;

import rarolabs.com.br.rvp.R;

/**
 * Created by rodrigosol on 1/23/15.
 */
public class DrawerItem {
    private static  final int labels[] = {R.string.drawer_redes,R.string.drawer_alertas,R.string.drawer_notificacoes,R.string.drawer_perfil};
    private static  final int icons[] = {R.drawable.ic_drawer_redes_normal,R.drawable.ic_drawer_alertas_normal,R.drawable.ic_drawer_notificacoes_normal,R.drawable.ic_drawer_perfil_normal};
    private static  final int selectedIcons[] = {R.drawable.ic_drawer_redes_selected,R.drawable.ic_drawer_alertas_selected,R.drawable.ic_drawer_notificacoes_selected,R.drawable.ic_drawer_perfil_selected};
    private static   DrawerItem[] itens = null;

    static{
        itens = new DrawerItem[labels.length];
        for(int i =0; i < labels.length; i++){
            itens[i] = new DrawerItem(labels[i],icons[i],selectedIcons[i]);
        }

    }

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

    public static DrawerItem[] getDrawerItens(){
        return itens;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static void check(int position) {
        for(int i = 0; i < labels.length; i++){
            itens[i].setChecked(i==position);
        }
    }

    private void setChecked(boolean checked) {
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
