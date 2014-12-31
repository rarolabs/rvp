/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package br.com.rarolabs.rvp.api.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;


import javax.inject.Named;


import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Rede;

/**
 * API - Rede de Vizinho Protegidos
 * Endpoints para gestão dos membros
 */
@Api(name = "rvpAPI", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.rvp.rarolabs.com.br", ownerName = "api.rvp.rarolabs.com.br", packagePath = ""))
public class MembrosAPI {

    /**
     * Solicita a associacao de um usuário a uma rede. A solicitacao
     * ficará pendente até quem um administrador aprove ou rejeite
     * @param redeId ID da Rede
     * @param usuarioId ID do Usuário
     * @param endereco Endereco do usuário para essa rede
     * @throws NotFoundException Pode ser retornada caso a rede e/ou usuário não existam
     * @throws ConflictException Pode ser retornada caso já exista um pedido de associacao nessa
     * rede para o mesmo usuario
     */
    @ApiMethod(name = "solicitarAssociacao")
    public void solicitarAssociacao(@Named("rede_id") Long redeId,
                             @Named("usuario_id") Long usuarioId, Endereco endereco) throws NotFoundException, ConflictException {
        Rede.solicitarAssociacao(redeId,usuarioId,endereco);
    }

    /**
     * Aprova o pedido de associacao de um usuário
     * @param membroId ID do membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "aprovarAssociacao")
    public void aprovarAssociacao(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.aprovarAssociacao(membroId);
    }

    /**
     * Reprova o pedido de associação de um usuário
     * @param membroId id do Membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "reprovarAssociacao")
    public void reprovarAssociacao(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.reprovarAssociacao(membroId);
    }

    /**
     * Torna um usuário administrador da rede
     * @param membroId id do Membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "tornarAdministrador")
    public void tornarAdministrador(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.tornarAdministrador(membroId);
    }

    /**
     * Retirna a permissão de um usuário da rede
     * @param membroId id do Membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "retirarPermissaoAdministrador")
    public void retirarPermissaoAdministrador(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.retirarPermissaoAdministrador(membroId);
    }

    /**
     * Inativa um usuário da rede
     * @param membroId id do Membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "inativarVizinho")
    public void inativarVizinho(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.inativarVizinho(membroId);
    }

    /**
     * Ativa um usuário inativo da rede
     * @param membroId id do Membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "ativarVizinho")
    public void ativarVizinho(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.ativarVizinho(membroId);
    }

    /**
     * Torna um usuário a autoridade policial da rede
     * @param membroId id do Membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "tornarAutoridade")
    public void tornarAutoridade(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.tornarAutoridade(membroId);
    }

    /**
     * Retira a permissão de um usuário na rede
     * @param membroId id do Membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "retirarPermissaoAutoridade")
    public void retirarPermissaoAutoridade(@Named("membro_id") Long membroId) throws NotFoundException, ForbiddenException {
        Membro.retirarPermissaoAutoridade(membroId);
    }
}
