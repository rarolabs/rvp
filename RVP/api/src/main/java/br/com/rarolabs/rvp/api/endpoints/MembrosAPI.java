/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package br.com.rarolabs.rvp.api.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;


import javax.inject.Named;


import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Rede;

/**
 * An endpoint class we are exposing
 */
@Api(name = "rvpAPI", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.rvp.rarolabs.com.br", ownerName = "api.rvp.rarolabs.com.br", packagePath = ""))
public class MembrosAPI {


    @ApiMethod(name = "solicitarAssociacao")
    public void solicitarAssociacao(@Named("rede_id") Long redeId,
                             @Named("usuario_id") Long usuarioId, Endereco endereco) throws NotFoundException {
        Rede.solicitarAssociacao(redeId,usuarioId,endereco);
    }

    @ApiMethod(name = "aprovarAssociacao")
    public void aprovarAssociacao(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.aprovarAssociacao(membroId);
    }

    @ApiMethod(name = "reprovarAssociacao")
    public void reprovarAssociacao(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.reprovarAssociacao(membroId);
    }

    @ApiMethod(name = "tornarAdministrador")
    public void tornarAdministrador(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.tornarAdministrador(membroId);
    }

    @ApiMethod(name = "retirarPermissaoAdministrador")
    public void retirarPermissaoAdministrador(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.retirarPermissaoAdministrador(membroId);
    }

    @ApiMethod(name = "inativarVizinho")
    public void inativarVizinho(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.inativarVizinho(membroId);
    }

    @ApiMethod(name = "ativarVizinho")
    public void ativarVizinho(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.ativarVizinho(membroId);
    }

    @ApiMethod(name = "tornarAutoridade")
    public void tornarAutoridade(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.tornarAutoridade(membroId);
    }

    @ApiMethod(name = "retirarPermissaoAutoridade")
    public void retirarPermissaoAutoridade(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.retirarPermissaoAutoridade(membroId);
    }
}
