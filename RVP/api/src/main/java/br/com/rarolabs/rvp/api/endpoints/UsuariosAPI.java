package br.com.rarolabs.rvp.api.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;


import javax.inject.Named;

import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Usuario;


@Api(name = "rvpAPI", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.rvp.rarolabs.com.br", ownerName = "api.rvp.rarolabs.com.br", packagePath = ""))
public class UsuariosAPI {

    /**
     * Cria um novo usuário
     * @param usuario Dados do usuário a ser criado
     * @return O usuário criado
     * @throws ConflictException Caso o e-mail informado já esteja em uso
     */
    @ApiMethod(name = "novoUsuario")
    public Usuario novoUsuario(Usuario usuario) throws ConflictException {
        return Usuario.novoUsuario(usuario);
    }

    /**
     * Busca um usuário pelo ID
     * @param id ID do usuário
     * @return Usuario encontrado
     * @throws NotFoundException caso o usuário não seja encontrado
     */
    @ApiMethod(name = "buscarUsuario")
    public Usuario buscarUsuario(@Named("id") Long id) throws NotFoundException {
        return Usuario.buscar(id);
    }

    /**
     * Apaga um usuário
     * @param id ID do usuário
     */
    @ApiMethod(name ="removerUsuario")
    public void removerUsuario(@Named("id") Long id){
        Usuario.apagar(id);
    }

    /**
     * Salva um novo endereco para um usuário
     * @param endereco Endereco a ser salvo
     */
    @ApiMethod(name = "salvarEndereco")
    public void salvarEndereco(Endereco endereco){
        Endereco.salvar(endereco);
    }


}
