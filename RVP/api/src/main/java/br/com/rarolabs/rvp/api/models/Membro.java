package br.com.rarolabs.rvp.api.models;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
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

    private Papel papel = Papel.VIVIZINHO;
    private Status status = Status.ATIVO;

    private Date dataAssociacao;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private @Load Ref<Rede> rede;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private @Load Ref<Usuario> usuario;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private @Load Ref<Visibilidade> visibilidade;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private @Load Ref<Endereco> endereco;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

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

    public Visibilidade getVisibilidade() {
        return visibilidade.get();
    }

    public void setVisibilidade(Visibilidade visibilidade) {
        this.visibilidade = Ref.create(visibilidade);
    }

    public Endereco getEndereco() {
        return endereco.get();
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = Ref.create(endereco);
    }

    public static void aprovarAssociacao(Long id) throws NotFoundException, ForbiddenException {
        mudarStatusAssociacao(id,Status.ATIVO);
    }

    public static void reprovarAssociacao(Long id) throws NotFoundException, ForbiddenException {
        mudarStatusAssociacao(id,Status.REPROVADO);
    }

    public static void tornarAdministrador(Long id) throws NotFoundException, ForbiddenException {
        mudarPapelAssociacao(id, Papel.ADMIN);

    }


    public static void retirarPermissaoAdministrador(Long id) throws NotFoundException, ForbiddenException {
        mudarPapelAssociacao(id, Papel.VIVIZINHO);
    }

    public static void inativarVizinho(Long id) throws NotFoundException, ForbiddenException {
        Membro m = mudarStatusAssociacao(id,Status.INATIVO);
        SearchService.removeDocument(m);
    }


    public static void ativarVizinho(Long id) throws NotFoundException, ForbiddenException {
        Membro m = mudarStatusAssociacao(id,Status.ATIVO);
        SearchService.createDocument(m);

    }

    public static void tornarAutoridade(Long id) throws NotFoundException, ForbiddenException {
        mudarPapelAssociacao(id, Papel.AUTORIDADE);
    }

    public static void retirarPermissaoAutoridade(Long id) throws NotFoundException, ForbiddenException {
        mudarPapelAssociacao(id, Papel.VIVIZINHO);
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
