package br.com.rarolabs.rvp.api.models;


import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import br.com.rarolabs.rvp.api.service.OfyService;


@Entity
public class Rede {

    @Id
    private Long id;
    @Index
    private Double latitude;

    @Index
    private Double Longitude;

    @Index
    private String nome;

    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    List<Ref<Membro>> membros = new ArrayList<Ref<Membro>>();

    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    List<Ref<Alerta>> alertas = new ArrayList<Ref<Alerta>>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Collection<Membro> getMembros() {
        return OfyService.ofy().load().refs(membros).values();
    }


    public Collection<Alerta> getAlertas() {
        return OfyService.ofy().load().refs(alertas).values();
    }


}
