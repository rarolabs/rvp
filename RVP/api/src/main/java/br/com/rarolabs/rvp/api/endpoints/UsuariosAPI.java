package br.com.rarolabs.rvp.api.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.Objectify;

import javax.inject.Named;

import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Usuario;
import br.com.rarolabs.rvp.api.service.OfyService;

/**
 * Created by rodrigosol on 12/30/14.
 */
@Api(name = "rvpAPI", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.rvp.rarolabs.com.br", ownerName = "api.rvp.rarolabs.com.br", packagePath = ""))
public class UsuariosAPI {

    @ApiMethod(name = "novoUsuario")
    public Usuario novoUsuario(Usuario usuario) throws ConflictException {
        return Usuario.novoUsuario(usuario);
    }

    @ApiMethod(name = "buscarUsuario")
    public Usuario buscarUsuario(@Named("id") Long id) throws NotFoundException {
        Objectify ofy = OfyService.ofy();
        Usuario u = ofy.load().type(Usuario.class).id(id).now();
        if(u==null){
            throw new NotFoundException("Usuário: " + id + " não encontrado");
        }
        return u;
    }

    @ApiMethod(name ="removerUsuario")
    public void removerUsuario(@Named("id") Long id){
        Objectify ofy = OfyService.ofy();
        ofy.delete().type(Usuario.class).id(id).now();
    }

    @ApiMethod(name = "salvarEndereco")
    public void salvarEndereco(Endereco endereco){
        Endereco.salvar(endereco);
    }


}
