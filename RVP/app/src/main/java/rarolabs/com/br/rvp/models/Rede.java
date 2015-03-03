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
import com.google.api.client.util.DateTime;
import com.orm.SugarRecord;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.RedeDetalhada;
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
public class Rede extends SugarRecord<Rede>  {


    private  String localizacao = null;
    private  Long redeId = null;
    private  Long membroId = null;
    private  String nomeRede = null;
    private  String nomeAdministrador = null;
    private  String avatarAdministrador = null;
    private  Integer quantidadeMembros = null;
    private  Date ultimaAtividade = null;
    private  String status = null;

    public Rede(){}

    public Rede(RedeDetalhada redeDetalhada){

        this.redeId = redeDetalhada.getRedeId();
        this.membroId = redeDetalhada.getMembroId();
        this.nomeRede = redeDetalhada.getNomeRede();
        this.nomeAdministrador = redeDetalhada.getNomeAdministrador();
        this.avatarAdministrador = redeDetalhada.getAvatarAdministrador();
        this.quantidadeMembros = redeDetalhada.getQuantidadeMembros();
        if(redeDetalhada.getUltimaAtividade()!=null) {
            this.ultimaAtividade = new Date(redeDetalhada.getUltimaAtividade().getValue());
        }
        this.status = redeDetalhada.getStatus();
        this.localizacao = redeDetalhada.getLocalizacao();
        save();
        for(br.com.rarolabs.rvp.api.rvpAPI.model.Membro m : redeDetalhada.getMembros()){
            new Membro(m,this);
        }
    }

    public Long getRedeId() {
        return redeId;
    }

    public void setRedeId(Long redeId) {
        this.redeId = redeId;
    }

    public Long getMembroId() {
        return membroId;
    }

    public void setMembroId(Long membroId) {
        this.membroId = membroId;
    }

    public String getNomeRede() {
        return nomeRede;
    }

    public void setNomeRede(String nomeRede) {
        this.nomeRede = nomeRede;
    }

    public String getNomeAdministrador() {
        return nomeAdministrador;
    }

    public void setNomeAdministrador(String nomeAdministrador) {
        this.nomeAdministrador = nomeAdministrador;
    }

    public String getAvatarAdministrador() {
        return avatarAdministrador;
    }

    public void setAvatarAdministrador(String avatarAdministrador) {
        this.avatarAdministrador = avatarAdministrador;
    }

    public Integer getQuantidadeMembros() {
        return quantidadeMembros;
    }

    public void setQuantidadeMembros(Integer quantidadeMembros) {
        this.quantidadeMembros = quantidadeMembros;
    }

    public Date getUltimaAtividade() {
        return ultimaAtividade;
    }

    public void setUltimaAtividade(Date ultimaAtividade) {
        this.ultimaAtividade = ultimaAtividade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Membro> getMembros() {
        return Membro.find(Membro.class, "rede = ?", this.getId().toString());
    }


    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public static Collection<? extends Rede> getRedes() {
        return Rede.listAll(Rede.class);
    }
}