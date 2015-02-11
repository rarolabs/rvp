package rarolabs.com.br.rvp.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.orm.SugarRecord;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
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



    public enum TipoAlerta {VEICULO_SUSPEITO,PESSOA_SUSPEITA,
        PANICO,PORTAO_ABERTO,SUSPEITA_DE_INVACAO,AUSENCIA, MUDANCA,INCENDIO}

    public enum TipoStatus {NOVO_MEMBRO,NOVO_ADMINSTRADOR,NOVA_AUTORIDADE}

    public enum Tipo {SOLICITACAO,ALERTA,SISTEMA,STATUS}

    private static final SimpleDateFormat sdfSecao = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyDDmm");


    private Date data;
    private int icon;
    private Boolean lido = false;
    private Tipo tipo;
    private Boolean secao = false;
    private TipoAlerta tipoAlerta;
    private TipoStatus tipoStatus;
    private String usuarioId;
    private Long membroId;
    private String nomeUsuario;
    private String nomeRede;
    private String texto;
    private Boolean respondida;
    private String avatar;
    private String avatarBlur;


    public Notificacao(){
    }

    public Notificacao(Bundle extras) {
        this.target = extras.getString("target","");
        this.setTipo(Notificacao.Tipo.valueOf(extras.getString("tipo")));
        String extraTipoStatus = extras.getString("tipo_status");
        this.setTipoStatus(extraTipoStatus != null ? Notificacao.TipoStatus.valueOf(extraTipoStatus) : null);
        this.setUsuarioId(extras.getString("usuario_id"));
        this.setMembroId(Long.valueOf(extras.getString("membro_id")));
        try {
            this.setNomeRede(URLDecoder.decode(extras.getString("nome_rede"), "UTF-8"));
            this.setNomeUsuario(URLDecoder.decode(extras.getString("nome_usuario"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        this.setData(new Date());
        Log.d("Image", "Avatar:" + extras.getString("avatar"));
        Log.d("Image","Avatar:" + extras.toString());
        this.setAvatar(extras.getString("avatar"));
        this.setAvatarBlur(extras.getString("avatar_blur"));


    }

    public void autoLido(){
        if(tipo != Tipo.SOLICITACAO){
            setLido(true);
            save();
        }
    }

    public String getTitulo(Context context) {

        switch (tipo){
            case SOLICITACAO:
                return context.getString(R.string.titulo_notificacao_solicitacao);
            case STATUS:
                switch (tipoStatus){
                    case NOVO_MEMBRO:
                        return context.getString(R.string.titulo_notificacao_novo_membro);
                    case NOVO_ADMINSTRADOR:
                        return context.getString(R.string.titulo_notificacao_novo_admin);
                    case NOVA_AUTORIDADE:
                        return context.getString(R.string.titulo_notificacao_nova_autoridade);
                }
        }

        return "Não implementado";
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
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
                    case NOVO_ADMINSTRADOR:
                        return Html.fromHtml(String.format(context.getString(R.string.descricao_notificacao_novo_admin), nomeUsuario, nomeRede));
                    case NOVA_AUTORIDADE:
                        return Html.fromHtml(String.format(context.getString(R.string.descricao_notificacao_nova_autoridade), nomeUsuario, nomeRede));

                }
        }
        return Html.fromHtml("Nao implementado");

    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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

    public String getSecao() {
        int diffDays = getDiff(data);
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
            case NOVO_ADMINSTRADOR:
                return getIconeFromString("ic_notificacoes_novo_admin",lido);
            case NOVO_MEMBRO:
                return getIconeSolicitacao(icone);
            case NOVA_AUTORIDADE:
                return getIconeFromString("ic_alertas_policia",lido);

        }
        return "";
    }

    private String getIconeAlerta(TipoAlerta tipoAlerta, Boolean lido) {
        switch (tipoAlerta){
            case VEICULO_SUSPEITO:
                return getIconeFromString("ic_alertas_veiculo", lido);
            case PESSOA_SUSPEITA:
                return getIconeFromString("ic_alertas_pessoa", lido);
            case PORTAO_ABERTO:
            case PANICO:
                return getIconeFromString("ic_alertas_panico", lido);
            case SUSPEITA_DE_INVACAO:
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

    private String getIconeFromString(String nome, Boolean lido) {
        String sufixo = (lido ? "read" : "unread");
        return nome+"_" +sufixo;
    }

    public static long totalNotificacoes(String target) {
        String[] params = {target};
        return Notificacao.count(Notificacao.class, "target=?", params);
    }
    public static long totalNotificacoesNaoLidas(String target) {
        String[] params = {target};
        return Notificacao.count(Notificacao.class, "lido = 0 and target=?",params);
    }

    public static void marcarTodasComoLidas(String target) {
        Notificacao.executeQuery("UPDATE notificacao SET lido = 1 where target = '"+target+"'");
    }
    public static void excluirTodo(String target) {
        Notificacao.executeQuery("DELETE FROM notificacao where target = '"+target+"'");
    }


    public static List<Notificacao> getNotificacoes(Integer skip, Integer count,String target,Notificacao ultimaCarregada){
       return criaSecoes(Notificacao.findWithQuery(Notificacao.class, "SELECT * FROM notificacao where target = ? ORDER by data desc LIMIT ?, ?", target, skip.toString(), count.toString()),ultimaCarregada);
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
