package br.com.rarolabs.rvp.api.models;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

/**
 * Created by rodrigosol on 12/18/14.
 */
@Entity
public class Membro {

    @Id
    private String id;
    public enum Papel { SYSADMIN, CRIADOR, ADMIN, AUTORIDADE, VIVIZINHO}
    public enum Status {ATIVO, INATIVO}

    private Papel papel = Papel.VIVIZINHO;
    private Status status = Status.ATIVO;

    private Date dataAssociacao;

    private Key<Rede> rede;
    private Key<Usuario> usuario;
    private Key<Visibilidade> visibilidade;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDataAssociacao() {
        return dataAssociacao;
    }

    public void setDataAssociacao(Date dataAssociacao) {
        this.dataAssociacao = dataAssociacao;
    }

    public Key<Rede> getRede() {
        return rede;
    }

    public void setRede(Key<Rede> rede) {
        this.rede = rede;
    }

    public Key<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(Key<Usuario> usuario) {
        this.usuario = usuario;
    }

    public Key<Visibilidade> getVisibilidade() {
        return visibilidade;
    }

    public void setVisibilidade(Key<Visibilidade> visibilidade) {
        this.visibilidade = visibilidade;
    }
}
