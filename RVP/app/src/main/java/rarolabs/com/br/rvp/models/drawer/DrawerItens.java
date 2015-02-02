package rarolabs.com.br.rvp.models.drawer;

import rarolabs.com.br.rvp.R;

/**
 * Created by rodrigosol on 2/2/15.
 */
public class DrawerItens {
    private static  final int labels[] = {R.string.drawer_redes,R.string.drawer_alertas,R.string.drawer_notificacoes,R.string.drawer_perfil};
    private static  final int icons[] = {R.drawable.ic_drawer_redes_normal,R.drawable.ic_drawer_alertas_normal,R.drawable.ic_drawer_notificacoes_normal,R.drawable.ic_drawer_perfil_normal};
    private static  final int selectedIcons[] = {R.drawable.ic_drawer_redes_selected,R.drawable.ic_drawer_alertas_selected,R.drawable.ic_drawer_notificacoes_selected,R.drawable.ic_drawer_perfil_selected};
    private static   DrawerItem[] itens = null;

    static{
        itens = new DrawerItem[labels.length];
        itens[0] = new DrawerItem(labels[0],icons[0],selectedIcons[0]);
        itens[1] = new DrawerItem(labels[1],icons[1],selectedIcons[1]);
        itens[2] = new NotificacaoDrawerItem(labels[2],icons[2],selectedIcons[2]);
        itens[3] = new DrawerItem(labels[3],icons[3],selectedIcons[3]);
    }

    public static void check(int position) {
        for(int i = 0; i < labels.length; i++){
            itens[i].setChecked(i==position);
        }
    }

    public static DrawerItem[] getDrawerItens() {
        return itens;
    }
}
