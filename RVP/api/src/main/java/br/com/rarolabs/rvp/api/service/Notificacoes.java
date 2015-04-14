package br.com.rarolabs.rvp.api.service;

import com.google.android.gcm.server.Message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import br.com.rarolabs.rvp.api.models.Alerta;
import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Mensagem;

/**
 * Created by rodrigosol on 2/11/15.
 */
public class Notificacoes {

    public abstract class MessageTemplate {

        protected final Object[] params;

        public MessageTemplate(Object... params){
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
                    .addData("avatar_blur", m.getUsuario().getAvatarBlur())
                    .addData("data", String.valueOf(new Date().getTime()));
        }
    }


    public class StatusTemplate extends MessageTemplate{
        public StatusTemplate(Object... params){
            super(params);
        }

        @Override
        public  Message parse(Membro m, String target) throws UnsupportedEncodingException {
           return super.base(m,target)
                       .addData("tipo", "STATUS")
                       .addData("tipo_status",(String)params[0])
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

    public class AlertaTemplate extends MessageTemplate {
        public AlertaTemplate(Object... params){
            super(params);
        }

        @Override
        public Message parse(Membro m, String target) throws UnsupportedEncodingException {
            Alerta alerta = (Alerta) params[0];
            String de = alerta.getDe() != null ? String.valueOf(alerta.getDe().getTime()) : null;
            String ate = alerta.getAte() != null ? String.valueOf(alerta.getAte().getTime()) : null;
            String detalhes = alerta.getDescricao() != null ? alerta.getDescricao() :"";
            return super.base(m, target)
                    .addData("tipo", "ALERTA")
                    .addData("tipo_alerta", alerta.getTipo().toString())
                    .addData("data_de",de )
                    .addData("data_ate",ate)
                    .addData("detalhes", detalhes)
                    .addData("backend_id", alerta.getId().toString())
                    .build();
        }

    }

    public class MensagemTemplate extends MessageTemplate {
        public MensagemTemplate(Object... params){
            super(params);
        }

        @Override
        public Message parse(Membro m, String target) throws UnsupportedEncodingException {
            Mensagem mensagem = (Mensagem) params[0];
            return super.base(m, target)
                    .addData("backend_id", mensagem.getAlerta().getId().toString())
                    .addData("data", String.valueOf(System.currentTimeMillis()))
                    .addData("detalhes", mensagem.getTexto())
                    .build();
        }
    }



}
