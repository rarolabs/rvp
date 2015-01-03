package br.com.rarolabs.rvp.api.models;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import br.com.rarolabs.rvp.api.service.OfyService;

/**
 * Created by rodrigosol on 1/3/15.
 */
@Entity
public class Dispositivo {
    @Id
    Long id;

    @Index
    private String dispositivoId;

    @Index
    private String usuarioId;

    private String sistemaOperacional;
    private String versao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDispositivoId() {
        return dispositivoId;
    }

    public void setDispositivoId(String dispositivoId) {
        this.dispositivoId = dispositivoId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public static void registrar(String dispositivoId,String so, String versao, String email) {
        Objectify ofy = OfyService.ofy();
        Dispositivo d = ofy.load().type(Dispositivo.class).filter("dispositivoId", dispositivoId).first().now();
        if (d != null) {
            return;
        }
        d = new Dispositivo();
        d.setDispositivoId(dispositivoId);
        d.setUsuarioId(email);
        d.setSistemaOperacional(so);
        d.setVersao(versao);

        ofy.save().entity(d).now();

    }

    public static void desregistrar(String dispositivoId,String email) {
        Objectify ofy = OfyService.ofy();
        Dispositivo d = ofy.load().type(Dispositivo.class).filter("dispositivoId", dispositivoId).first().now();

        if (d == null || !d.getUsuarioId().equals(email)) {
            return;
        }
        ofy.delete().entity(d).now();

    }
}
