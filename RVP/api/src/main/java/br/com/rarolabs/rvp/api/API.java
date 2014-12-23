/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package br.com.rarolabs.rvp.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;


import java.util.List;

import javax.inject.Named;

import javax.persistence.EntityManager;


import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;
import br.com.rarolabs.rvp.api.responders.GeoqueryResponder;
import br.com.rarolabs.rvp.api.service.OfyService;
import br.com.rarolabs.rvp.api.service.SearchService;

/**
 * An endpoint class we are exposing
 */
@Api(name = "rvpAPI", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.rvp.rarolabs.com.br", ownerName = "api.rvp.rarolabs.com.br", packagePath = ""))
public class API {


    @ApiMethod(name = "createUsuario")
    public Usuario createUsuario(Usuario usuario) throws ConflictException {

        Objectify ofy = OfyService.ofy();
        if(ofy.load().type(Usuario.class).filter("email", usuario.getEmail()).first().now() != null){
            throw new ConflictException("e-mail já cadastrado");
        }
        ofy.save().entity(usuario).now();
        return usuario;
    }

    @ApiMethod(name = "getUsuario")
    public Usuario getUsuario(@Named("id") Long id) throws NotFoundException {
        Objectify ofy = OfyService.ofy();
        Usuario u = ofy.load().type(Usuario.class).id(id).now();
        if(u==null){
            throw new NotFoundException("Usuário: " + id + " não encontrado");
        }
        return u;
    }

    @ApiMethod(name ="deleteUsuario")
    public void deleteUsuario(@Named("id") Long id){
        Objectify ofy = OfyService.ofy();
        ofy.delete().type(Usuario.class).id(id).now();
    }


    @ApiMethod(name ="createRede")
    public  Rede createRede(Rede rede) throws ConflictException {

        Objectify ofy = OfyService.ofy();

        if(ofy.load().type(Usuario.class).filter("nome", rede.getNome()).first().now() != null){
            throw new ConflictException("Já existe uma rede com o nome:" + rede.getNome());
        }

        ofy.save().entity(rede).now();

        return rede;
    }

    @ApiMethod(name ="getRede")
    public  Rede getRede(@Named("id") Long id) throws ConflictException, NotFoundException {

        Objectify ofy = OfyService.ofy();
        Rede u = ofy.load().type(Rede.class).id(id).now();
        if(u==null){
            throw new NotFoundException("Rede: " + id + " não encontrado");
        }
        return u;
    }

    @ApiMethod(name ="deleteRede")
    public void deleteRede(@Named("id") Long id){
        Objectify ofy = OfyService.ofy();
        ofy.delete().type(Rede.class).id(id).now();
    }


    @ApiMethod(name = "solicitarAssociacao")
    public void solicitarAssociacao(@Named("rede_id") Long redeId,
                             @Named("usuario_id") Long usuarioId, Endereco endereco) throws NotFoundException {

        Objectify ofy = OfyService.ofy();
        Rede rede = ofy.load().type(Rede.class).id(redeId).now();
        if(rede == null){
            throw new NotFoundException("Rede " + redeId + " não encontrada");
        }

        Usuario usuario = ofy.load().type(Usuario.class).id(usuarioId).now();
        if(usuario == null){
            throw new NotFoundException("Usuario " + usuarioId + " não encontrado");
        }

        if(endereco.getId() == null){
            ofy.save().entity(endereco).now();
        }

        Membro m = new Membro();
        m.setRede(rede);
        m.setUsuario(usuario);
        m.setEndereco(endereco);

        ofy.save().entity(m).now();

        SearchService.createDocument(m);

    }

    @ApiMethod(name = "getRedesByDistance")
    public List<GeoqueryResponder> getRedesByDistance(@Named("latitude") Double latitude,
                                                  @Named("longitude") Double longitude,
                                                  @Named("distancia") Double distancia){

        return SearchService.searchByPosition(latitude,longitude,distancia);

    }




}
