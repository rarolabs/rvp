package br.com.rarolabs.rvp.api.models;


import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
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


    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private @Load List<Ref<Alerta>> alertas = new ArrayList<Ref<Alerta>>();


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
        return dono.get();
    }

    public void setDono(Membro dono) {
        Ref<Membro> ref = Ref.create(dono);
        this.dono = ref;
        this.membros.add(ref);
    }

    @ApiResourceProperty
    public Double getLatitude(){
      return getDono().getEndereco().getLatitude();
    }

    @ApiResourceProperty
    public Double getLongitude(){
      return getDono().getEndereco().getLongitude();
    }


    public static Rede novaRede(final String nome, final String usuarioId,  final Endereco endereco) throws ConflictException {
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
        m.setVisibilidadeEndereco(Membro.Visibilidade.PUBLICO);
        m.setVisibilidadeEmail(Membro.Visibilidade.PUBLICO);
        m.setVisibilidadeTelefoneFixo(Membro.Visibilidade.PUBLICO);
        m.setVisibilidadeTelefoneCelular(Membro.Visibilidade.PUBLICO);


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

        SearchService.createDocument(m);

        return rede;
    }

    public static Membro solicitarAssociacao(Long id, String usuarioId, final Endereco endereco) throws NotFoundException, ConflictException {



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

        final Membro m = new Membro();
        m.setRede(rede);
        m.setUsuario(usuario);
        m.setEndereco(endereco);
        m.setStatus(Membro.Status.AGUARDANDO_APROVACAO);
        m.setPapel(Membro.Papel.VIVIZINHO);
        m.setEndereco(endereco);

        m.setVisibilidadeEndereco(Membro.Visibilidade.COM_AUTORIDADE_E_ADMINISTRADOR);
        m.setVisibilidadeEmail(Membro.Visibilidade.COM_AUTORIDADE_E_ADMINISTRADOR);
        m.setVisibilidadeTelefoneCelular(Membro.Visibilidade.COM_AUTORIDADE_E_ADMINISTRADOR);
        m.setVisibilidadeTelefoneFixo(Membro.Visibilidade.COM_AUTORIDADE_E_ADMINISTRADOR);

        ofy.transact(new VoidWork() {
            @Override
            public void vrun() {
                ofy.save().entity(m).now();
                ofy.save().entity(rede).now();
                m.setRede(rede);
                rede.addMembro(m);
            }
        });

        return m;
    }

    private static boolean solicitacaoRepetida(final Rede rede, final Usuario usuario) {
        return Collections2.filter(rede.getMembros(), new com.google.common.base.Predicate<Membro>() {
            @Override
            public boolean apply(@Nullable Membro input) {
                return input.getUsuario().getId() == usuario.getId();
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


    public Collection<Membro> membrosAtivos() {
        return Collections2.filter(getMembros(), new com.google.common.base.Predicate<Membro>() {
            @Override
            public boolean apply(@Nullable Membro input) {
                return input.getStatus() == Membro.Status.ATIVO;
            }
        });
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

    public static void apagar(Long id,String userId) throws OAuthRequestException {
        Objectify ofy = OfyService.ofy();
        Usuario u = ofy.load().type(Usuario.class).id(userId).now();
        Rede r = ofy.load().type(Rede.class).id(id).now();
        if(podeApagar(r,u)) {
            ofy.delete().type(Rede.class).id(id).now();
        }else{
            throw new OAuthRequestException("O usuário " + u.getEmail() + " não tem permissão para apagar essa rede");
        }
    }

    private static boolean podeApagar(Rede r, Usuario u) throws OAuthRequestException {

        return r.getDono().getUsuario().getId() == u.getId();
    }
}
