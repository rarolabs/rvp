package br.com.rarolabs.rvp.api.models;


import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.api.server.spi.response.ConflictException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    private List<Ref<Membro>> membros = new ArrayList<Ref<Membro>>();

    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<Membro> dono;


    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private List<Ref<Alerta>> alertas = new ArrayList<Ref<Alerta>>();


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

    public Double getLatitude(){
      return getDono().getEndereco().getLatitude();
    }

    public Double getLongitude(){
      return getDono().getEndereco().getLongitude();
    }


    public static Rede novaRede(final String nome, final Long usuarioId,  final Endereco endereco) throws ConflictException {
        final Objectify ofy = OfyService.ofy();
        if(ofy.load().type(Rede.class).filter("nome", nome).first().now() != null){
            throw new ConflictException("Já existe uma rede com o nome:" + nome);
        }

        final Usuario u = ofy.load().type(Usuario.class).id(usuarioId).now();

        if(endereco.getId() == null){
            endereco.setUsuario(u);
            ofy.save().entity(endereco).now();
        }

        final Visibilidade v =  new Visibilidade();
        v.setEndereco(Visibilidade.Tipo.PUBLICO);
        v.setEmail(Visibilidade.Tipo.PUBLICO);
        v.setTelefoneFixo(Visibilidade.Tipo.PUBLICO);
        v.setTelefoneCelular(Visibilidade.Tipo.PUBLICO);

        final Rede rede = new Rede();
        final Membro m = new Membro();

        ofy.transact(new VoidWork() {
            @Override
            public void vrun() {
                ofy.save().entity(v).now();
                m.setUsuario(u);
                m.setVisibilidade(v);
                m.setEndereco(endereco);

                ofy.save().entity(m).now();
                rede.setNome(nome);
                rede.setDono(m);


            }

        });

        ofy.save().entity(rede).now();
        m.setRede(rede);

        SearchService.createDocument(m);

        return rede;
    }
}
