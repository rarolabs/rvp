package br.com.rarolabs.rvp.api.models;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

import br.com.rarolabs.rvp.api.service.OfyService;

/**
 * Created by rodrigosol on 12/18/14.
 */
@Entity
public class Mensagem {
    @Id
    private Long id;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<Alerta> alerta;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<Usuario> usuario;

    private String texto;
    private Date data;

    private Long alertaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAlerta(Alerta alerta){
        this.alerta = Ref.create(alerta);
    }
    public Alerta getAlerta() {
        return alerta.get();
    }

    public Usuario getUsuario() {
        return usuario.get();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = Ref.create(usuario);
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Membro getMembro(Usuario user) {
        Objectify ofy = OfyService.ofy();
        Rede r = ofy.load().type(Rede.class).id(getAlerta().getRedeId()).now();
        return r.getMembroByUser(user);
    }

    public Long getAlertaId() {
        return alertaId;
    }

    public void setAlertaId(Long alertaId){
        this.alertaId = alertaId;
        setAlerta(OfyService.ofy().load().type(Alerta.class).id(alertaId).now());
    }
}
