package br.com.rarolabs.rvp.api.models;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import java.util.Date;

import br.com.rarolabs.rvp.api.service.OfyService;
import br.com.rarolabs.rvp.api.service.SearchService;

/**
 * Created by rodrigosol on 12/18/14.
 */
@Entity
public class Membro {

    @Id
    private Long id;



    public enum Papel { SYSADMIN, CRIADOR, ADMIN, AUTORIDADE, VIVIZINHO}
    public enum Status {ATIVO, INATIVO,AGUARDANDO_APROVACAO,REPROVADO}
    public enum Visibilidade {PUBLICO,PRIVADO, SOMENTE_COM_AUTORIDADE}

    private Papel papel = Papel.VIVIZINHO;
    private Status status = Status.ATIVO;

    private Date dataAssociacao;

    private Visibilidade visibilidadeEmail;
    private Visibilidade visibilidadeEndereco;
    private Visibilidade visibilidadeTelefoneFixo;
    private Visibilidade visibilidadeTelefoneCelular;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private @Load Ref<Rede> rede;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private @Index @Load Ref<Usuario> usuario;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private @Load Ref<Endereco> endereco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ApiResourceProperty
    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

    @ApiResourceProperty
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDataAssociacao() {
        return dataAssociacao;
    }

    public void setDataAssociacao(Date dataAssociacao) {
        this.dataAssociacao = dataAssociacao;
    }


    public Rede getRede() {
        return rede.get();
    }

    public void setRede(Rede rede) {
        this.rede = Ref.create(rede);
    }


    public Usuario getUsuario() {
        return usuario.get();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = Ref.create(usuario);
    }

    @ApiResourceProperty
    public Visibilidade getVisibilidadeEmail() {
        return visibilidadeEmail;
    }

    public void setVisibilidadeEmail(Visibilidade visibilidadeEmail) {
        this.visibilidadeEmail = visibilidadeEmail;
    }
    @ApiResourceProperty
    public Visibilidade getVisibilidadeEndereco() {
        return visibilidadeEndereco;
    }

    public void setVisibilidadeEndereco(Visibilidade visibilidadeEndereco) {
        this.visibilidadeEndereco = visibilidadeEndereco;
    }

    @ApiResourceProperty
    public Visibilidade getVisibilidadeTelefoneFixo() {
        return visibilidadeTelefoneFixo;
    }

    public void setVisibilidadeTelefoneFixo(Visibilidade visibilidadeTelefoneFixo) {
        this.visibilidadeTelefoneFixo = visibilidadeTelefoneFixo;
    }

    @ApiResourceProperty
    public Visibilidade getVisibilidadeTelefoneCelular() {
        return visibilidadeTelefoneCelular;
    }

    public void setVisibilidadeTelefoneCelular(Visibilidade visibilidadeTelefoneCelular) {
        this.visibilidadeTelefoneCelular = visibilidadeTelefoneCelular;
    }


    public Endereco getEndereco() {
        return endereco.get();
    }

    public Long getRedeId() {
        return rede.get().getId();
    }
    public String getUsuarioId() {
        return usuario.get().getId();
    }


    public Long getEnderecoId() {
        return endereco.get().getId();
    }


    public void setEndereco(Endereco endereco) {
        this.endereco = Ref.create(endereco);
    }




    public static Membro aprovarAssociacao(Long id) throws NotFoundException, ForbiddenException {

        Membro m = mudarStatusAssociacao(id,Status.ATIVO);
        m.getUsuario().add(m);
        SearchService.createDocument(m);
        return m;

    }


    public static Membro reprovarAssociacao(Long id) throws NotFoundException, ForbiddenException {
        return mudarStatusAssociacao(id,Status.REPROVADO);
    }

    public static Membro  tornarAdministrador(Long id) throws NotFoundException, ForbiddenException {
        return mudarPapelAssociacao(id, Papel.ADMIN);

    }


    public static Membro retirarPermissaoAdministrador(Long id) throws NotFoundException, ForbiddenException {
        return mudarPapelAssociacao(id, Papel.VIVIZINHO);
    }

    public static Membro inativarVizinho(Long id) throws NotFoundException, ForbiddenException {
        Membro m = mudarStatusAssociacao(id,Status.INATIVO);
        SearchService.removeDocument(m);
        return m;
    }


    public static Membro ativarVizinho(Long id) throws NotFoundException, ForbiddenException {
        Membro m = mudarStatusAssociacao(id,Status.ATIVO);
        SearchService.createDocument(m);
        return m;

    }

    public static Membro tornarAutoridade(Long id) throws NotFoundException, ForbiddenException {

        return mudarPapelAssociacao(id, Papel.AUTORIDADE);
    }

    public static Membro retirarPermissaoAutoridade(Long id) throws NotFoundException, ForbiddenException {
        return mudarPapelAssociacao(id, Papel.VIVIZINHO);
    }

    private static Membro mudarStatusAssociacao(Long id, Status status) throws NotFoundException, ForbiddenException {
        Objectify ofy = OfyService.ofy();
        Membro m = ofy.load().type(Membro.class).id(id).now();
        if(m==null){
            throw new NotFoundException("Membro " + id  + " não encontrado");
        }

        if(m.getPapel() == Papel.CRIADOR){
            throw new ForbiddenException("Não é possível mudar o status do criador da rede");
        }

        m.setStatus(status);
        ofy.save().entity(m).now();
        return m;
    }

    private static Membro mudarPapelAssociacao(Long id, Papel papel) throws NotFoundException, ForbiddenException {
        Objectify ofy = OfyService.ofy();
        Membro m = ofy.load().type(Membro.class).id(id).now();
        if(m==null){
            throw new NotFoundException("Membro " + id  + " não encontrado");
        }

        if(m.getPapel() == Papel.CRIADOR){
            throw new ForbiddenException("Não é possível mudar o papel do criador da rede");
        }


        m.setPapel(papel);
        ofy.save().entity(m).now();
        return m;
    }



}
