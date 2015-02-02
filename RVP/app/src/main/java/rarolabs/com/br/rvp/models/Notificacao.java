package rarolabs.com.br.rvp.models;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Spanned;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;



import rarolabs.com.br.rvp.R;

/**
 * Created by rodrigosol on 1/29/15.
 */
public class Notificacao implements Iconable  {



    public enum TipoAlerta {VEICULO_SUSPEITO,PESSOA_SUSPEITA,
        PANICO,PORTAO_ABERTO,SUSPEITA_DE_INVACAO,AUSENCIA, MUDANCA,INCENDIO}

    public enum TipoStatus {ADMINISTRADOR,NOVO_MEMBRO}

    public enum Tipo {SOLICITACAO,ALERTA,SISTEMA,STATUS}

    private static final SimpleDateFormat sdfSecao = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyDDmm");

    private String titulo;
    private Date data;
    private Spanned texto;
    private Drawable icon;
    private Boolean lido = false;
    private Tipo tipo;
    private Boolean secao = false;
    private TipoAlerta tipoAlerta;
    private TipoStatus tipoStatus;
    private String usuarioId;
    private Long membroId;
    private String nomeUsuario;
    private String nomeRede;





    public Notificacao(String titulo, Date data, Spanned texto, Tipo tipo) {
        this.titulo = titulo;
        this.data = data;
        this.texto = texto;
        this.tipo = tipo;
    }

    public Notificacao(String titulo, Date data, Tipo tipo) {
        this.titulo = titulo;
        this.data = data;
        this.tipo = tipo;

    }



    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Spanned getTexto() {
        return texto;
    }

    public void setTexto(Spanned texto) {
        this.texto = texto;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
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
            case ADMINISTRADOR:
                return getIconeFromString("ic_notificacoes_novo_admin",lido);
            case NOVO_MEMBRO:
                return "ic_cadastro_foto_vazia";
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


}
