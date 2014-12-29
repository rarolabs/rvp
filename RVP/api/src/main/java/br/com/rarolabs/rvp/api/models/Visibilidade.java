package br.com.rarolabs.rvp.api.models;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by rodrigosol on 12/18/14.
 */
@Entity
public class Visibilidade {

    public enum Tipo {PUBLICO,PRIVADO, SOMENTE_COM_AUTORIDADE, SOMENTE_COM_ADMIN, COM_AUTORIDADE_E_ADMINISTRADOR}

    @Id
    private Long id;
    private Key<Membro> membro;
    private Tipo email;
    private Tipo telefoneFixo;
    private Tipo telefoneCelular;
    private Tipo endereco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Key<Membro> getMembro() {
        return membro;
    }

    public void setMembro(Key<Membro> membro) {
        this.membro = membro;
    }

    public Tipo getEmail() {
        return email;
    }

    public void setEmail(Tipo email) {
        this.email = email;
    }

    public Tipo getTelefoneFixo() {
        return telefoneFixo;
    }

    public void setTelefoneFixo(Tipo telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }

    public Tipo getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(Tipo telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public Tipo getEndereco() {
        return endereco;
    }

    public void setEndereco(Tipo endereco) {
        this.endereco = endereco;
    }
}
