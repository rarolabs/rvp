package br.com.rarolabs.rvp.api.models;


import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import br.com.rarolabs.rvp.api.service.OfyService;
import br.com.rarolabs.rvp.api.service.SearchService;


@Entity
public class Rede {

    @Id
    private Long id;

    @Index
    private String nome;

    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private @Load List<Ref<Membro>> membros = new ArrayList<Ref<Membro>>();

    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private @Load Ref<Membro> dono;
    private @Index Long donoId;


    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private @Load List<Ref<Alerta>> alertas = new ArrayList<Ref<Alerta>>();

    private String localizacao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void addMembro(Membro membro){
        this.membros.add(Ref.create(membro));
    }


    public Membro getDono() {
        return OfyService.ofy().load().type(Membro.class).id(donoId).now();
    }

    public void setDono(Membro dono) {
        Ref<Membro> ref = Ref.create(dono);
        this.dono = ref;
        this.membros.add(ref);
        this.donoId = dono.getId();
    }

    @ApiResourceProperty
    public Double getLatitude(){
      return getDono().getEndereco().getLatitude();
    }

    @ApiResourceProperty
    public Double getLongitude(){
      return getDono().getEndereco().getLongitude();
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public static Rede novaRede(final String nome, final String usuarioId, final Endereco endereco, Membro.Visibilidade visibilidadeFixo, Membro.Visibilidade visibilidadeCel, Membro.Visibilidade visibilidadeEndereco) throws ConflictException {
        final Objectify ofy = OfyService.ofy();
        if(ofy.load().type(Rede.class).filter("nome", nome).first().now() != null){
            throw new ConflictException("Já existe uma rede com o nome:" + nome);
        }

        final Usuario u = ofy.load().type(Usuario.class).id(usuarioId).now();

        if(endereco.getId() == null){
            endereco.setUsuario(u);
            ofy.save().entity(endereco).now();
        }


        final Rede rede = new Rede();
        final Membro m = new Membro();

        m.setPapel(Membro.Papel.CRIADOR);
        m.setStatus(Membro.Status.ATIVO);
        m.setVisibilidadeEndereco(visibilidadeEndereco);
        m.setVisibilidadeEmail(Membro.Visibilidade.PUBLICO);
        m.setVisibilidadeTelefoneFixo(visibilidadeFixo);
        m.setVisibilidadeTelefoneCelular(visibilidadeCel);


        ofy.transact(new VoidWork() {
            @Override
            public void vrun() {
                m.setUsuario(u);
                m.setEndereco(endereco);
                ofy.save().entity(m).now();
                u.add(m);
                rede.setNome(nome);
                rede.setDono(m);


            }

        });

        ofy.save().entity(rede).now();
        m.setRede(rede);
        ofy.save().entity(m).now();
        SearchService.createDocument(m);

        Queue q = QueueFactory.getDefaultQueue();
        q.add(TaskOptions.Builder.withUrl("/atualiza_endereco").param("key", rede.getId().toString()));


        return rede;
    }

    public static Membro solicitarAssociacao(Long id, String usuarioId, final Endereco endereco, Membro.Visibilidade visibilidadeFixo, Membro.Visibilidade visibilidadeCel, Membro.Visibilidade visibilidadeEndereco) throws NotFoundException, ConflictException {



        final Objectify ofy = OfyService.ofy();
        final Rede rede = ofy.load().type(Rede.class).id(id).now();
        if(rede == null){
            throw new NotFoundException("Rede " + id + " não encontrada");
        }

        Usuario usuario = ofy.load().type(Usuario.class).id(usuarioId).now();
        if(usuario == null){
            throw new NotFoundException("Usuario " + usuarioId + " não encontrado");
        }

        if(solicitacaoRepetida(rede,usuario)){
            throw new ConflictException("Já existe um pedido de associacao para esse usuario");
        }

        if(endereco.getId() == null){
            ofy.save().entity(endereco).now();
        }

        final Membro m = Membro.novoOuInativo(id,usuarioId);
        if(m.getStatus().equals(Membro.Status.ATIVO)){
            throw new ConflictException("Esse usuário já foi adicionado");
        }


        m.setRede(rede);
        m.setUsuario(usuario);
        m.setEndereco(endereco);
        m.setStatus(Membro.Status.AGUARDANDO_APROVACAO);
        m.setPapel(Membro.Papel.VIVIZINHO);
        m.setEndereco(endereco);

        System.out.println("VisibilidadeEnd:" + visibilidadeEndereco);
        System.out.println("VisibilidadeFixo:" + visibilidadeFixo);
        System.out.println("VisibilidadeCel:" + visibilidadeCel);

        m.setVisibilidadeEndereco(visibilidadeEndereco);
        m.setVisibilidadeEmail(Membro.Visibilidade.PRIVADO);
        m.setVisibilidadeTelefoneCelular(visibilidadeCel);
        m.setVisibilidadeTelefoneFixo(visibilidadeFixo);

        ofy.transact(new VoidWork() {
            @Override
            public void vrun() {
                ofy.save().entity(m).now();
                ofy.save().entity(rede).now();
                m.setRede(rede);
                rede.addMembro(m);
                ofy.save().entity(rede);
                ofy.save().entity(m);
            }
        });

        return m;
    }

    private static boolean solicitacaoRepetida(final Rede rede, final Usuario usuario) {
        return Collections2.filter(rede.getMembros(), new com.google.common.base.Predicate<Membro>() {
            @Override
            public boolean apply(@Nullable Membro input) {
                return input.getUsuarioId() == usuario.getId();
            }
        }).size() != 0;

    }


    public static Collection<Membro> solicitacoesPendentes(Long redeId) {
        Objectify ofy = OfyService.ofy();
        Rede r = ofy.load().type(Rede.class).id(redeId).now();
        return r.solicitacoesPendentes();
    }


    public Collection<Membro> solicitacoesPendentes() {
        return Collections2.filter(getMembros(), new com.google.common.base.Predicate<Membro>() {
            @Override
            public boolean apply(@Nullable Membro input) {
                return (input.getStatus() == Membro.Status.AGUARDANDO_APROVACAO);

            }
        });
    }

    public static Collection<Membro> membrosAtivos(Long redeId) {
        Objectify ofy = OfyService.ofy();
        Rede r = ofy.load().type(Rede.class).id(redeId).now();
        return r.membrosAtivos();
    }

    public static Collection<Membro> membrosAdministradores(Long redeId) {
        Objectify ofy = OfyService.ofy();
        Rede r = ofy.load().type(Rede.class).id(redeId).now();
        return r.membrosAdministradores();
    }



    public Collection<Membro> membrosAtivos() {
        Collection<Membro> membros =  getMembros();
        return Rede.filtrarMembrosAtivos(membros);
    }

    public Collection<Membro> membrosAdministradores() {
        Collection<Membro> membros =  getMembros();
        return Rede.filtrarMembrosAdministradores(membros);
    }




    public static Rede buscar(Long id) throws NotFoundException {
        Objectify ofy = OfyService.ofy();
        Rede u = ofy.load().type(Rede.class).id(id).now();
        if(u==null){
            throw new NotFoundException("Rede: " + id + " não encontrado");
        }
        return u;

    }


    @Override
    public String toString() {
        return "Rede{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", membros=" + membros +
                ", dono=" + dono +
                ", alertas=" + alertas +
                '}';
    }

    public void apagar() throws OAuthRequestException {
        Objectify ofy = OfyService.ofy();
        ofy.delete().entities(getMembros()).now();
        ofy.delete().entity(this).now();
    }



    public static Collection<Membro> filtrarMembrosAdministradores(Collection<Membro> membros) {

        Collection<Membro> filteredMembros =  Collections2.filter(membros, new com.google.common.base.Predicate<Membro>() {
            @Override
            public boolean apply(@Nullable Membro input) {

                return input.getPapel().equals(Membro.Papel.ADMIN) ||
                        input.getPapel().equals(Membro.Papel.CRIADOR);
            }
        });

        return filteredMembros;

    }

    public static Collection<Membro> filtrarMembrosAtivos(Collection<Membro> membros) {

        Collection<Membro> filteredMembros =  Collections2.filter(membros, new com.google.common.base.Predicate<Membro>() {
            @Override
            public boolean apply(@Nullable Membro input) {

                return input.getStatus().equals(Membro.Status.ATIVO);
            }
        });

        return filteredMembros;

    }


    public static Collection<Membro> filtrarMinhasRedes(List<Membro> membros) {
        Collection<Membro> filteredMembros =  Collections2.filter(membros, new com.google.common.base.Predicate<Membro>() {
            @Override
            public boolean apply(@Nullable Membro input) {

                return input.getStatus().equals(Membro.Status.ATIVO) ||
                       input.getStatus().equals(Membro.Status.AGUARDANDO_APROVACAO);
            }
        });

        return filteredMembros;

    }
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Membro getMembroByUser(Usuario user) {

        return OfyService.ofy().load()
                               .type(Membro.class)
                               .filter("redeId",this.getId())
                               .filter("usuarioId",user.getEmail()).first().now();


    }
}
