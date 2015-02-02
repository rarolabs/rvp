package br.com.rarolabs.rvp.api.service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

import java.io.IOException;
import java.util.List;

import br.com.rarolabs.rvp.api.auth.Constants;
import br.com.rarolabs.rvp.api.models.Dispositivo;
import br.com.rarolabs.rvp.api.models.Membro;

/**
 * Created by rodrigosol on 2/2/15.
 */
public class NotificacaoService {
    public static void notificarSolicacaoAssociacao(Membro m) {
        Sender sender = new Sender(Constants.GCM_API_KEY);
        for(Membro admin: m.getRede().membrosAdministradores()){
            try {
                System.out.println("Enviando mensagem para:" + m.getUsuario().getEmail());
                Message msg = mensagemDeSolicitacao(m);
                sender.send(msg,admin.getUsuario().getDispositivos(),20);
            } catch (IOException e) {
                System.out.println("Não foi possivel enviar uma mensagem:" + e.getMessage());
            }

        }
    }

    private static Message mensagemDeSolicitacao(Membro m) {
        return new Message.Builder()
                .addData("tipo","SOLICITACAO")
                .addData("usuario_id",m.getUsuarioId())
                .addData("membro_id",m.getId().toString())
                .addData("rede_id",m.getRedeId().toString())
                .addData("nome_rede",m.getNomeRede())
                .addData("nome_usuario",m.getUsuario().getNome())
                .build();

    }
}
