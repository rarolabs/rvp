package br.com.rarolabs.rvp.api.models;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.api.server.spi.config.ApiTransformer;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

import transforms.UsuarioTransform;

/**
 * Created by rodrigosol on 12/18/14.
 */


//@ApiTransformer(UsuarioTransform.class)
@Entity
public class Usuario {
    @Id
    private Long id;

    private String nome;
    @Index
    private String email;

    private String dddTelefoneFixo;
    private String telefoneFixo;

    private String dddTelefoneCelular;
    private String telefoneCelular;

    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private List<Key<Membro>> partipacoes = new ArrayList<Key<Membro>>();

    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private List<Key<Alerta>> alertas = new ArrayList<Key<Alerta>>();

    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private List<Key<Endereco>> enderecos = new ArrayList<Key<Endereco>>();

    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private List<Key<Mensagem>> mensagens = new ArrayList<Key<Mensagem>>();

    public Usuario(){

    }

    public Usuario(String nome, String email, String dddTelefoneCelular, String telefoneCelular, String dddTelefoneFixo, String telefoneFixo) {
        this.nome = nome;
        this.email = email;
        this.dddTelefoneCelular = dddTelefoneCelular;
        this.telefoneCelular = telefoneCelular;
        this.dddTelefoneFixo = this.dddTelefoneFixo;
        this.telefoneFixo = telefoneFixo;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getDddTelefoneCelular() {
        return dddTelefoneCelular;
    }

    public void setDddTelefoneCelular(String dddTelefoneCelular) {
        this.dddTelefoneCelular = dddTelefoneCelular;
    }

    public String getTelefoneFixo() {
        return telefoneFixo;
    }

    public void setTelefoneFixo(String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }

    public String getDddTelefoneFixo() {
        return dddTelefoneFixo;
    }

    public void setDddTelefoneFixo(String dddTelefoneFixo) {
        this.dddTelefoneFixo = dddTelefoneFixo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Key<Membro>> getPartipacoes() {
        return partipacoes;
    }

    public void setPartipacoes(List<Key<Membro>> partipacoes) {
        this.partipacoes = partipacoes;
    }

    public List<Key<Alerta>> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<Key<Alerta>> alertas) {
        this.alertas = alertas;
    }

    public List<Key<Endereco>> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Key<Endereco>> enderecos) {
        this.enderecos = enderecos;
    }

    public List<Key<Mensagem>> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Key<Mensagem>> mensagens) {
        this.mensagens = mensagens;
    }
}
