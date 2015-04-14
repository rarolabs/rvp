package rarolabs.com.br.rvp.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.ImageView;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.orm.SugarRecord;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;
import rarolabs.com.br.rvp.utils.ImageUtil;

/**
 * Created by rodrigosol on 1/29/15.
 */
public class Notificacao extends SugarRecord<Notificacao> implements Iconable  {


    private String target;
    private Long backendId;

    public enum TipoAlerta {PESSOA_SUSPEITA,VEICULO_SUSPEITO,AUSENCIA,MUDANCA,PANICO,INCENDIO,EMERGENCIA_POLICIAL};
    public enum TipoStatus {NOVO_MEMBRO,NOVO_ADMINISTRADOR,NOVA_AUTORIDADE,REJEITAR,RETIRAR_ADMINISTRADOR,RETIRAR_AUTORIDADE,DEIXOU_REDE}

    public enum Tipo {SOLICITACAO,ALERTA,SISTEMA,STATUS}

    private static final SimpleDateFormat sdfSecao = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyDDmm");
    private static final SimpleDateFormat sdfDia = new SimpleDateFormat("dd/MM");


    private Long data;
    private int icon;
    private Boolean lido = false;
    private Tipo tipo;
    private Boolean secao = false;
    private TipoAlerta tipoAlerta;
    private TipoStatus tipoStatus;
    private String usuarioId;
    private Long membroId;
    private Long redeId;
    private String nomeUsuario;
    private String nomeRede;
    private String texto;
    private Boolean respondida;
    private Boolean abrivel = true;
    private String avatar;
    private String avatarBlur;

    private String detalhes;
    private Long dataDe;
    private Long dataAte;





    public Notificacao(){
    }

    public Notificacao(Bundle extras) {
        this.target = extras.getString("target","");
        this.setTipo(Notificacao.Tipo.valueOf(extras.getString("tipo")));
        String extraTipoStatus = extras.getString("tipo_status");
        this.setTipoStatus(extraTipoStatus != null ? Notificacao.TipoStatus.valueOf(extraTipoStatus) : null);

        this.setUsuarioId(extras.getString("usuario_id"));
        this.setRedeId(Long.parseLong(extras.getString("rede_id")));
        this.setMembroId(Long.valueOf(extras.getString("membro_id")));
        try {
            this.setNomeRede(URLDecoder.decode(extras.getString("nome_rede"), "UTF-8"));
            this.setNomeUsuario(URLDecoder.decode(extras.getString("nome_usuario"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String extraTipoAlerta = extras.getString("tipo_alerta",null);
        if(extraTipoAlerta!=null) {
            this.setTipoAlerta(extraTipoAlerta != null ? TipoAlerta.valueOf(extraTipoAlerta) : null);
            this.backendId = Long.parseLong(extras.getString("backend_id"));
            String de = extras.getString("data_de");
            if(de!=null && !de.equals("")){
                this.setDataDe(Long.parseLong(de));
            }

            String ate = extras.getString("data_ate");
            if(ate !=null && !ate.equals("")){
                this.setDataAte(Long.parseLong(ate));
            }
            try {
                this.setDetalhes(URLDecoder.decode(extras.getString("detalhes"), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }


        this.setData(Long.parseLong(extras.getString("data")));
        this.setAvatar(extras.getString("avatar"));
        this.setAvatarBlur(extras.getString("avatar_blur"));
        save();

        if(extraTipoAlerta!=null){
            new Comentario(this,this.membroId,this.nomeUsuario,this.avatar,this.avatarBlur,this.data,this.detalhes).save();
        }




    }

    public void autoLido(){
        if(tipo != Tipo.SOLICITACAO){
            setLido(true);
            save();
        }
    }
    public String getAlertaSubTitulo(Context context) {
        switch (tipoAlerta){
            case MUDANCA:
                if(dataDe != null && dataDe > 0) {
                    return String.format(context.getString(R.string.titulo_mudanca), sdfDia.format(new Date(dataDe)));
                }else{
                    return context.getString(getEsquema().getTitle());
                }
            case AUSENCIA:
                if(dataDe != null && dataDe > 0 ) {
                    if (dataAte != null && dataAte > 0 && dataDe != dataAte) {
                        return String.format(context.getString(R.string.titulo_ausencia), sdfDia.format(new Date(dataDe)), sdfDia.format(new Date(dataAte)));
                    }else{
                        return String.format(context.getString(R.string.titulo_ausencia_unico_dia), sdfDia.format(new Date(dataDe)));
                    }

                }else{
                    return context.getString(getEsquema().getTitle());
                }
        }
        return "";

    }
    public String getTitulo(Context context) {

        switch (tipo){
            case SOLICITACAO:
                return context.getString(R.string.titulo_notificacao_solicitacao);
            case STATUS:
                switch (tipoStatus){
                    case NOVO_MEMBRO:
                        return context.getString(R.string.titulo_notificacao_novo_membro);
                    case NOVO_ADMINISTRADOR:
                        return context.getString(R.string.titulo_notificacao_novo_admin);
                    case NOVA_AUTORIDADE:
                        return context.getString(R.string.titulo_notificacao_nova_autoridade);
                    case REJEITAR:
                        return context.getString(R.string.titulo_notificacao_rejeitado);
                    case RETIRAR_ADMINISTRADOR:
                        return context.getString(R.string.titulo_notificacao_deixou_de_ser_administrador);
                    case RETIRAR_AUTORIDADE:
                        return context.getString(R.string.titulo_notificacao_deixou_de_ser_autoridade);
                    case DEIXOU_REDE:
                        return context.getString(R.string.titulo_notificacao_deixou_a_rede);
                }
                break;
            case ALERTA:
                switch (tipoAlerta){
                    case PESSOA_SUSPEITA:
                    case VEICULO_SUSPEITO:
                    case INCENDIO:
                    case EMERGENCIA_POLICIAL:
                        return context.getString(getEsquema().getTitle());
                    case MUDANCA:
                        if(dataDe != null && dataDe > 0) {
                            return String.format(context.getString(R.string.titulo_mudanca), sdfDia.format(new Date(dataDe)));
                        }else{
                            return context.getString(getEsquema().getTitle());
                        }
                    case AUSENCIA:
                        if(dataDe != null && dataDe > 0 ) {
                            if (dataAte != null && dataAte > 0 && dataDe != dataAte) {
                                return String.format(context.getString(R.string.titulo_ausencia), sdfDia.format(new Date(dataDe)), sdfDia.format(new Date(dataAte)));
                            }else{
                                return String.format(context.getString(R.string.titulo_ausencia_unico_dia), sdfDia.format(new Date(dataDe)));
                            }

                        }else{
                            return context.getString(getEsquema().getTitle());
                        }
                }
        }

        return "Não implementado";
    }

    public Date getData() {
        return new Date(data);
    }

    public void setData(Long data) {
        this.data = data;
    }

    public Spanned getTexto(Context context) {
        String str = null;
        switch (tipo){
            case SOLICITACAO:
                return Html.fromHtml(String.format(context.getString(R.string.descricao_notificacao_solicitacao), nomeUsuario, nomeRede));
            case STATUS:
                switch (tipoStatus){
                    case NOVO_MEMBRO:
                        return Html.fromHtml(String.format(context.getString(R.string.descricao_notificacao_novo_membro), nomeUsuario, nomeRede));
                    case NOVO_ADMINISTRADOR:
                        return Html.fromHtml(String.format(context.getString(R.string.descricao_notificacao_novo_admin), nomeUsuario, nomeRede));
                    case NOVA_AUTORIDADE:
                        return Html.fromHtml(String.format(context.getString(R.string.descricao_notificacao_nova_autoridade), nomeUsuario, nomeRede));
                    case REJEITAR:
                        return Html.fromHtml(String.format(context.getString(R.string.descricao_notificacao_rejeitado), nomeUsuario, nomeRede));
                    case RETIRAR_ADMINISTRADOR:
                        return Html.fromHtml(String.format(context.getString(R.string.descricao_notificacao_deixou_de_ser_administrador), nomeUsuario, nomeRede));
                    case RETIRAR_AUTORIDADE:
                        return Html.fromHtml(String.format(context.getString(R.string.descricao_notificacao_deixou_de_ser_autoridade), nomeUsuario, nomeRede));
                    case DEIXOU_REDE:
                        return Html.fromHtml(String.format(context.getString(R.string.descricao_notificacao_deixou_a_rede), nomeUsuario, nomeRede));

                }
                break;
            case ALERTA:
                Comentario comentario = this.getUltimoComentario();
                if(comentario!=null) {
                    return Html.fromHtml(comentario.getTexto());
                }else{
                    return Html.fromHtml("");
                }

        }
        return Html.fromHtml("Nao implementado");

    }

    public Comentario getUltimoComentario() {
        List<Comentario> comentarios = Comentario.findWithQuery(Comentario.class, "SELECT * FROM comentario where notificacao = ?  ORDER by data desc LIMIT 0, 1", this.getId().toString());
        Log.d("Comentario","size:" + comentarios.size());
        if(comentarios.size()>0){
            Log.d("comentario",comentarios.get(0).toString());
            return comentarios.get(0);
        }
        return null;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Long getBackendId() {
        return backendId;
    }

    public void setBackendId(Long backendId) {
        this.backendId = backendId;
    }

    public Boolean isLido() {
        return lido;
    }

    public void setLido(Boolean lido) {
        this.lido = lido;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setSecao(Boolean secao) {
        this.secao = secao;
    }
    public Boolean isSecao() {
        return this.secao;
    }

    public TipoAlerta getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(TipoAlerta tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public TipoStatus getTipoStatus() {
        return tipoStatus;
    }

    public void setTipoStatus(TipoStatus tipoStatus) {
        this.tipoStatus = tipoStatus;
    }

    public Boolean getLido() {
        return lido;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getMembroId() {
        return membroId;
    }

    public void setMembroId(Long membroId) {
        this.membroId = membroId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeRede() {
        return nomeRede;
    }

    public void setNomeRede(String nomeRede) {
        this.nomeRede = nomeRede;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Boolean getRespondida() {
        return respondida;
    }

    public void setRespondida(Boolean respondida) {
        this.respondida = respondida;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setAvatarBlur(String avatarBlur) {
        this.avatarBlur = avatarBlur;
    }

    public String getAvatarBlur() {
        return avatarBlur;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Boolean isAbrivel() {
        return abrivel;
    }

    public void setAbrivel(Boolean abrivel) {
        this.abrivel = abrivel;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public Date getDataDe() {
        return new Date(dataDe);
    }

    public void setDataDe(Long dataDe) {
        this.dataDe = dataDe;
    }

    public Date getDataAte() {
        return new Date(dataAte);
    }

    public void setDataAte(Long dataAte) {
        this.dataAte = dataAte;
    }

    public Long getRedeId() {
        return redeId;
    }

    public void setRedeId(Long redeId) {
        this.redeId = redeId;
    }


    public String getSecao() {
        int diffDays = getDiff(getData());
        switch (diffDays){
            case 0:
                return "Hoje";
            case 1:
                return "Ontem";
            case 2:
                return "Anteontem";
            default:
                return sdfSecao.format(data);
        }

    }

    private static  int getDiff(Date data) {
        Date now = new Date();
        long diff = now.getTime() - data.getTime();
        return  (int) diff / (24 * 60 * 60 * 1000);
    }

    public String getAlertaIconResource(CircleImageView icone) {
        return getIconeAlerta(tipoAlerta,false);
    }

    @Override
    public String getIconResource(CircleImageView icone) {

        switch (tipo){
            case ALERTA:
                return getIconeAlerta(tipoAlerta,lido);
            case SISTEMA:
            case STATUS:
                return getIconeStatus(tipoStatus, lido,icone);
            case SOLICITACAO:
                return getIconeSolicitacao(icone);
            default:
                return "ic_cadastro_foto_vazia";

        }
    }

    private String getIconeSolicitacao(final CircleImageView icone) {
        if(getAvatar()==null){
           Object[] params = {this,getUsuarioId()};
           new AsyncTask<Object,Void,Void>(){
               @Override
               protected Void doInBackground(Object... params) {
                   Notificacao n = (Notificacao) params[0];
                   ImageView iv = (ImageView) params[1];
                   Context context = iv.getContext();
                   SharedPreferences settings = context.getSharedPreferences("RVP", 0);
                   GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(context, Constants.OAUTH_CLIENT_ID);
                   BackendServices service = new BackendServices(context, settings.getString(Constants.ACCOUNT, null), Constants.BACKEND_URL);
                   try {
                       final Usuario u = service.buscarUsuario(n.getUsuarioId());
                       Log.d("Usuario recuperado","Dados:" + u.toString());
                       if(u != null){
                           n.setAvatar(u.getAvatar());
                           n.setAvatarBlur(u.getAvatarBlur());
                           n.save();
                       }
                       ((Activity)icone.getContext()).runOnUiThread(new Runnable() {
                           public void run() {
                               Log.d("Usuario Recuperado", "Avatar:" + u.getAvatar());
                               ImageUtil.loadIconAssync(u.getAvatar(), icone,40);

                           }
                       });

                   } catch (BackendExpection backendExpection) {
                       backendExpection.printStackTrace();
                   }


                   return null;
               }
           }.execute(this,icone);

        }else {
            String url = getAvatar();
            ImageUtil.loadIconAssync(url, icone,40);
        }
        return null;

    }


    private String getIconeStatus(TipoStatus tipoStatus, Boolean lido, CircleImageView icone) {
        if(tipoStatus==null){
            return getIconeSolicitacao(icone);
        }
        switch (tipoStatus){
            case RETIRAR_ADMINISTRADOR:
            case NOVO_ADMINISTRADOR:
                return getIconeFromString("ic_notificacoes_novo_admin", lido);
            case NOVO_MEMBRO:
            case REJEITAR:
            case DEIXOU_REDE:
                return getIconeSolicitacao(icone);
            case NOVA_AUTORIDADE:
            case RETIRAR_AUTORIDADE:
                return getIconeFromString("ic_alertas_policia", lido);
        }
        return "";
    }

    private String getIconeAlerta(TipoAlerta tipoAlerta, Boolean lido) {
        switch (tipoAlerta){
            case VEICULO_SUSPEITO:
                return getIconeFromString("ic_alertas_veiculo", lido);
            case PESSOA_SUSPEITA:
                return getIconeFromString("ic_alertas_pessoa", lido);
            case PANICO:
                return getIconeFromString("ic_alertas_panico", lido);
            case EMERGENCIA_POLICIAL:
                return getIconeFromString("ic_alertas_policia", lido);
            case AUSENCIA:
                return getIconeFromString("ic_alertas_ausencia", lido);
            case MUDANCA:
                return getIconeFromString("ic_alertas_mudanca", lido);
            case INCENDIO:
                return getIconeFromString("ic_alertas_incendio", lido);
          }
        return "";

    }

    public EsquemaAlerta getEsquema() {
        return EsquemaAlerta.get(this.tipoAlerta.toString());
    }


    private String getIconeFromString(String nome, Boolean lido) {
        String sufixo = (lido ? "read" : "unread");
        return nome+"_" +sufixo;
    }

    public static long totalNotificacoes(String target) {
        String[] params = {target};
        return Notificacao.count(Notificacao.class, "tipo <> 'ALERTA' and target=?", params);
    }
    public static long totalAlertas(String target) {
        String[] params = {target};
        return Notificacao.count(Notificacao.class, "tipo = 'ALERTA' and target=?", params);
    }

    public static long totalNotificacoesNaoLidas(String target) {
        String[] params = {target};
        return Notificacao.count(Notificacao.class, "tipo <> 'ALERTA' and lido = 0 and target=?",params);
    }
    public static long totalAlertasNaoLidos(String target) {
        String[] params = {target};
        return Notificacao.count(Notificacao.class, "tipo = 'ALERTA' and lido = 0 and target=?",params);
    }

    public static void marcarTodasNotificacoesComoLidas(String target) {
        Notificacao.executeQuery("UPDATE notificacao SET lido = 1 where TIPO <> 'ALERTA' and target = '"+target+"'");
    }

    public static void marcarTodosAlertasComoLidos(String target) {
        Notificacao.executeQuery("UPDATE notificacao SET lido = 1 where TIPO = 'ALERTA' and target = '"+target+"'");
    }

    public static void excluirTodasNotificacoes(String target) {
        Notificacao.executeQuery("DELETE FROM notificacao where tipo <> 'ALERTA' and  target = '"+target+"'");
    }

    public static void excluirTodosAlertas(String target) {
        Notificacao.executeQuery("DELETE FROM notificacao where tipo = 'ALERTA' and  target = '"+target+"'");
    }


    public static List<Notificacao> getNotificacoes(Integer skip, Integer count,String target,Notificacao ultimaCarregada){
       return criaSecoes(Notificacao.findWithQuery(Notificacao.class, "SELECT * FROM notificacao where TIPO <> ? and target = ? ORDER by data desc LIMIT ?, ?", "ALERTA" ,target, skip.toString(), count.toString()),ultimaCarregada);
    }

    public static List<Notificacao> getAlertas(Integer skip, Integer count,String target,Notificacao ultimaCarregada){
        return criaSecoes(Notificacao.findWithQuery(Notificacao.class, "SELECT * FROM notificacao where TIPO = ? and target = ? ORDER by data desc LIMIT ?, ?", "ALERTA" ,target, skip.toString(), count.toString()),ultimaCarregada);
    }

    public List<Comentario> getComentarios() {
        return new ArrayList<Comentario>();
    }


    private static List<Notificacao> criaSecoes(List<Notificacao> notificacaos,Notificacao ultimaCarregada) {
        String secaoAtual = "";
        if(ultimaCarregada!=null){
            secaoAtual = ultimaCarregada.getSecao();
        }
        for(Notificacao n: notificacaos){
            if(!n.getSecao().equals(secaoAtual)){
                n.setSecao(true);
                secaoAtual = n.getSecao();
            }else{
                n.setSecao(false);
            }
        }
        return notificacaos;
    }


}
