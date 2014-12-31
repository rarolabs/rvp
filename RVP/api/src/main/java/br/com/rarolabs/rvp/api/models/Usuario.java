package br.com.rarolabs.rvp.api.models;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.api.server.spi.config.ApiTransformer;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.rarolabs.rvp.api.service.OfyService;
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


    public static Collection<Membro> minhasRedes(Long usuarioId) {
        return OfyService.ofy().load().type(Usuario.class).id(usuarioId).now().getPartipacoes();
    }

    public static Usuario novoUsuario(Usuario usuario) throws ConflictException {
        Objectify ofy = OfyService.ofy();
        if(ofy.load().type(Usuario.class).filter("email", usuario.getEmail()).first().now() != null){
            throw new ConflictException("e-mail já cadastrado");
        }
        ofy.save().entity(usuario).now();
        return usuario;

    }

    public static Usuario buscar(Long id) throws NotFoundException {
        Usuario u = OfyService.ofy().load().type(Usuario.class).id(id).now();
        if(u==null){
            throw  new NotFoundException("Usuario " + id + " nao encontrado");
        }

        return u;
    }
}
