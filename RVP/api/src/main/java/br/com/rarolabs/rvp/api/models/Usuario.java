package br.com.rarolabs.rvp.api.models;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigosol on 12/18/14.
 */
@Entity
public class Usuario {
    @Id
    private String id;

    private String nome;
    private String email;

    private String dddTelefoneFixo;
    private String telefoneFixo;

    private String dddTelefoneCelular;
    private String telefoneCelular;

    @Index
    private List<Key<Membro>> partipacoes = new ArrayList<Key<Membro>>();

    @Index
    private List<Key<Alerta>> alertas = new ArrayList<Key<Alerta>>();

    @Index
    private List<Key<Endereco>> enderecos = new ArrayList<Key<Endereco>>();

    @Index
    private List<Key<Mensagem>> mensagens = new ArrayList<Key<Mensagem>>();

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
