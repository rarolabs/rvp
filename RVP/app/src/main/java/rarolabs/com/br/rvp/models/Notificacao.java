package rarolabs.com.br.rvp.models;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import rarolabs.com.br.rvp.R;

/**
 * Created by rodrigosol on 1/29/15.
 */
public class Notificacao extends SugarRecord<Notificacao> implements Iconable  {




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



    public Notificacao(){
    }
    public Notificacao(Bundle extras) {

        this.setTipo(Notificacao.Tipo.valueOf(extras.getString("tipo")));
        String extraTipoStatus = extras.getString("tipo_status");
        this.setTipoStatus(extraTipoStatus != null ? Notificacao.TipoStatus.valueOf(extraTipoStatus) : null);
        this.setUsuarioId(extras.getString("usuario_id"));
        this.setMembroId(Long.valueOf(extras.getString("membro_id")));
        this.setNomeRede(extras.getString("nome_rede"));
        this.setNomeUsuario(extras.getString("nome_usuario"));
        this.setData(new Date());


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
    public String getIconResource() {
        switch (tipo){
            case ALERTA:
                return getIconeAlerta(tipoAlerta,lido);
            case SISTEMA:
            case STATUS:
                return getIconeStatus(tipoStatus, lido);
            case SOLICITACAO:
                return "ic_cadastro_foto_vazia";
            default:
                return "ic_cadastro_foto_vazia";

        }
    }

    private String getIconeStatus(TipoStatus tipoStatus, Boolean lido) {
        if(tipoStatus==null){
            return "ic_drawer_notificacoes_normal";
        }
        switch (tipoStatus){
            case NOVO_ADMINSTRADOR:
                return getIconeFromString("ic_notificacoes_novo_admin",lido);
            case NOVO_MEMBRO:
                return "ic_cadastro_foto_vazia";
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

    public static long totalNotificacoes() {
        return Notificacao.count(Notificacao.class,null,null);
    }
    public static long totalNotificacoesNaoLidas() {
        return Notificacao.count(Notificacao.class, "lido = 0",null);
    }

    public static void marcarTodasComoLidas() {
        Notificacao.executeQuery("UPDATE notificacao SET lido = 1");
    }
    public static void excluirTodo() {
        Notificacao.executeQuery("DELETE FROM notificacao");
    }


    public static List<Notificacao> getNotificacoes(Integer skip, Integer count,Notificacao ultimaCarregada){
       return criaSecoes(Notificacao.findWithQuery(Notificacao.class, "SELECT * FROM notificacao ORDER by data desc LIMIT ?, ?", skip.toString(), count.toString()),ultimaCarregada);
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
