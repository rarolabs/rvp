package rarolabs.com.br.rvp.models.drawer;

import android.util.Log;

import rarolabs.com.br.rvp.models.Notificacao;

/**
 * Created by rodrigosol on 2/2/15.
 */
public class NotificacaoDrawerItem extends DrawerItem {

    public NotificacaoDrawerItem(int label, int icon, int selectedIcon) {
        super(label, icon, selectedIcon);
    }
    @Override
    public long getSize(){
        Log.d("NotificacaoDrawerItem", "getSizeChamado");
        return Notificacao.totalNotificacoesNaoLidas();
    }
}
