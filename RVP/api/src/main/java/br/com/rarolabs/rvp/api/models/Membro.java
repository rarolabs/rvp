package br.com.rarolabs.rvp.api.models;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.com.rarolabs.rvp.api.responders.Profile;
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
    @Index
    private Status status = Status.INATIVO;

    private Date dataAssociacao;

    private Visibilidade visibilidadeEmail;
    private Visibilidade visibilidadeEndereco;
    private Visibilidade visibilidadeTelefoneFixo;
    private Visibilidade visibilidadeTelefoneCelular;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private @Load Ref<Rede> rede;

    private @Index Long redeId;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private @Index @Load Ref<Usuario> usuario;
    private @Index String usuarioId;

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


    @ApiResourceProperty
    public String getNomeRede() {
        return getRede().getNome();
    }

    @ApiResourceProperty
    public Double getLatitude(){
        return getEndereco().getLatitude();
    }

    @ApiResourceProperty
    public Double getLongitude(){
        return getEndereco().getLongitude();
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
        return OfyService.ofy().cache(false).load().ref(rede).now();
    }

    public void setRede(Rede rede) {
        this.rede = Ref.create(rede);
        this.redeId = rede.getId();
    }


    public Usuario getUsuario() {
        return OfyService.ofy().cache(false).load().type(Usuario.class).id(usuarioId).now();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = Ref.create(usuario);
        this.usuarioId = usuario.getId();
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
       return  endereco!=null ? endereco.get() : null;
    }

    public Long getRedeId() {
        return rede.get().getId();
    }
    public String getUsuarioId() {
        return usuarioId;
    }


    public Long getEnderecoId() {
        return endereco.get().getId();
    }


    public void setEndereco(Endereco endereco) {
        this.endereco = Ref.create(endereco);
    }

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public List<Dispositivo> getDispositivos() {
        return this.getUsuario().getDispositivos();
    }



    public static Membro aprovarAssociacao(Long id, Boolean tornarAdministrador, Boolean tornarAutoridade) throws NotFoundException, ForbiddenException {

        Membro m = mudarStatusAssociacao(id,Status.ATIVO);
        if(tornarAutoridade){
            m.setPapel(Papel.AUTORIDADE);
        }else if(tornarAdministrador){
            m.setPapel(Papel.ADMIN);
        }
        OfyService.ofy().save().entity(m);

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

    public static Membro deixarRede(Long membroId,String email) throws ForbiddenException, OAuthRequestException {
        Objectify ofy = OfyService.ofy();
        Membro m = ofy.load().type(Membro.class).id(membroId).now();

        if(!m.getUsuarioId().equals(email)){
            throw new ForbiddenException("Somente o próprio membro pode deixar a rede");
        }
        boolean excluirRede = false;
        if(m.getPapel() == Papel.CRIADOR || m.getPapel() == Papel.ADMIN){
            Collection<Membro> membros = m.getRede().getMembros();
            if(membros.size() > 1 &&
               Rede.filtrarMembrosAdministradores(membros).size() < 2){
                throw new ForbiddenException("Você deve nomear outro administrador antes de deixar a rede");
            }else{
                excluirRede = true;
            }

        }
        if(excluirRede){
           m.getRede().apagar();
        }else {
            m.setStatus(Status.INATIVO);
            ofy.save().entity(m).now();
        }
        return m;

    }

    public static Profile buscarMembro(Long membroId, String email) throws NotFoundException {
        Objectify ofy = OfyService.ofy();
        Membro m = ofy.load().type(Membro.class).id(membroId).now();
        Usuario u = ofy.load().type(Usuario.class).id(email).now();
        if(m==null){
            throw new NotFoundException("Vizinho não encontrado");
        }

        if(u==null){
            throw new NotFoundException("Usuário não cadastrado");
        }

        return ofuscateMembro(m,u);
    }

    private static Profile ofuscateMembro(Membro m, Usuario u) {

        System.out.println("Membro a ser ofuscado:" +m.getId());
        System.out.println("Usuario que esta vendo" + u.getId());
        System.out.println("Rede que esta sendo visualizada" + m.getRedeId());

        Membro membroQueEstavaVendo = u.minhaAssociacao(m.getRedeId());
        System.out.println("Voltou" + membroQueEstavaVendo);
        if(membroQueEstavaVendo==null){
            membroQueEstavaVendo = new Membro();
            membroQueEstavaVendo.setPapel(Papel.VIVIZINHO);
        }
        if(membroQueEstavaVendo.papel.equals(Papel.ADMIN) ||
           membroQueEstavaVendo.papel.equals(Papel.CRIADOR)){
            return createProfile(m,membroQueEstavaVendo);
        }else {
            return createProfile(m.ofuscaTelefoneCelular(membroQueEstavaVendo)
                    .ofuscaTelefoneFixo(membroQueEstavaVendo)
                    .ofuscaEndereco(membroQueEstavaVendo), membroQueEstavaVendo);
        }
    }

    private static Profile createProfile(Membro m, Membro membroQueEstavaVendo) {
        Profile p = new Profile();
        p.setMembroId(m.getId());

        Usuario dadosDoMembro = m.getUsuario();



        p.setUsuarioId(m.getUsuarioId());
        p.setNome(dadosDoMembro.getNome());
        if(m.getEndereco()!=null) {
            p.setEndereco(m.getEndereco().getDescricao());
        }
        p.setAvatar(dadosDoMembro.getAvatar());
        p.setAvatarBlur(dadosDoMembro.getAvatarBlur());
        p.setPapelDoVisualizado(membroQueEstavaVendo.getPapel());
        p.setTelefoneCelular(dadosDoMembro.getTelefoneCelularComDDD());
        p.setTelefoneFixo(dadosDoMembro.getTelefoneFixoComDDD());
        p.setPapel(m.getPapel());
        p.setStatus(m.getStatus());

        System.out.println(p);

        return p;
    }

    private Membro ofuscaEndereco(Membro membroQueEstavaVendo) {
        if (visibilidadeEndereco.equals(Visibilidade.PRIVADO) ||
           (visibilidadeEndereco.equals(Visibilidade.SOMENTE_COM_AUTORIDADE) && !membroQueEstavaVendo.papel.equals(Papel.AUTORIDADE))){
            this.endereco = null;
        }
        return this;
    }

    private Membro ofuscaTelefoneFixo(Membro membroQueEstavaVendo) {
        if (visibilidadeTelefoneFixo.equals(Visibilidade.PRIVADO) ||
                (visibilidadeTelefoneFixo.equals(Visibilidade.SOMENTE_COM_AUTORIDADE) && !membroQueEstavaVendo.papel.equals(Papel.AUTORIDADE))){
            this.getUsuario().setTelefoneFixo("");
            this.getUsuario().setDddTelefoneFixo("");
        }
        return this;
    }

    private Membro ofuscaTelefoneCelular(Membro membroQueEstavaVendo) {
        if (visibilidadeTelefoneCelular.equals(Visibilidade.PRIVADO) ||
                (visibilidadeTelefoneCelular.equals(Visibilidade.SOMENTE_COM_AUTORIDADE) && !membroQueEstavaVendo.papel.equals(Papel.AUTORIDADE))){
            this.getUsuario().setTelefoneCelular("");
            this.getUsuario().setDddTelefoneCelular("");
        }
        return this;

    }

    public static Membro novoOuInativo(Long redeId, String usuarioId) {
        Membro m = OfyService.ofy().load().type(Membro.class).filter("redeId",redeId).filter("usuarioId",usuarioId).first().now();
        if(m!=null){
            return m;
        }else{
            return new Membro();
        }
    }




    @Override
    public String toString() {
        return "Membro{" +
                "id=" + id +
                ", papel=" + papel +
                ", status=" + status +
                ", dataAssociacao=" + dataAssociacao +
                ", visibilidadeEmail=" + visibilidadeEmail +
                ", visibilidadeEndereco=" + visibilidadeEndereco +
                ", visibilidadeTelefoneFixo=" + visibilidadeTelefoneFixo +
                ", visibilidadeTelefoneCelular=" + visibilidadeTelefoneCelular +
                ", rede=" + rede +
                ", usuario=" + usuario +
                ", endereco=" + endereco +
                '}';
    }
}
