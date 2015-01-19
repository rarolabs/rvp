package br.com.rarolabs.rvp.api.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;


import javax.inject.Named;

import br.com.rarolabs.rvp.api.auth.Constants;
import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Usuario;

@Api(name = "rvpAPI", version = "v1",
        namespace = @ApiNamespace(ownerDomain = "api.rvp.rarolabs.com.br",
                ownerName = "api.rvp.rarolabs.com.br", packagePath = ""),
        scopes = {Constants.PROFILE_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE}
)
public class UsuariosAPI {

    /**
     * Cria um novo usuário
     * @param usuario Dados do usuário a ser criado
     * @return O usuário criado
     * @throws ConflictException Caso o e-mail informado já esteja em uso
     */
    @ApiMethod(name = "novoUsuario")
    public Usuario novoUsuario(Usuario usuario,User user) throws ConflictException, OAuthRequestException {
        if(user==null){
            throw new OAuthRequestException("Usuário não autenticado");
        }

        usuario.setId(user.getEmail());
        return Usuario.novoUsuario(usuario);
    }

    /**
     * Busca um usuário pelo ID
     * @param usuarioId Usuario autenticado
     * @return Usuario encontrado
     * @throws NotFoundException caso o usuário não seja encontrado
     */
    @ApiMethod(name = "buscarUsuario")
    public Usuario buscarUsuario(@Named("id_usuario") String usuarioId,User user) throws NotFoundException, OAuthRequestException {
        if(user==null){
            throw new OAuthRequestException("Usuário não autenticado");
        }

        return Usuario.buscar(usuarioId);
    }

    /**
     * Apaga um usuário
     * @param user Usuario autenticado
     */
    @ApiMethod(name ="removerUsuario")
    public void removerUsuario(User user) throws OAuthRequestException {
        if(user==null){
            throw new OAuthRequestException("Usuário não autenticado");
        }

        Usuario.apagar(user.getEmail());
    }


}
