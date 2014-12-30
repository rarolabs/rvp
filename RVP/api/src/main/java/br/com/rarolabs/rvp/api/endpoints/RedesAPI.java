package br.com.rarolabs.rvp.api.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.Objectify;

import java.util.Collection;
import java.util.List;

import javax.inject.Named;

import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;
import br.com.rarolabs.rvp.api.responders.GeoqueryResponder;
import br.com.rarolabs.rvp.api.service.OfyService;
import br.com.rarolabs.rvp.api.service.SearchService;

/**
 * Created by rodrigosol on 12/30/14.
 */
@Api(name = "rvpAPI", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.rvp.rarolabs.com.br", ownerName = "api.rvp.rarolabs.com.br", packagePath = ""))
public class RedesAPI {

    @ApiMethod(name ="novaRede")
    public Rede novaRede(
            @Named("nome") String nome,
            @Named("usuario_id") Long usuarioId,
            Endereco endereco) throws ConflictException {


        return Rede.novaRede(nome,usuarioId,endereco);
    }

    @ApiMethod(name ="buscarRede")
    public  Rede buscarRede(@Named("id") Long id) throws ConflictException, NotFoundException {

        Objectify ofy = OfyService.ofy();
        Rede u = ofy.load().type(Rede.class).id(id).now();
        if(u==null){
            throw new NotFoundException("Rede: " + id + " não encontrado");
        }
        return u;
    }

    @ApiMethod(name ="apagarRede")
    public void apagarRede(@Named("id") Long id){
        Objectify ofy = OfyService.ofy();
        ofy.delete().type(Rede.class).id(id).now();
    }

    @ApiMethod(name = "buscarRedesProximas")
    public List<GeoqueryResponder> buscarRedesProximas(@Named("latitude") Double latitude,
                                                       @Named("longitude") Double longitude,
                                                       @Named("distancia") Double distancia){

        return SearchService.searchByPosition(latitude, longitude, distancia);

    }

    @ApiMethod(name = "minhasRedes")
    public Collection<Membro> minhasRedes(@Named("usuario_id") Long usuarioId){
        return Usuario.minhasRedes(usuarioId);
    }

    @ApiMethod(name = "solicitacoesPendentes")
    public Collection<Membro> solicitacoesPendentes(@Named("rede_id") Long redeId){
        return Rede.solicitacoesPendentes(redeId);
    }

}
