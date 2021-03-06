package br.com.rarolabs.rvp.api.models;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.appengine.api.search.GeoPoint;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;

import java.util.Date;

import br.com.rarolabs.rvp.api.service.OfyService;


/**
 * Created by rodrigosol on 12/18/14.
 */
@Entity
public class Endereco {
    @Id
    private Long id;


    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<Usuario> usuario;


    private Double latitude;
    private Double Longitude;
    private String descricao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario.get();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = Ref.create(usuario);
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
