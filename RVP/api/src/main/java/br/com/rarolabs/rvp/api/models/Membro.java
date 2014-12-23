package br.com.rarolabs.rvp.api.models;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GeoPoint;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.StatusCode;

import java.util.Date;

/**
 * Created by rodrigosol on 12/18/14.
 */
@Entity
public class Membro {

    @Id
    private Long id;
    public enum Papel { SYSADMIN, CRIADOR, ADMIN, AUTORIDADE, VIVIZINHO}
    public enum Status {ATIVO, INATIVO}

    private Papel papel = Papel.VIVIZINHO;
    private Status status = Status.ATIVO;

    private Date dataAssociacao;

    private @Load Ref<Rede> rede;
    private @Load Ref<Usuario> usuario;
    private @Load Ref<Visibilidade> visibilidade;
    private @Load Ref<Endereco> endereco;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Rede getRede() {
        return rede.get();
    }

    public void setRede(Rede rede) {
        this.rede = Ref.create(rede);
    }

    public Usuario getUsuario() {
        return usuario.get();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = Ref.create(usuario);
    }

    public Visibilidade getVisibilidade() {
        return visibilidade.get();
    }

    public void setVisibilidade(Visibilidade visibilidade) {
        this.visibilidade = Ref.create(visibilidade);
    }

    public Endereco getEndereco() {
        return endereco.get();
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = Ref.create(endereco);
    }

}
