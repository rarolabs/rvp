package br.com.rarolabs.rvp.api.service;

import com.google.android.gcm.server.Sender;
import com.googlecode.objectify.Objectify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.rarolabs.rvp.api.auth.Constants;
import br.com.rarolabs.rvp.api.models.Alerta;
import br.com.rarolabs.rvp.api.models.Dispositivo;
import br.com.rarolabs.rvp.api.models.Membro;

/**
 * Created by rodrigosol on 2/2/15.
 */
public class NotificacaoService {
    private static final Sender sender = new Sender(Constants.GCM_API_KEY);
    private static final String[] STATUS_NOVO_MEMBRO = new String[1];
    private static final String[] STATUS_REJEITAR = new String[1];
    private static final String[] STATUS_NOVO_ADMINISTRADOR = new String[1];
    private static final String[] STATUS_NOVA_AUTORIDADE = new String[1];
    private static final String[] STATUS_RETIRAR_ADMINISTRADOR = new String[1];
    private static final String[] STATUS_DEIXOU_REDE = new String[1];
    private static final String[] STATUS_RETIRAR_AUTORIDADE = new String[1];

    static {
        STATUS_NOVO_MEMBRO[0] = "NOVO_MEMBRO";
        STATUS_REJEITAR[0] = "REJEITAR";
        STATUS_NOVO_ADMINISTRADOR[0] = "NOVO_ADMINISTRADOR";
        STATUS_NOVA_AUTORIDADE[0] = "NOVA_AUTORIDADE";
        STATUS_RETIRAR_ADMINISTRADOR[0] = "RETIRAR_ADMINISTRADOR";
        STATUS_RETIRAR_AUTORIDADE[0] = "RETIRAR_AUTORIDADE";
        STATUS_DEIXOU_REDE[0] = "DEIXOU_REDE";
    }


    public static void notificarSolicacaoAssociacao(Membro m) {
        enviarNotificacao(m,m.getRede().membrosAdministradores(), new Notificacoes().new SolicitacaoTemplate(), 20);
    }


    public static void notificarNovoVizinho(Membro m) {
        enviarNotificacao(m,m.getRede().membrosAtivos(), new Notificacoes().new StatusTemplate(STATUS_NOVO_MEMBRO), 5);
    }

    public static void notificarReprovarAssociacao(Membro m) {
        enviarNotificacao(m, new Notificacoes().new StatusTemplate(STATUS_REJEITAR), 5);
    }

    public static void notificarNovoAdministrador(Membro m) {
        enviarNotificacao(m,m.getRede().membrosAtivos(), new Notificacoes().new StatusTemplate(STATUS_NOVO_ADMINISTRADOR), 5);
    }

    public static void notificarRetirarAdministrador(Membro m) {
        enviarNotificacao(m,m.getRede().membrosAtivos(), new Notificacoes().new StatusTemplate(STATUS_RETIRAR_ADMINISTRADOR), 5);
    }

    public static void notificarDeixarARede(Membro m) {
        //Acontece null pointer
        enviarNotificacao(m,m.getRede().membrosAtivos(), new Notificacoes().new StatusTemplate(STATUS_DEIXOU_REDE), 5);
    }

    public static void notificarTornarAutoridade(Membro m) {
        enviarNotificacao(m,m.getRede().membrosAtivos(), new Notificacoes().new StatusTemplate(STATUS_NOVA_AUTORIDADE), 5);
    }

    public static void notificarRetirarAutoridade(Membro m) {
        enviarNotificacao(m,m.getRede().membrosAtivos(), new Notificacoes().new StatusTemplate(STATUS_RETIRAR_AUTORIDADE), 5);
    }

    public static void enviarAlerta(Alerta alerta) {
        Objectify ofy = OfyService.ofy();
        Membro m = ofy.load().type(Membro.class).id(alerta.getMembroId()).now();
        enviarNotificacao(m, m.getRede().membrosAtivos(), new Notificacoes().new AlertaTemplate(alerta), 10);
    }


    private static void enviarNotificacao(Membro m, Notificacoes.MessageTemplate template, int retries) {
        try {
            System.out.println("Enviando mensagem");
            List<String> dest = getDestinations(m);
            if(dest.size()>0) {
                sender.send(template.parse(m, m.getUsuarioId()), dest, 5);
            }
        } catch (IOException e) {
            System.out.println("Não foi possivel enviar uma mensagem:" + e.getMessage());
        }

    }

    private static void enviarNotificacao(Membro m, Collection<Membro> destinations, Notificacoes.MessageTemplate template, int i) {
        try {
            System.out.println("Enviando mensagem");
            for(Membro membro: destinations) {
                List<String> dest = getDestinations(membro);
                if(dest.size()>0) {
                    sender.send(template.parse(m, membro.getUsuarioId()), dest, 5);
                }
            }
        } catch (IOException e) {
            System.out.println("Não foi possivel enviar uma mensagem:" + e.getMessage());
        }
    }


    private static List<String> getDestinations(Membro membro) {
        List<String> ids = new ArrayList<String>();
            for(Dispositivo d: membro.getDispositivos()){
                ids.add(d.getDispositivoId());
            }
        return ids;
    }


}
