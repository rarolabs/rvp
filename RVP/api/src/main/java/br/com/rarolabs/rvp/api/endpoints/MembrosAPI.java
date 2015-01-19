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
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;


import javax.inject.Named;


import br.com.rarolabs.rvp.api.auth.Constants;
import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;

/**
 * API - Rede de Vizinho Protegidos
 * Endpoints para gestão dos membros
 */
@Api(name = "rvpAPI", version = "v1",
     namespace = @ApiNamespace(ownerDomain = "api.rvp.rarolabs.com.br",
                               ownerName = "api.rvp.rarolabs.com.br", packagePath = ""),
        scopes = {Constants.PROFILE_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE}
)

public class MembrosAPI {

    /**
     * Solicita a associacao de um usuário a uma rede. A solicitacao
     * ficará pendente até quem um administrador aprove ou rejeite
     * @param redeId ID da Rede
     * @param endereco Endereco do usuário para essa rede
     * @return Membro
     * @throws NotFoundException Pode ser retornada caso a rede e/ou usuário não existam
     * @throws ConflictException Pode ser retornada caso já exista um pedido de associacao nessa
     * rede para o mesmo usuario
     */
    @ApiMethod(name = "solicitarAssociacao")
    public Membro solicitarAssociacao(@Named("rede_id") Long redeId,
                                      @Named("visibilidadeFixo") Membro.Visibilidade visibilidadeFixo,
                                      @Named("visibilidadeCel") Membro.Visibilidade visibilidadeCel,
                                      @Named("visibilidadeEndereco") Membro.Visibilidade visibilidadeEndereco,
                                      Endereco endereco, User user) throws OAuthRequestException, NotFoundException, ConflictException {
        if(user==null){
            throw new OAuthRequestException("Usuário não autenticado");
        }
        return Rede.solicitarAssociacao(redeId,user.getEmail(),endereco,visibilidadeFixo,visibilidadeCel,visibilidadeEndereco);
    }

    /**
     * Aprova o pedido de associacao de um usuário
     * @param membroId ID do membro
     * @return Membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "aprovarAssociacao")
    public Membro aprovarAssociacao(@Named("membro_id") Long membroId,User user) throws NotFoundException, ForbiddenException, OAuthRequestException {
        if(user==null){
            throw new OAuthRequestException("Usuário não autenticado");
        }

        return Membro.aprovarAssociacao(membroId);
    }

    /**
     * Reprova o pedido de associação de um usuário
     * @param membroId id do Membro
     * @return Membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "reprovarAssociacao")
    public Membro reprovarAssociacao(@Named("membro_id") Long membroId,User user) throws NotFoundException, ForbiddenException, OAuthRequestException {
        if(user==null){
            throw new OAuthRequestException("Usuário não autenticado");
        }
        return Membro.reprovarAssociacao(membroId);
    }

    /**
     * Torna um usuário administrador da rede
     * @param membroId id do Membro
     * @return Membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "tornarAdministrador")
    public Membro tornarAdministrador(@Named("membro_id") Long membroId,User user) throws NotFoundException, ForbiddenException, OAuthRequestException {
        if(user==null){
            throw new OAuthRequestException("Usuário não autenticado");
        }
        return Membro.tornarAdministrador(membroId);
    }

    /**
     * Retirna a permissão de um usuário da rede
     * @param membroId id do Membro
     * @return Membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "retirarPermissaoAdministrador")
    public Membro retirarPermissaoAdministrador(@Named("membro_id") Long membroId,User user) throws NotFoundException, ForbiddenException, OAuthRequestException {
        if(user==null){
            throw new OAuthRequestException("Usuário não autenticado");
        }
        return Membro.retirarPermissaoAdministrador(membroId);
    }

    /**
     * Inativa um usuário da rede
     * @param membroId id do Membro
     * @return Membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "inativarVizinho")
    public Membro inativarVizinho(@Named("membro_id") Long membroId,User user) throws NotFoundException, ForbiddenException, OAuthRequestException {
        if(user==null){
            throw new OAuthRequestException("Usuário não autenticado");
        }
        return Membro.inativarVizinho(membroId);
    }

    /**
     * Ativa um usuário inativo da rede
     * @param membroId id do Membro
     * @return Membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "ativarVizinho")
    public Membro ativarVizinho(@Named("membro_id") Long membroId,User user) throws NotFoundException, ForbiddenException, OAuthRequestException {
        if(user==null){
            throw new OAuthRequestException("Usuário não autenticado");
        }
        return Membro.ativarVizinho(membroId);
    }

    /**
     * Torna um usuário a autoridade policial da rede
     * @param membroId id do Membro
     * @return Membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "tornarAutoridade")
    public Membro tornarAutoridade(@Named("membro_id") Long membroId,User user) throws NotFoundException, ForbiddenException, OAuthRequestException {
        if(user==null){
            throw new OAuthRequestException("Usuário não autenticado");
        }
        return Membro.tornarAutoridade(membroId);
    }

    /**
     * Retira a permissão de um usuário na rede
     * @param membroId id do Membro
     * @return Membro
     * @throws NotFoundException Pode ser lançada caso alguma entidade não exista
     * @throws ForbiddenException Pode ser lançada casa a operacao não seja permitida
     */
    @ApiMethod(name = "retirarPermissaoAutoridade")
    public Membro retirarPermissaoAutoridade(@Named("membro_id") Long membroId,User user) throws NotFoundException, ForbiddenException, OAuthRequestException {
        if(user==null){
            throw new OAuthRequestException("Usuário não autenticado");
        }

       return Membro.retirarPermissaoAutoridade(membroId);
    }
}
