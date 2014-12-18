package br.com.rarolabs.rvp.api.models;


import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Rede {

    @Id
    private String id;
    private Long latitude;
    private Long Longitude;
    private String nome;

    @Index
    List<Key<Membro>> membros = new ArrayList<Key<Membro>>();

    @Index
    List<Key<Alerta>> alertas = new ArrayList<Key<Alerta>>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return Longitude;
    }

    public void setLongitude(Long longitude) {
        Longitude = longitude;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Key<Membro>> getMembros() {
        return membros;
    }

    public void setMembros(List<Key<Membro>> membros) {
        this.membros = membros;
    }

    public List<Key<Alerta>> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<Key<Alerta>> alertas) {
        this.alertas = alertas;
    }
}
