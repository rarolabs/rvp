package br.com.rarolabs.rvp.api.service;

import com.google.android.gcm.server.Message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import br.com.rarolabs.rvp.api.models.Membro;

/**
 * Created by rodrigosol on 2/11/15.
 */
public class Notificacoes {

    public abstract class MessageTemplate {

        protected final String[] params;

        public MessageTemplate(String... params){
            this.params = params;
        }

        public abstract Message parse(Membro m, String target) throws UnsupportedEncodingException;
        protected Message.Builder base(Membro m, String target) throws UnsupportedEncodingException {
            return  new Message.Builder()
                    .addData("target", target)
                    .addData("usuario_id", m.getUsuarioId())
                    .addData("membro_id", m.getId().toString())
                    .addData("rede_id",m.getRedeId().toString())
                    .addData("nome_rede", URLEncoder.encode(m.getNomeRede(), "UTF-8"))
                    .addData("nome_usuario",URLEncoder.encode(m.getUsuario().getNome(), "UTF-8"))
                    .addData("avatar", m.getUsuario().getAvatar())
                    .addData("avatar_blur", m.getUsuario().getAvatarBlur());
        }
    }


    public class StatusTemplate extends MessageTemplate{
        public StatusTemplate(String... params){
            super(params);
        }

        @Override
        public  Message parse(Membro m, String target) throws UnsupportedEncodingException {
           return super.base(m,target)
                       .addData("tipo", "STATUS")
                       .addData("tipo_status",params[0])
                       .build();
        }
    }


    public class SolicitacaoTemplate extends MessageTemplate {
        @Override
        public Message parse(Membro m, String target) throws UnsupportedEncodingException {
            return super.base(m, target)
                        .addData("tipo", "SOLICITACAO")
                        .build();
        }

    }


}
