package br.com.rarolabs.rvp.api.models;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by rodrigosol on 12/18/14.
 */
@Entity
public class Mensagem {
    @Id
    private Long id;
    private Key<Alerta> alerta;
    private Key<Usuario> usuario;
    private String texto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Key<Alerta> getAlerta() {
        return alerta;
    }

    public void setAlerta(Key<Alerta> alerta) {
        this.alerta = alerta;
    }

    public Key<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(Key<Usuario> usuario) {
        this.usuario = usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
