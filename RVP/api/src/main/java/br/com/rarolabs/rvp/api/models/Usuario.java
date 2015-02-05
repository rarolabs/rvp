package br.com.rarolabs.rvp.api.models;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.api.server.spi.config.ApiTransformer;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.rarolabs.rvp.api.responders.RedeDetalhada;
import br.com.rarolabs.rvp.api.service.OfyService;


/**
 * Created by rodrigosol on 12/18/14.
 */


//@ApiTransformer(UsuarioTransform.class)
@Entity
public class Usuario {
    @Id
    private String id;

    private String nome;

    private String dddTelefoneFixo;
    private String telefoneFixo;

    private String dddTelefoneCelular;
    private String telefoneCelular;

    private String avatar;

    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Collection<Ref<Membro>> partipacoes = new ArrayList<Ref<Membro>>();

    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Collection<Ref<Alerta>> alertas = new ArrayList<Ref<Alerta>>();

    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Collection<Ref<Endereco>> enderecos = new ArrayList<Ref<Endereco>>();

    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Collection<Ref<Mensagem>> mensagens = new ArrayList<Ref<Mensagem>>();

    public Usuario(){

    }

    public Usuario(String nome, String email, String dddTelefoneCelular, String telefoneCelular, String dddTelefoneFixo, String telefoneFixo) {
        this.nome = nome;
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
        return id;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        System.out.println("Avatar definido:" + avatar);
    }

    public Collection<Membro> getPartipacoes() {
        return OfyService.ofy().load().refs(partipacoes).values();
    }

    public Collection<Alerta> getAlertas() {
        return OfyService.ofy().load().refs(alertas).values();
    }

    public Collection<Endereco> getEnderecos() {
        return OfyService.ofy().load().refs(enderecos).values();
    }

    public Collection<Mensagem> getMensagens() {
        return OfyService.ofy().load().refs(mensagens).values();
    }

    public void add(Membro membro){
        partipacoes.add(Ref.create(membro));
    }


    public static Collection<RedeDetalhada> minhasRedes(String usuarioId) {
        Usuario u = OfyService.ofy().load().type(Usuario.class).id(usuarioId).now();
        List<Membro> membros =  OfyService.ofy().load().type(Membro.class)
                                                .filter("usuario",u).list();

        return detalhaRedes(Rede.filtrarMinhasRedes(membros));
    }

    private static Collection<RedeDetalhada> detalhaRedes(Collection<Membro> membros) {
        Collection<RedeDetalhada> redes = new ArrayList<RedeDetalhada>(membros.size());
        for(Membro m: membros){
            redes.add(new RedeDetalhada(m));
        }
        return redes;
    }

    public static Usuario novoUsuario(Usuario usuario) {
        Objectify ofy = OfyService.ofy();
        Usuario recuperado = ofy.load().type(Usuario.class).id(usuario.getEmail()).now();

        if(recuperado != null){
            recuperado.setNome(usuario.getNome());
            recuperado.setDddTelefoneCelular(usuario.getDddTelefoneCelular());
            recuperado.setDddTelefoneFixo(usuario.getDddTelefoneFixo());
            recuperado.setTelefoneCelular(usuario.getTelefoneCelular());
            recuperado.setTelefoneFixo(usuario.getTelefoneFixo());
            ofy.save().entity(recuperado).now();
            usuario = recuperado;
        }else{
            ofy.save().entity(usuario).now();
        }

        return usuario;

    }

    public static Usuario buscar(String id) throws NotFoundException {
        Usuario u = OfyService.ofy().load().type(Usuario.class).id(id).now();
        if(u==null){
            throw  new NotFoundException("Usuario " + id + " nao encontrado");
        }

        return u;
    }

    public static void apagar(String id) {
        Objectify ofy = OfyService.ofy();
        ofy.delete().type(Usuario.class).id(id).now();
    }

    public List<String> getDispositivos() {
        List<String> ids = new ArrayList<String>();

        for(Dispositivo d:
                OfyService.ofy().load()
                          .type(Dispositivo.class)
                          .filter("usuarioId",this.getId()).list()){
            ids.add(d.getDispositivoId());
        }
        return ids;
    }
}
